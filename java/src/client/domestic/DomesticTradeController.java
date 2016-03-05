package client.domestic;

import java.util.HashMap;
import java.util.Map;

import client.base.*;
import client.data.PlayerInfo;
import client.main.ClientPlayer;
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
	int woodMax;
	int brickMax;
	int sheepMax;
	int wheatMax;
	int oreMax;
	
	boolean buttonPushed = false;


	 int sendingWood = 1;
	 int sendingBrick = 1;
	 int sendingSheep = 1;
	 int sendingWheat = 1; 
	 int sendingOre = 1;
	 
	int receiverIndex;
	
	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	private void reset(boolean isInit)
	{

		 wood = 0;
		 brick = 0;
		 sheep = 0;
		 wheat = 0;
		 ore = 0;
		receiverIndex = 0;
		if (!isInit) this.getTradeOverlay().reset();
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
		reset(true);
		GetModelFacade.registerListener(this);
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
		
		if (!buttonPushed)
		{
			buttonPushed = true;

		switch (resource)
		{
		case WOOD:
				wood--;
				this.getTradeOverlay().setResourceAmount(resource, wood.toString());
				if (wood == 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
			break;
		case BRICK:
				brick--;
				this.getTradeOverlay().setResourceAmount(resource, brick.toString());
				if (brick == 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);

			break;
		case SHEEP:
			sheep--;
			this.getTradeOverlay().setResourceAmount(resource, sheep.toString());
			if (sheep == 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);

			break;
		case WHEAT:
				wheat--;
				this.getTradeOverlay().setResourceAmount(resource, wheat.toString());
				if (wheat == 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);

			break;
		case ORE:
				ore--;
				this.getTradeOverlay().setResourceAmount(resource, ore.toString());
				if (ore == 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);

			break;
		}
		
		this.getTradeOverlay().setTradeEnabled(receiverIndex != ClientPlayer.sole().getUserIndex() && CanModelFacade.sole().canOfferTrade(getResourceList(), receiverIndex));
	
		buttonPushed = false;

		}
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		
		if (!buttonPushed)
		{
			buttonPushed = true;
		switch (resource)
		{
	case WOOD:
		wood++;
		this.getTradeOverlay().setResourceAmount(resource, wood.toString());
		if (sendingWood > 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, wood < woodMax, true);
		else this.getTradeOverlay().setResourceAmountChangeEnabled(resource, (wood < (19-woodMax)), true);
		break;
	case BRICK:
		brick++;
		this.getTradeOverlay().setResourceAmount(resource, brick.toString());
		if (sendingBrick > 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, brick < brickMax, true);
		else this.getTradeOverlay().setResourceAmountChangeEnabled(resource, (wood < (19-woodMax)), true);
		break;
	case SHEEP:
		sheep++;
		this.getTradeOverlay().setResourceAmount(resource, sheep.toString());
		if (sendingSheep > 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, sheep < sheepMax, true);
		else this.getTradeOverlay().setResourceAmountChangeEnabled(resource, (sheep < (19-sheepMax)), true);
		break;
	case WHEAT:
		wheat++;
		this.getTradeOverlay().setResourceAmount(resource, wheat.toString());
		if (sendingWheat > 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, wheat < wheatMax, true);
		else this.getTradeOverlay().setResourceAmountChangeEnabled(resource, (wheat < (19-wheatMax)), true);
		break;
	case ORE:
		ore++;
		this.getTradeOverlay().setResourceAmount(resource, ore.toString());
		if (sendingOre > 0) this.getTradeOverlay().setResourceAmountChangeEnabled(resource, ore < oreMax, true);
		else this.getTradeOverlay().setResourceAmountChangeEnabled(resource, (ore < (19-oreMax)), true);
		break;
		}
		
		this.getTradeOverlay().setTradeEnabled(receiverIndex != ClientPlayer.sole().getUserIndex() && CanModelFacade.sole().canOfferTrade(getResourceList(), receiverIndex));
		
		buttonPushed = false;

		}
	}

	@Override
	public void sendTradeOffer() {

		DoModelFacade.sole().doOfferTrade(getResourceList(), receiverIndex);
		getTradeOverlay().closeModal();
		reset(false);
		getWaitOverlay().showModal();
	}

	private Map<ResourceType, Integer> getResourceList()
	{
		Map<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>();
		resources.put(ResourceType.WOOD, wood * sendingWood);
		resources.put(ResourceType.BRICK, brick * sendingBrick);
		resources.put(ResourceType.SHEEP, sheep * sendingSheep);
		resources.put(ResourceType.WHEAT, wheat * sendingWheat);
		resources.put(ResourceType.ORE, ore * sendingOre);
		return resources;
	}
	
	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		receiverIndex = playerIndex;
		this.getTradeOverlay().setTradeEnabled(receiverIndex != ClientPlayer.sole().getUserIndex() && CanModelFacade.sole().canOfferTrade(getResourceList(), receiverIndex));

	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		switch (resource)
		{
		case WOOD:
			if (sendingWood > 0) wood = 0;
			sendingWood = -1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WOOD, (wood < (19-woodMax)), (wood > 0));
			this.getTradeOverlay().setResourceAmount(resource, wood.toString());
			break;
		case BRICK:
			if (sendingBrick > 0) brick = 0;
			sendingBrick = -1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.BRICK, brick < (19-brickMax), (brick > 0));
			this.getTradeOverlay().setResourceAmount(resource, brick.toString());

			break;
		case SHEEP:
			if (sendingSheep > 0) sheep = 0;
			sendingSheep = -1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.SHEEP, sheep < (19-sheepMax), (sheep > 0));
			this.getTradeOverlay().setResourceAmount(resource, sheep.toString());

			break;
		case WHEAT:
			if (sendingWheat > 0) wheat = 0;
			sendingWheat = -1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WHEAT, wheat < (19-wheatMax), (wheat > 0));
			this.getTradeOverlay().setResourceAmount(resource, wheat.toString());

			break;
		case ORE:
			if (sendingOre > 0) ore = 0;
			sendingOre = -1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ORE, ore < (19-oreMax), (ore > 0));
			this.getTradeOverlay().setResourceAmount(resource, ore.toString());

			break;
		}
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
				
		switch (resource)
		{
		case WOOD:
			if (sendingWood < 0) wood = 0;
			sendingWood = 1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(resource, wood < woodMax, (wood > 0));
			this.getTradeOverlay().setResourceAmount(resource, wood.toString());

			break;
		case BRICK:
			if (sendingBrick < 0) brick = 0;
			sendingBrick = 1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(resource, brick < brickMax, (brick > 0));
			this.getTradeOverlay().setResourceAmount(resource, brick.toString());

			break;
		case SHEEP:
			if (sendingSheep < 0) sheep = 0;
			sendingSheep = 1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(resource, sheep < sheepMax, (sheep > 0));
			this.getTradeOverlay().setResourceAmount(resource, sheep.toString());

			break;
		case WHEAT:
			if (sendingWheat < 0) wheat = 0;
			sendingWheat = 1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(resource, wheat < wheatMax, (wheat > 0));
			this.getTradeOverlay().setResourceAmount(resource, wheat.toString());

			break;
		case ORE:
			if (sendingOre < 0) ore = 0;
			sendingOre = 1;
			this.getTradeOverlay().setResourceAmountChangeEnabled(resource, ore < oreMax, (ore > 0));
			this.getTradeOverlay().setResourceAmount(resource, ore.toString());
			break;
		}

	}

	@Override
	public void unsetResource(ResourceType resource) {
		switch (resource)
		{
		case WOOD:
			wood = 0;
			break;
		case BRICK:
			brick = 0;
			break;
		case SHEEP:
			sheep = 0;
			break;
		case WHEAT:
			wheat = 0;
			break;
		case ORE:
			ore = 0;
			break;
		}
		this.getTradeOverlay().setResourceAmount(resource, "");
		this.getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
		this.reset(false);
	}

	@Override
	public void acceptTrade(boolean willAccept) {

		DoModelFacade doModelFacade = DoModelFacade.sole();
		doModelFacade.doAcceptTrade(willAccept);
		getAcceptOverlay().closeModal();
		this.reset(false);
	}

	PlayerInfo[] tradingPartners = null;
	
	@Override
	public void update() {
		if (!GetModelFacade.sole().hasTradeOffer()) {
			if (this.getWaitOverlay().isModalShowing()) this.getWaitOverlay().closeModal();
		}
		if (tradingPartners == null)
			{ tradingPartners = GetModelFacade.sole().getTradingPartners();
			this.getTradeOverlay().setPlayers(tradingPartners);
			}
		boolean canDomesticTrade = CanModelFacade.sole().canDomesticTrade();
		this.getTradeView().enableDomesticTrade(canDomesticTrade);
		this.getTradeOverlay().setResourceSelectionEnabled(canDomesticTrade);
		this.getTradeOverlay().setPlayerSelectionEnabled(canDomesticTrade);
		GetModelFacade getModelFacade = GetModelFacade.sole();
		if (CanModelFacade.sole().canViewTrade()) 
		{
			this.getAcceptOverlay().reset();
			this.getAcceptOverlay().setPlayerName(getModelFacade.getTradeSenderName());

			
			for (ResourceType type: ResourceType.values())
			{
				int resource = GetModelFacade.sole().getTradeResource(type);
				if (resource > 0) this.getAcceptOverlay().addGetResource(type, resource);
				if (resource < 0) this.getAcceptOverlay().addGiveResource(type, resource * -1);
			}
			
			this.getAcceptOverlay().setAcceptEnabled(CanModelFacade.sole().canAcceptTrade());
			
			if (!this.getAcceptOverlay().isModalShowing()) this.getAcceptOverlay().showModal();			
		}
		woodMax = getModelFacade.getResourceAmount(ResourceType.WOOD);
		brickMax = getModelFacade.getResourceAmount(ResourceType.BRICK);
		sheepMax = getModelFacade.getResourceAmount(ResourceType.SHEEP);
		wheatMax = getModelFacade.getResourceAmount(ResourceType.WHEAT);
		oreMax = getModelFacade.getResourceAmount(ResourceType.ORE);
//		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WOOD, (wood < woodMax), (wood > 0));
//		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.BRICK, (brick < brickMax), (brick > 0));
//		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.SHEEP, (sheep < sheepMax), (sheep > 0));
//		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WHEAT, (wheat < wheatMax), (wheat > 0));
//		this.getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ORE, (ore < oreMax), (ore > 0));
		
	}

}

