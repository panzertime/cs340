package shared.models;

import java.util.Map;
import java.util.Random;

import shared.models.exceptions.NoDevCardFoundException;
import shared.models.hand.Hand;
import shared.models.hand.ResourceType;
import shared.models.hand.development.DevCard;
import shared.models.hand.development.Knight;
import shared.models.hand.development.Monopoly;
import shared.models.hand.development.RoadBuilding;
import shared.models.hand.development.Monument;
import shared.models.hand.development.YearOfPlenty;
import shared.models.hand.exceptions.ResourceException;

public class Bank {
	
	
	
	
	
	/**
	 * @return the hand
	 */
	public Hand getHand() {
		return hand;
	}
	private Hand hand;
	
	public Bank(Map<String, Object> resourceList, Map<String, Object> deckList)
	{
		hand = new Hand(resourceList, deckList);
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
	public void receiveResource(ResourceType type, Integer num) throws ResourceException {
		this.getHand().receiveResource(type, num);
	}
	
	/**
	 * @param type the type of resource to take
	 * @param num the number of resource to be taken
	 * @throws ResourceException
	 * @post bank now has less resources of type and num 
	 */
	public void sendResource(ResourceType type, Integer num) throws ResourceException {
		this.getHand().sendResource(type, num);
	}
	
	
}
