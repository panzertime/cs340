package shared.models;

import java.util.Map;

import shared.models.exceptions.BadPlayerIndexException;

public class TradeModel {
	
	private Player sender;
	private Player receiver;
	private int wood;
	private int brick;
	private int sheep;
	private int wheat;
	private int ore;
	
	
	public TradeModel(Map<String, Object> tradeOffer)
	{
		if (tradeOffer == null) return;
		int s = (Integer)tradeOffer.get("sender");
		int r = (Integer)tradeOffer.get("receiver");
		try {
			sender = GameModel.whichPlayer(s);
			receiver = GameModel.whichPlayer(r);
		} catch (BadPlayerIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> offer = (Map<String, Object>)tradeOffer.get("offer");
		wood = (Integer)offer.get("wood");
		brick = (Integer)offer.get("brick");
		sheep = (Integer)offer.get("sheep");
		wheat = (Integer)offer.get("wheat");
		ore = (Integer)offer.get("ore");
		
	}


	public Player getSender() {
		return sender;
	}


	public void setSender(Player sender) {
		this.sender = sender;
	}


	public Player getReceiver() {
		return receiver;
	}


	public void setReceiver(Player receiver) {
		this.receiver = receiver;
	}


	public int getWood() {
		return wood;
	}


	public void setWood(int wood) {
		this.wood = wood;
	}


	public int getBrick() {
		return brick;
	}


	public void setBrick(int brick) {
		this.brick = brick;
	}


	public int getSheep() {
		return sheep;
	}


	public void setSheep(int sheep) {
		this.sheep = sheep;
	}


	public int getWheat() {
		return wheat;
	}


	public void setWheat(int wheat) {
		this.wheat = wheat;
	}


	public int getOre() {
		return ore;
	}


	public void setOre(int ore) {
		this.ore = ore;
	}
	
	

}
