package client.resources;

import java.util.*;

import client.base.*;
import client.modelfacade.CanModelFacade;
import client.modelfacade.get.GetModelFacade;
import shared.model.hand.ResourceType;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}
	
	public void update()
	{
		GetModelFacade getModelFacade = GetModelFacade.sole();
		CanModelFacade canModelFacade = CanModelFacade.sole();
		
		this.getView().setElementAmount(ResourceBarElement.WOOD, getModelFacade.getResourceAmount(ResourceType.WOOD));
		this.getView().setElementAmount(ResourceBarElement.BRICK, getModelFacade.getResourceAmount(ResourceType.BRICK));
		this.getView().setElementAmount(ResourceBarElement.SHEEP, getModelFacade.getResourceAmount(ResourceType.SHEEP));
		this.getView().setElementAmount(ResourceBarElement.WHEAT, getModelFacade.getResourceAmount(ResourceType.WHEAT));
		this.getView().setElementAmount(ResourceBarElement.ORE, getModelFacade.getResourceAmount(ResourceType.ORE));
		this.getView().setElementAmount(ResourceBarElement.ROAD, getModelFacade.getFreeRoads());
		this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, getModelFacade.getFreeSettlements());
		this.getView().setElementAmount(ResourceBarElement.CITY, getModelFacade.getFreeCities());
		this.getView().setElementAmount(ResourceBarElement.SOLDIERS, getModelFacade.getSoldiers());
		this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, canModelFacade.canBuyDevCard());
		
	}

}

