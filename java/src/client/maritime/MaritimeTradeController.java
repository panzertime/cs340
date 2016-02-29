package client.maritime;

import java.util.HashMap;

import client.base.*;
import client.modelfacade.*;
import shared.model.hand.ResourceType;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;

	private HashMap<ResourceType,Integer> giveRatios;
	private HashMap<ResourceType,Integer> getRatios;
	private ResourceType giveType;
	private ResourceType getType;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
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
			if (
			ResourceType[] resources = new ResourceType[]();
			resources = (ResourceType[]) giveRatios.keySet().toArray();
			tradeOverlay.showGiveOptions(resources);
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
			getTradeOverlay().closeModal();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
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
			ResourceType[] resources = (ResourceType[]) getRatios.keySet().toArray();
			tradeOverlay.showGetOptions(resources);
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

}

