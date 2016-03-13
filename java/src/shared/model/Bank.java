package shared.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;

import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.NoDevCardFoundException;
import shared.model.hand.Hand;
import shared.model.hand.ResourceType;
import shared.model.hand.development.DevCard;
import shared.model.hand.exceptions.NoRemainingResourceException;

public class Bank {
	
	
	
	
	
	/**
	 * @return the hand
	 */
	public Hand getHand() {
		return hand;
	}
	private Hand hand;
	
	public Bank(JSONObject resourceList, JSONObject deckList) throws BadJSONException
	{
		if (resourceList == null || deckList == null) throw new BadJSONException(); 
		hand = new Hand(resourceList, deckList);
	}
	
	public boolean equalsJSON(JSONObject resourceList, JSONObject deckList) {
		if (resourceList == null || deckList == null) return false; 
		return hand.equalsJSON(resourceList, deckList);
	}

	/**
	 * @pre New game
	 * @post Bank hand has the amount of cards and resources specified by game rules
	 */
/*	public void initializeBank() {
//		hand = new Hand(19, 19, 19, 19, 19);
		for (int i = 0; i < 14; i++)
		{
			this.giveDevCardToBank(new Knight());
		}
		for (int i = 0; i < 5; i++)
		{
			this.giveDevCardToBank(new Monument());
		}
		for (int i = 0; i < 2; i++)
		{
			this.giveDevCardToBank(new Monopoly());
			this.giveDevCardToBank(new RoadBuilding());
			this.giveDevCardToBank(new YearOfPlenty());
		}
		
		
	}*/
	
	//public Boolean hasResources(ResourceType type, Integer num) throws ResourceException {}

	/**
	 * @return true if bank hand has >=1 Dev cards 
	 */
	public Boolean hasDevCard() {
		
		return this.getHand().hasDevCard();
	}
	
	/**
	 * @pre hasDevCard
	 * @return Random devCard from bank's hand 
	 * @throws NoDevCardFoundException
	 * @post devCard returned is deleted from the bank's hand
	 */
	public DevCard takeDevCardFromBank()  throws NoDevCardFoundException {
		Random generator = new Random(); 
		int r = generator.nextInt(this.getHand().getNumberOfDevCards());
		
		DevCard card = this.getHand().getDevCards().get(r);
		card.setEnabled(false);
		this.getHand().getDevCards().remove(r);
		return card;
		}
	
	/**
	 * @param card The card being returned to the bank
	 * @post The card has been added to the corresponding list in the bank's hand
	 */
	public void giveDevCardToBank(DevCard card) {
		this.getHand().getDevCards().add(card);
	}
	
	
	/**
	 * @param type the type of resource to add
	 * @param num the number of resource to be added
	 * @throws ResourceException
	 * @post bank now has addition resources of type and num 
	 */
	public void receiveResource(ResourceType type, Integer num) {
		this.getHand().receiveResource(type, num);
	}
	
	/**
	 * @param type the type of resource to take
	 * @param num the number of resource to be taken
	 * @throws NoRemainingResourceException 
	 * @throws ResourceException
	 * @post bank now has less resources of type and num 
	 */
	public void sendResource(ResourceType type, Integer num) throws NoRemainingResourceException {
		this.getHand().sendResource(type, num);
	}


	public JSONObject toJSON() {
		Map<String, Object> resourceList = new HashMap<String, Object>();
		resourceList.put("wood", this.getHand().getWood());
		resourceList.put("brick", this.getHand().getBrick());
		resourceList.put("sheep", this.getHand().getSheep());
		resourceList.put("wheat", this.getHand().getWheat());
		resourceList.put("ore", this.getHand().getOre());
		return (JSONObject) resourceList;
	}

	
	
	
}
