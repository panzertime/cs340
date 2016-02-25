package client.devcards;

import client.base.*;
import client.modelfacade.CanModelFacade;
import client.modelfacade.get.GetModelFacade;
import shared.model.hand.ResourceType;
import shared.model.hand.development.DevCardType;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

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
		
	}

	@Override
	public void playMonumentCard() {
		
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
		
	}
	
	public void update() 
	{
		GetModelFacade getModelFacade = GetModelFacade.sole();
		CanModelFacade canModelFacade = CanModelFacade.sole();
		
		this.getPlayCardView().setCardEnabled(DevCardType.KNIGHT, getModelFacade.hasDevCardEnabled(DevCardType.KNIGHT));
		this.getPlayCardView().setCardAmount(DevCardType.KNIGHT, getModelFacade.getDevCardAmount(DevCardType.KNIGHT)); 
		
		this.getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, getModelFacade.hasDevCardEnabled(DevCardType.MONOPOLY));
		this.getPlayCardView().setCardAmount(DevCardType.MONOPOLY, getModelFacade.getDevCardAmount(DevCardType.MONOPOLY)); 
		
		this.getPlayCardView().setCardEnabled(DevCardType.YEAROFPLENTY, getModelFacade.hasDevCardEnabled(DevCardType.YEAROFPLENTY));
		this.getPlayCardView().setCardAmount(DevCardType.YEAROFPLENTY, getModelFacade.getDevCardAmount(DevCardType.YEAROFPLENTY)); 
		
		this.getPlayCardView().setCardEnabled(DevCardType.ROADBUILDING, getModelFacade.hasDevCardEnabled(DevCardType.ROADBUILDING));
		this.getPlayCardView().setCardAmount(DevCardType.ROADBUILDING, getModelFacade.getDevCardAmount(DevCardType.ROADBUILDING)); 
		
		this.getPlayCardView().setCardEnabled(DevCardType.MONUMENT, getModelFacade.hasDevCardEnabled(DevCardType.MONUMENT));
		this.getPlayCardView().setCardAmount(DevCardType.MONUMENT, getModelFacade.getDevCardAmount(DevCardType.MONUMENT)); 	
		

	}

}

