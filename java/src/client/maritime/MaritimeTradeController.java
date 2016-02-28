package client.maritime;

import client.base.*;
import shared.model.hand.ResourceType;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	
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
		// setCancelEnabled
		// modelFacade.canTrades for each
		// showGiveOptions for each
		getTradeOverlay().showModal();
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

