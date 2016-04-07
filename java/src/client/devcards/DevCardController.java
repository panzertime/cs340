package client.devcards;

import client.base.Controller;
import client.base.IAction;
import client.main.ClientPlayer;
import client.modelfacade.CanModelFacade;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.hand.ResourceType;
import shared.model.hand.development.DevCardType;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, GetModelFacadeListener {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		
		GetModelFacade.registerListener(this);

	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		DoModelFacade doModelFacade = DoModelFacade.sole();
		doModelFacade.doBuyDevCard();
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		//can?
		DoModelFacade.sole().doUseMonopoly(resource);
	}

	@Override
	public void playMonumentCard() {
		//can?
		DoModelFacade.sole().doUseMonument();
		
	}

	@Override
	public void playRoadBuildCard() {
		
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		DoModelFacade.sole().doUseYearOfPlenty(resource1, resource2);
	}
	
	@Override
	public void update() 
	{
		int playerIndex = ClientPlayer.sole().getUserIndex();
		if(GetModelFacade.sole().isTurn(playerIndex)) {
			this.getPlayCardView().setCardEnabled(DevCardType.KNIGHT, GetModelFacade.sole().hasDevCardEnabled(DevCardType.KNIGHT));
			this.getPlayCardView().setCardAmount(DevCardType.KNIGHT, GetModelFacade.sole().getDevCardAmount(DevCardType.KNIGHT)); 
			
			this.getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, GetModelFacade.sole().hasDevCardEnabled(DevCardType.MONOPOLY));
			this.getPlayCardView().setCardAmount(DevCardType.MONOPOLY, GetModelFacade.sole().getDevCardAmount(DevCardType.MONOPOLY)); 
			
			this.getPlayCardView().setCardEnabled(DevCardType.YEAROFPLENTY, GetModelFacade.sole().hasDevCardEnabled(DevCardType.YEAROFPLENTY));
			this.getPlayCardView().setCardAmount(DevCardType.YEAROFPLENTY, GetModelFacade.sole().getDevCardAmount(DevCardType.YEAROFPLENTY)); 
			
			this.getPlayCardView().setCardEnabled(DevCardType.ROADBUILDING, GetModelFacade.sole().hasDevCardEnabled(DevCardType.ROADBUILDING));
			this.getPlayCardView().setCardAmount(DevCardType.ROADBUILDING, GetModelFacade.sole().getDevCardAmount(DevCardType.ROADBUILDING)); 
			
			this.getPlayCardView().setCardEnabled(DevCardType.MONUMENT, CanModelFacade.sole().canUseMonument());
			this.getPlayCardView().setCardAmount(DevCardType.MONUMENT, GetModelFacade.sole().getDevCardAmount(DevCardType.MONUMENT)); 	
		}		
	}
}

