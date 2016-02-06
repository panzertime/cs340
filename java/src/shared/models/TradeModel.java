package shared.models;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import shared.logger.Log;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.BadPlayerIndexException;

public class TradeModel {
	
	private Player sender;
	private Player receiver;
	private int wood;
	private int brick;
	private int sheep;
	private int wheat;
	private int ore;
	
	
	public TradeModel(JSONObject tradeOffer) throws BadJSONException
	{
		if (tradeOffer == null) return;
		Long s = ((Long) tradeOffer.get("sender"));
		Long r = ((Long) tradeOffer.get("receiver"));
		if (s == null || r == null) throw new BadJSONException();
		
		try {
			sender = GameModel.whichPlayer(s.intValue());
			receiver = GameModel.whichPlayer(r.intValue());
		} catch (BadPlayerIndexException e) {
			Log.exception(e);
		}
		JSONObject offer = (JSONObject)tradeOffer.get("offer");
		Long wood = ((Long) offer.get("wood"));
		Long brick = ((Long) offer.get("brick"));
		Long sheep = ((Long) offer.get("sheep"));
		Long wheat = ((Long) offer.get("wheat"));
		Long ore = ((Long) offer.get("ore"));
		if (wood == null || brick == null || sheep == null || wheat == null || ore == null) throw new BadJSONException();
		this.wood = wood.intValue();
		this.brick = brick.intValue();
		this.sheep = sheep.intValue();
		this.wheat = wheat.intValue();
		this.ore = ore.intValue();
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
