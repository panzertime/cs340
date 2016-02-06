package shared.models;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import shared.models.exceptions.BadPlayerIndexException;

public class TradeModel {
	
	private Player sender;
	private Player receiver;
	private int wood;
	private int brick;
	private int sheep;
	private int wheat;
	private int ore;
	
	
	public TradeModel(JSONObject tradeOffer)
	{
		if (tradeOffer == null) return;
		int s = ((Long) tradeOffer.get("sender")).intValue();
		int r = ((Long) tradeOffer.get("receiver")).intValue();
		try {
			sender = GameModel.whichPlayer(s);
			receiver = GameModel.whichPlayer(r);
		} catch (BadPlayerIndexException e) {
			e.printStackTrace();
		}
		JSONObject offer = (JSONObject)tradeOffer.get("offer");
		wood = ((Long) offer.get("wood")).intValue();
		brick = ((Long) offer.get("brick")).intValue();
		sheep = ((Long) offer.get("sheep")).intValue();
		wheat = ((Long) offer.get("wheat")).intValue();
		ore = ((Long) offer.get("ore")).intValue();
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



	public Map<String, Object> getResources() {
		Map<String, Object> resources = new HashMap<String, Object>();
		if (this.getBrick() > 0)
			resources.put("brick", this.getBrick() * (-1));
		else
			resources.put("brick", 0);
		if (this.getOre() > 0)
		resources.put("ore", this.getOre() * (-1));
		else
			resources.put("ore", 0);
		if (this.getSheep() > 0)
		resources.put("sheep", this.getSheep() * (-1));
		else
			resources.put("sheep", 0);
		if (this.getWheat() > 0)
		resources.put("wheat", this.getWheat() * (-1));
		else
			resources.put("wheat", 0);
		if (this.getWood() > 0)
		resources.put("wood", this.getWood() * (-1));
		else
			resources.put("wood", 0);
		return resources;
	}
	
	

}
