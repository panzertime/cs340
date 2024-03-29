package client.discard;

import java.util.HashMap;
import java.util.Map;

import client.base.Controller;
import client.misc.IWaitView;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.hand.ResourceType;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, GetModelFacadeListener {

	private int wood = 0;
	private int brick = 0;
	private int sheep = 0;
	private int wheat = 0;
	private int ore = 0;
	boolean buttonPushed = false;
	private int discardNeeded = 0;
	
	private IWaitView waitView;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		setToStandard();
		this.waitView = waitView;
		
		GetModelFacade.registerListener(this);
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		if (!buttonPushed)
		{
			buttonPushed = true;

		switch (resource)
		{
	case WOOD:
		wood++;
		this.getDiscardView().setResourceDiscardAmount(resource, wood);
		break;
	case BRICK:
		brick++;
		this.getDiscardView().setResourceDiscardAmount(resource, brick);
		break;
	case SHEEP:
		sheep++;
		this.getDiscardView().setResourceDiscardAmount(resource, sheep);
		break;
	case WHEAT:
		wheat++;
		this.getDiscardView().setResourceDiscardAmount(resource, wheat);
		break;
	case ORE:
		ore++;
		this.getDiscardView().setResourceDiscardAmount(resource, ore);
		break;
		}
		int resourceTotal = wood + brick + sheep + wheat + ore;
		this.getDiscardView().setStateMessage("Discard " + resourceTotal + "/" + discardNeeded);
		if (resourceTotal == discardNeeded)//(CanModelFacade.sole().canDiscardCards(this.getResourceList()))
		{
			this.getDiscardView().setDiscardButtonEnabled(true);
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, (wood > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, (brick > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, (sheep > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, (wheat > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, (ore > 0));
		}
		else
		{
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, (wood < woodMax), (wood > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, (brick < brickMax), (brick > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, (sheep < sheepMax), (sheep > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, (wheat < wheatMax), (wheat > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, (ore < oreMax), (ore > 0));

		}
		buttonPushed = false;

		}
		
	}
	

	@Override
	public void decreaseAmount(ResourceType resource) {
		if (!buttonPushed)
		{
			buttonPushed = true;

		switch (resource)
		{
		case WOOD:

				wood--;
				this.getDiscardView().setResourceDiscardAmount(resource, wood);
		break;
		case BRICK:
				brick--;
				this.getDiscardView().setResourceDiscardAmount(resource, brick);
			break;
		case SHEEP:
			sheep--;
			this.getDiscardView().setResourceDiscardAmount(resource, sheep);
			break;
		case WHEAT:
				wheat--;
				this.getDiscardView().setResourceDiscardAmount(resource, wheat);
			break;
		case ORE:
				ore--;
				this.getDiscardView().setResourceDiscardAmount(resource, ore);
			break;
		}
		
		int resourceTotal = wood + brick + sheep + wheat + ore;
		this.getDiscardView().setStateMessage("Discard " + resourceTotal + "/" + discardNeeded);
		if (resourceTotal < discardNeeded)//(!CanModelFacade.sole().canDiscardCards(this.getResourceList()))
		{
			this.getDiscardView().setDiscardButtonEnabled(false);
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, (wood < woodMax), (wood > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, (brick < brickMax), (brick > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, (sheep < sheepMax), (sheep > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, (wheat < wheatMax), (wheat > 0));
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, (ore < oreMax), (ore > 0));
		
		}
		else
		{
			
		}
		
		buttonPushed = false;

		}
	}

	@Override
	public void discard() {
		getDiscardView().closeModal();
		DoModelFacade.sole().doDiscard(this.getResourceList());
		setToStandard();
		if (GetModelFacade.sole().isStateDiscarding())
			this.getWaitView().showModal();
	}

	private Map<ResourceType, Integer> getResourceList()
	{
		Map<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>();
		resources.put(ResourceType.WOOD, wood);
		resources.put(ResourceType.BRICK, brick);
		resources.put(ResourceType.SHEEP, sheep);
		resources.put(ResourceType.WHEAT, wheat);
		resources.put(ResourceType.ORE, ore);
		return resources;
	}
	
	private void setToStandard()
	{
		this.getDiscardView().setStateMessage("");
		wood = 0;
		brick = 0;
		sheep = 0;
		wheat = 0;
		ore = 0;
		woodMax = 0;
		brickMax = 0;
		sheepMax = 0;
		wheatMax = 0;
		oreMax = 0;
		this.getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, wood);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, brick);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, sheep);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, wheat);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.ORE, ore);
		this.getDiscardView().setDiscardButtonEnabled(false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
	}
	
	int woodMax;
	int brickMax;
	int sheepMax;
	int wheatMax;
	int oreMax;
	@Override
	public void update() {
		
		GetModelFacade getModelFacade = GetModelFacade.sole();
		woodMax = getModelFacade.getResourceAmount(ResourceType.WOOD);
		brickMax = getModelFacade.getResourceAmount(ResourceType.BRICK);
		sheepMax = getModelFacade.getResourceAmount(ResourceType.SHEEP);
		wheatMax = getModelFacade.getResourceAmount(ResourceType.WHEAT);
		oreMax = getModelFacade.getResourceAmount(ResourceType.ORE);
		boolean mustDiscard = getModelFacade.mustDiscard();
		if (mustDiscard && !this.getDiscardView().isModalShowing())
		{
			this.getDiscardView().showModal();
		}
		else if(GetModelFacade.sole().isStateDiscarding() && !mustDiscard  && !this.getWaitView().isModalShowing())
		{	this.getWaitView().showModal();
		}
		else if (!GetModelFacade.sole().isStateDiscarding() && this.getWaitView().isModalShowing())
		{
			this.getWaitView().closeModal();
		}
		else if (!GetModelFacade.sole().isStateDiscarding() && this.getDiscardView().isModalShowing())
		{
			this.getDiscardView().closeModal();
		}
		int totalCards = woodMax + brickMax + sheepMax + wheatMax + oreMax;
		discardNeeded = totalCards / 2;
		
		this.getDiscardView().setResourceMaxAmount(ResourceType.WOOD, woodMax);
		this.getDiscardView().setResourceMaxAmount(ResourceType.BRICK, brickMax);
		this.getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, sheepMax);
		this.getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, wheatMax);
		this.getDiscardView().setResourceMaxAmount(ResourceType.ORE, oreMax);
		
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, (0 < woodMax), wood > 0);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, (0 < brickMax), brick > 0);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, (0 < sheepMax), sheep > 0);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, (0 < wheatMax), wheat > 0);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, (0 < oreMax), ore > 0);
		int cardsToDiscard = wood + brick + sheep + wheat + ore;
		this.getDiscardView().setStateMessage("Discard " + cardsToDiscard + "/" + discardNeeded);
	}

}

