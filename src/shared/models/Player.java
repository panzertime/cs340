package shared.models;

import shared.models.board.peice.City;
import shared.models.board.peice.NullPlayerException;
import shared.models.board.peice.Road;
import shared.models.board.peice.Settlement;
import shared.models.hand.Hand;
import shared.models.hand.ResourceException;
import shared.models.hand.ResourceType;

public class Player {
	
	private Hand hand;
	private Settlement[] settlements = new Settlement[5];
	private City[] cities = new City[4];
	private Road[] roads = new Road[15];
	private String userName;
	
	public Player(String userName) {
		try {
			for (int i = 0; i < 5; i++)
				settlements[i] = new Settlement(this);
			for (int i = 0; i < 4; i++) 
				cities[i] = new City(this);
			for (int i = 0; i < 15; i++)
				roads[i] = new Road(this);
		} catch (NullPlayerException e) {
			//TODO log "no player passed to building or Road Constructors at" print stack trace
		}
		setUserName(userName);
	}
	
	
	/**
	 * @param type the type of resource to add
	 * @param num the number of resource to be added
	 * @throws ResourceException
	 * @post player now has addition resources of type and num 
	 */
	public void giveResource(ResourceType type, Integer num) throws ResourceException {
		hand.giveResource(type, num);
	}


	/**
	 * @param type the type of resource to take
	 * @param num the number of resource to be taken
	 * @throws ResourceException
	 * @post player now has less resources of type and num 
	 */
	public void takeResource(ResourceType type, Integer num) throws ResourceException {
		hand.takeResource(type, num);
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
