package shared.model;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import shared.model.exceptions.BadJSONException;
import shared.model.hand.ResourceType;

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
	private Integer wood;
	private Integer brick;
	private Integer sheep;
	private Integer wheat;
	private Integer ore;
	
	
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

	public Integer getWood() {
		return wood;
	}


	public void setWood(Integer wood) {
		this.wood = wood;
	}


	public Integer getBrick() {
		return brick;
	}


	public void setBrick(Integer brick) {
		this.brick = brick;
	}


	public Integer getSheep() {
		return sheep;
	}


	public void setSheep(Integer sheep) {
		this.sheep = sheep;
	}


	public Integer getWheat() {
		return wheat;
	}


	public void setWheat(Integer wheat) {
		this.wheat = wheat;
	}


	public Integer getOre() {
		return ore;
	}


	public void setOre(Integer ore) {
		this.ore = ore;
	}



	public Map<ResourceType, Integer> getResourcesToGive() {
		Map<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>();
	        if (this.getBrick() < 0)
	            resources.put(ResourceType.BRICK, this.getBrick() * (-1));
	        else
	            resources.put(ResourceType.BRICK, 0);
	        if (this.getOre() < 0)
	            resources.put(ResourceType.ORE, this.getOre() * (-1));
	        else
	            resources.put(ResourceType.ORE, 0);
	        if (this.getSheep() < 0)
	        resources.put(ResourceType.SHEEP, this.getSheep() * (-1));
	        else
	            resources.put(ResourceType.SHEEP, 0);
	        if (this.getWheat() < 0)
	        resources.put(ResourceType.WHEAT, this.getWheat() * (-1));
	        else
	            resources.put(ResourceType.WHEAT, 0);
	        if (this.getWood() < 0)
	        resources.put(ResourceType.WOOD, this.getWood() * (-1));
	        else
	            resources.put(ResourceType.WOOD, 0);
		return resources;
	}



	

}