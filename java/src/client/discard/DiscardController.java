package client.discard;

import java.util.HashMap;
import java.util.Map;

import client.base.Controller;
import client.misc.IWaitView;
import client.modelfacade.CanModelFacade;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.hand.ResourceType;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, GetModelFacadeListener {

	int wood;
	int brick;
	int sheep;
	int wheat;
	int ore;
	
	private IWaitView waitView;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
		setToStandard();
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
		
		GetModelFacade getModelFacade = GetModelFacade.sole();
		switch (resource)
		{
	case WOOD:
		if (wood < getModelFacade.getResourceAmount(resource))
		{
		wood++;
		this.getDiscardView().setResourceDiscardAmount(resource, wood);
		}
		break;
	case BRICK:
		if (brick < getModelFacade.getResourceAmount(resource))
		{
		brick++;
		this.getDiscardView().setResourceDiscardAmount(resource, brick);
		}
		break;
	case SHEEP:
		if (sheep < getModelFacade.getResourceAmount(resource))
		{
		sheep++;
		this.getDiscardView().setResourceDiscardAmount(resource, sheep);
		}
		break;
	case WHEAT:
		if (wheat < getModelFacade.getResourceAmount(resource))
		{
		wheat++;
		this.getDiscardView().setResourceDiscardAmount(resource, wheat);
		}
		break;
	case ORE:
		if (ore < getModelFacade.getResourceAmount(resource))
		{
		ore++;
		this.getDiscardView().setResourceDiscardAmount(resource, ore);
		}
		break;
		}
		
		CanModelFacade canModelFacade = CanModelFacade.sole();
		if (canModelFacade.canDiscardCards(this.getResourceList()))
			this.getDiscardView().setDiscardButtonEnabled(true);

	}
	

	@Override
	public void decreaseAmount(ResourceType resource) {
		
		switch (resource)
		{
		case WOOD:
			if (wood > 0)
			{
				wood--;
				this.getDiscardView().setResourceDiscardAmount(resource, wood);
			}
			break;
		case BRICK:
			if (brick > 0)
			{
				brick--;
				this.getDiscardView().setResourceDiscardAmount(resource, brick);
			}	
			break;
		case SHEEP:
			if (sheep > 0)
			{
			sheep--;
			this.getDiscardView().setResourceDiscardAmount(resource, sheep);
			}
			break;
		case WHEAT:
			if (wheat > 0)
			{
				wheat--;
				this.getDiscardView().setResourceDiscardAmount(resource, wheat);
			}
			break;
		case ORE:
			if (ore > 0)
			{
				ore--;
				this.getDiscardView().setResourceDiscardAmount(resource, ore);
			}
			break;
		}
		CanModelFacade canModelFacade = CanModelFacade.sole();
		if (!canModelFacade.canDiscardCards(this.getResourceList()))
			this.getDiscardView().setDiscardButtonEnabled(false);
	}

	@Override
	public void discard() {
		
		getDiscardView().closeModal();
		setToStandard();
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
		wood = 0;
		brick = 0;
		sheep = 0;
		wheat = 0;
		ore = 0;
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
	
	@Override
	public void update() {
		GetModelFacade getModelFacade = GetModelFacade.sole();
		int wo = getModelFacade.getResourceAmount(ResourceType.WOOD);
		int b = getModelFacade.getResourceAmount(ResourceType.WOOD);
		int s = getModelFacade.getResourceAmount(ResourceType.WOOD);
		int wh = getModelFacade.getResourceAmount(ResourceType.WOOD);
		int o = getModelFacade.getResourceAmount(ResourceType.WOOD);
		
		this.getDiscardView().setResourceMaxAmount(ResourceType.WOOD, wo);
		this.getDiscardView().setResourceMaxAmount(ResourceType.BRICK, b);
		this.getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, s);
		this.getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, wh);
		this.getDiscardView().setResourceMaxAmount(ResourceType.ORE, o);
		
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, (wood < wo), (wood > 0));
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, (brick < b), (brick > 0));
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, (sheep < s), (sheep > 0));
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, (wheat < wh), (wheat > 0));
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, (ore < o), (ore > 0));
	}

}

