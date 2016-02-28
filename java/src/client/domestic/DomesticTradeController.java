package client.domestic;

import client.base.*;
import client.misc.*;
import client.modelfacade.CanModelFacade;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.hand.ResourceType;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, GetModelFacadeListener {

	
	Integer wood;
	Integer brick;
	Integer sheep;
	Integer wheat;
	Integer ore;
	
	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	private void setToStandard()
	{
		wood = 0;
		brick = 0;
		sheep = 0;
		wheat = 0;
		ore = 0;
		this.getTradeOverlay().setResourceAmount(ResourceType.WOOD, wood.toString());
		this.getTradeOverlay().setResourceAmount(ResourceType.BRICK, brick.toString());
		this.getTradeOverlay().setResourceAmount(ResourceType.SHEEP, sheep.toString());
		this.getTradeOverlay().setResourceAmount(ResourceType.WHEAT, wheat.toString());
		this.getTradeOverlay().setResourceAmount(ResourceType.ORE, ore.toString());
		this.getTradeOverlay().setCancelEnabled(true);
		this.getTradeOverlay().setTradeEnabled(false);
		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);

	}
	
	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {

		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {

	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {

	}

	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {

	}

	@Override
	public void setResourceToReceive(ResourceType resource) {

	}

	@Override
	public void setResourceToSend(ResourceType resource) {

	}

	@Override
	public void unsetResource(ResourceType resource) {

	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {

		DoModelFacade doModelFacade = DoModelFacade.sole();
		doModelFacade.doAcceptTrade(willAccept);
		getAcceptOverlay().closeModal();
	}

	@Override
	public void update() {
		CanModelFacade canModelFacade = CanModelFacade.sole();
		this.getTradeView().enableDomesticTrade(canModelFacade.canDomesticTrade());
		this.getTradeOverlay().setResourceSelectionEnabled(canModelFacade.canDomesticTrade());
		this.getTradeOverlay().setPlayerSelectionEnabled(canModelFacade.canDomesticTrade());
		this.getAcceptOverlay().setAcceptEnabled(canModelFacade.canAcceptTrade());
		GetModelFacade getModelFacade = GetModelFacade.sole();
		this.getTradeOverlay().setPlayers(getModelFacade.getTradingPartners());
		if (canModelFacade.canViewTrade()) 
		{
			
			ResourceType type = ResourceType.WOOD;

			this.getAcceptOverlay().addGetResource(type, getModelFacade.getTradeGetResource(type));
			this.getAcceptOverlay().addGiveResource(type, getModelFacade.getTradeGiveResource(type));
			
			type = ResourceType.BRICK;

			this.getAcceptOverlay().addGetResource(type, getModelFacade.getTradeGetResource(type));
			this.getAcceptOverlay().addGiveResource(type, getModelFacade.getTradeGiveResource(type));
			
			type = ResourceType.SHEEP;

			this.getAcceptOverlay().addGetResource(type, getModelFacade.getTradeGetResource(type));
			this.getAcceptOverlay().addGiveResource(type, getModelFacade.getTradeGiveResource(type));

			type = ResourceType.WHEAT;
			
			this.getAcceptOverlay().addGetResource(type, getModelFacade.getTradeGetResource(type));
			this.getAcceptOverlay().addGiveResource(type, getModelFacade.getTradeGiveResource(type));

			type = ResourceType.ORE;

			this.getAcceptOverlay().addGetResource(type, getModelFacade.getTradeGetResource(type));
			this.getAcceptOverlay().addGiveResource(type, getModelFacade.getTradeGiveResource(type));
			
			this.getAcceptOverlay().setPlayerName(getModelFacade.getTradeSenderName());
			
		}
		

		
	}

}

