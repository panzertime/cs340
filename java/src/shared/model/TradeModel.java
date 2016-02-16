package shared.model;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import shared.model.exceptions.BadJSONException;

public class TradeModel {
	
	private Integer senderID;
	/**
	 * @return the senderID
	 */
	public Integer getSenderID() {
		return senderID;
	}

	/**
	 * @return the receiverID
	 */
	public Integer getReceiverID() {
		return receiverID;
	}

	private Integer receiverID;
	private int wood;
	private int brick;
	private int sheep;
	private int wheat;
	private int ore;
	
	
	public TradeModel(JSONObject tradeOffer) throws BadJSONException
	{
		if (tradeOffer == null) return;
		Long senderID = ((Long) tradeOffer.get("sender"));
		Long receiverID = ((Long) tradeOffer.get("receiver"));
		if (senderID == null || receiverID == null)
			throw new BadJSONException();
		this.senderID = senderID.intValue();
		this.receiverID = receiverID.intValue();
		
		JSONObject offer = (JSONObject)tradeOffer.get("offer");
		if (offer == null)
			throw new BadJSONException();
		
		Long wood = ((Long) offer.get("wood"));
		Long brick = ((Long) offer.get("brick"));
		Long sheep = ((Long) offer.get("sheep"));
		Long wheat = ((Long) offer.get("wheat"));
		Long ore = ((Long) offer.get("ore"));
		if (wood == null || brick == null || sheep == null || wheat == null || ore == null)
			throw new BadJSONException();
		
		this.wood = wood.intValue();
		this.brick = brick.intValue();
		this.sheep = sheep.intValue();
		this.wheat = wheat.intValue();
		this.ore = ore.intValue();
	}
	


	public boolean equalsJSON(JSONObject tradeOffer) {
		Long senderID = ((Long) tradeOffer.get("sender"));
		Long receiverID = ((Long) tradeOffer.get("receiver"));
		if (senderID == null || receiverID == null)
			return false;
		if (this.senderID != senderID.intValue() || this.receiverID != receiverID.intValue())
			return false;
		
		JSONObject offer = (JSONObject)tradeOffer.get("offer");
		if (offer == null)
			return false;
		
		Long wood = ((Long) offer.get("wood"));
		Long brick = ((Long) offer.get("brick"));
		Long sheep = ((Long) offer.get("sheep"));
		Long wheat = ((Long) offer.get("wheat"));
		Long ore = ((Long) offer.get("ore"));
		if (wood == null || brick == null || sheep == null || wheat == null || ore == null) 
			return false;
		
		if (this.wood != wood.intValue()) return false;
		if (this.brick != brick.intValue()) return false;
		if (this.sheep != sheep.intValue()) return false;
		if (this.wheat != wheat.intValue()) return false;
		if (this.ore != ore.intValue()) return false;
		return true;
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



	public Map<String, Object> getResourcesToGive() {
		Map<String, Object> resources = new HashMap<String, Object>();
		if (this.getBrick() < 0)
			resources.put("brick", this.getBrick() * (-1));
		else
			resources.put("brick", 0);
		if (this.getOre() < 0)
			resources.put("ore", this.getOre() * (-1));
		else
			resources.put("ore", 0);
		if (this.getSheep() < 0)
		resources.put("sheep", this.getSheep() * (-1));
		else
			resources.put("sheep", 0);
		if (this.getWheat() < 0)
		resources.put("wheat", this.getWheat() * (-1));
		else
			resources.put("wheat", 0);
		if (this.getWood() < 0)
		resources.put("wood", this.getWood() * (-1));
		else
			resources.put("wood", 0);
		return resources;
	}



	

}
