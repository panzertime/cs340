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
				int ratio = CanModelFacade.sole().canOfferMaritime(type);
				if (ratio != 0) {
					giveRatios.put(type, ratio);
				}
			}
			ResourceType[] resources = (ResourceType[]) giveRatios.keySet().toArray();
			tradeOverlay.showGiveOptions(resources);
			getTradeOverlay().showModal();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void makeTrade() {
		// modelFacade.doTrades
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		// selectGetOption
		// setTradeEnabled
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		// selectGiveOption
		tradeOverlay.selectGiveOption(resource, giveRatios.get(resource));
		tradeOverlay.hideGiveOptions();
		// hideGiveOptions
		// canRecieveOffer for each
		// showGetOptions for each
	}

	@Override
	public void unsetGetValue() {
		// these might work by doing .getController.unset*Value() in 
		// the listener that currently resets in the overlay;
		// do like a roll back of each stage
		// like if stage enabled, unset

		// showGetOptions
		// tradeDisabled
	}

	@Override
	public void unsetGiveValue() {
		// hideGetOptions
		// showGiveOptions
		// 
	}

}

