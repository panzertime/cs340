package client.maritime;

import java.util.HashMap;
import java.util.Arrays;

import client.base.*;
import client.modelfacade.*;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.hand.ResourceType;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, GetModelFacadeListener {

	private IMaritimeTradeOverlay tradeOverlay;

	private HashMap<ResourceType,Integer> giveRatios;
	private HashMap<ResourceType,Integer> getRatios;
	private ResourceType giveType;
	private ResourceType getType;
	
	
	private void reset()
	{
		giveRatios = new HashMap<ResourceType,Integer>();
		getRatios = new HashMap<ResourceType,Integer>();
		giveType = null;
		getType = null;
	}
	
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
		giveRatios = new HashMap<ResourceType,Integer>();
		getRatios = new HashMap<ResourceType,Integer>();
		GetModelFacade.registerListener(this);

		
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
		try {	
			tradeOverlay.setCancelEnabled(true);
			for (ResourceType type : ResourceType.values()) {
				System.out.println("Checking give ratio for " + type.toString());
				int ratio = CanModelFacade.sole().canOfferMaritime(type);
				if (ratio != 0) {
					giveRatios.put(type, ratio);
				}
			}
			if (!giveRatios.isEmpty()) {
				Object[] keys = giveRatios.keySet().toArray();
				ResourceType[] resources = Arrays.copyOf(keys, keys.length, ResourceType[].class);		
				getTradeOverlay().showGiveOptions(resources);
			} 
			else {
				getTradeOverlay().showGiveOptions(new ResourceType[5]);
			}
			getTradeOverlay().showModal();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void makeTrade() {
		try {
			int ratio = giveRatios.get(giveType);
			DoModelFacade.sole().doMaritimeTrade(ratio, giveType, getType);
			reset();
			getTradeOverlay().closeModal();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
		reset();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		// selectGetOption
		// setTradeEnabled
		tradeOverlay.selectGetOption(resource, getRatios.get(resource));
		getType = resource;
		tradeOverlay.setTradeEnabled(true);
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		try {
			tradeOverlay.selectGiveOption(resource, giveRatios.get(resource));
			giveType = resource;
			tradeOverlay.hideGiveOptions();
			for (ResourceType type : ResourceType.values()) {
				boolean ratio = CanModelFacade.sole().canReceiveMaritime(type);
				if (ratio) {
					getRatios.put(type, 1);
				}
			}
			if (!getRatios.isEmpty()) {
				Object[] keys = getRatios.keySet().toArray();
				ResourceType[] resources = Arrays.copyOf(keys, keys.length, ResourceType[].class);	
				getTradeOverlay().showGetOptions(resources);
			}
			else {
				getTradeOverlay().showGetOptions(new ResourceType[5]);
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void unsetGetValue() {
		tradeOverlay.setTradeEnabled(false);
		getType = null;
		ResourceType[] resources = (ResourceType[]) getRatios.keySet().toArray();
		tradeOverlay.showGetOptions(resources);
	}

	@Override
	public void unsetGiveValue() {
		giveType = null;
		ResourceType[] resources = (ResourceType[]) giveRatios.keySet().toArray();
		tradeOverlay.showGiveOptions(resources);

	}

	@Override
	public void update() {
		this.getTradeView().enableMaritimeTrade(CanModelFacade.sole().canDomesticTrade());		
	}

}

