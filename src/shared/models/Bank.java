package shared.models;

import java.util.ArrayDeque;

import shared.models.hand.Hand;
import shared.models.hand.ResourceException;
import shared.models.hand.ResourceType;
import shared.models.hand.development.DevCard;
import shared.models.hand.development.NoDevCardFoundException;

public class Bank {

	
	private Hand hand;

	/**
	 * @pre New game
	 * @post Bank hand has the amount of cards and resources specified by game rules
	 */
	public void initializeBank() {}
	
	//public Boolean hasResources(ResourceType type, Integer num) throws ResourceException {}

	/**
	 * @return true if bank hand has >=1 Dev cards
	 */
	public Boolean hasDevCard() {
		return null;}
	
	/**
	 * @pre hasDevCard
	 * @return Random devCard from bank's hand 
	 * @throws NoDevCardFoundException
	 * @post devCard returned is deleted from the bank's hand
	 */
	public DevCard takeDevCardFromBank()  throws NoDevCardFoundException {
		return null;}
	
	/**
	 * @param card The card being returned to the bank
	 * @post The card has been added to the corresponding list in the bank's hand
	 */
	public void returnDevCardToBank(DevCard card) {}
	
	
	/**
	 * @param type the type of resource to add
	 * @param num the number of resource to be added
	 * @throws ResourceException
	 * @post bank now has addition resources of type and num 
	 */
	public void giveResource(ResourceType type, Integer num) throws ResourceException {}
	
	/**
	 * @param type the type of resource to take
	 * @param num the number of resource to be taken
	 * @throws ResourceException
	 * @post bank now has less resources of type and num 
	 */
	public void takeResource(ResourceType type, Integer num) throws ResourceException {}
	
	/**
	* @post bank now has addition resources of settlement cost 
	 */
	public void receiveSettlementCost() {}
	/**
	* @post bank now has addition resources of city cost 
	 */
	public void receiveCityCost() {}
	/**
	* @post bank now has addition resources of road cost 
	 */
	public void receiveRoadCost() {}
	/**
	* @post bank now has addition resources of development cost 
	 */
	public void receiveDevelopmentCost() {}
}
