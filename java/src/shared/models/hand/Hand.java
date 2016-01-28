
package shared.models.hand;

import java.util.ArrayList;

import shared.models.hand.development.Knight;
import shared.models.hand.development.Monopoly;
import shared.models.hand.development.RoadBuilding;
import shared.models.hand.development.Victory;
import shared.models.hand.development.YearOfPlenty;

public class Hand {
	
	private Integer wood = 0;
	private Integer brick = 0;
	private Integer sheep = 0;
	private Integer wheat = 0;
	private Integer ore = 0;
	
	private ArrayList<Knight> knightCards;
	private ArrayList<YearOfPlenty>  yearofplentyCards;
	private ArrayList<Victory>  victoryCards;
	private ArrayList<Monopoly>  monopolyCards;
	private ArrayList<RoadBuilding>  roadbuildingCards;
	
	/**
	 * 
	 * @return wood
	 */
	public Integer getWood() {
		return wood;
	}

	/**
	 * 
	 * @return brick
	 */
	public Integer getBrick() {
		return brick;
	}

	/**
	 * 
	 * @return sheep
	 */
	
	public Integer getSheep() {
		return sheep;
	}

	/**
	 * 
	 * @return wheat
	 */
	public Integer getWheat() {
		return wheat;
	}

	/**
	 * 
	 * @return ore
	 */
	public Integer getOre() {
		return ore;
	}
	
	/**
	 * 
	 * @param wood
	 * @param brick
	 * @param sheep
	 * @param wheat
	 * @param ore
	 */
	public Hand(Integer wood, Integer brick, Integer sheep, Integer wheat, Integer ore) {
		this.wood = wood;
		this.brick = brick;
		this.sheep = sheep;
		this.wheat = wheat;
		this.ore = ore;
	}
	
	/**
	 * @return Total: wood + brick + sheep + wheat + ore
	 */
	public int getHandSize() {
		return getWood() + getBrick() + getSheep() + getWheat() + getOre();}
	
	/**
	 * @param type the type of resource to add
	 * @param num the number of resource to be added
	 * @throws ResourceException
	 * @post hand now has addition resources of type and num 
	 */
	public void giveResource(ResourceType type, Integer num) throws ResourceException {
		switch (type) {
			case WOOD: 
				wood += num;
				return;
			case BRICK: 
				brick += num;
				return;
			case SHEEP: 
				sheep += num;
				return;
			case WHEAT: 
				wheat += num;
				return;
			case ORE: 
				ore += num;
				return;
		}
		throw new BadResourceTypeException();
	}


	/**
	 * @param type the type of resource to take
	 * @param num the number of resource to be taken
	 * @throws ResourceException
	 * @post hand now has less resources of type and num 
	 */
	public void takeResource(ResourceType type, Integer num) throws ResourceException {
		if (!hasResource(type, num)) {
			throw new NoRemainingResourceException();
		}
		switch (type) {
			case WOOD: 
				wood -= num;
				return;
			case BRICK: 
				brick -= num;
				return;
			case SHEEP: 
				sheep -= num;
				return;
			case WHEAT: 
				wheat -= num;
				return;
			case ORE: 
				ore -= num;
				return;
		}
	}
	
	/**
	 * @param type the type of resource to checked
	 * @param num the number of resource to be checked
	 * @return Boolean Indicating if the player has the resources of type and nun
	 * @throws BadResourceTypeException
	 */
	private Boolean hasResource(ResourceType type, Integer num) throws BadResourceTypeException {
		switch (type) {
			case WOOD: 
				if (wood >= num) {
					return true;
				}
				return false;
			case BRICK: 
				if (brick >= num) {
					return true;
				}
				return false;
			case SHEEP: 
				if (sheep >= num) {
					return true;
				}
				return false;
			case WHEAT: 
				if (wheat >= num) {
					return true;
				}
				return false;
			case ORE: 
				if (ore >= num) {
					return true;
				}
				return false;
		}
		throw new BadResourceTypeException();
	}
	
	/**
	 * @return Boolean indicating if player has resources for road
	 */
	public Boolean hasRoadCost() {
		try {
			if (!hasResource(ResourceType.WOOD, 1))
				return false;
			if (!hasResource(ResourceType.BRICK, 1))
				return false;
			return true;
		} catch (ResourceException e) {
			//TODO add log "fix enums in " print stackTrace
		}
		return false;
	}
	
	/**
	 * @return Boolean indicating if player has resources for settlement
	 */
	public Boolean hasSettlementCost() {
		try {
			if (!hasResource(ResourceType.WOOD, 1))
				return false;
			if (!hasResource(ResourceType.BRICK, 1))
				return false;
			if (!hasResource(ResourceType.SHEEP, 1))
				return false;
			if (!hasResource(ResourceType.WHEAT, 1))
				return false;
			return true;
		} catch (ResourceException e) {
			//TODO add log "fix enums in " print stackTrace
		}
		return false;
	}
	
	/**
	 * @return Boolean indicating if player has resources for city
	 */
	public Boolean hasCityCost() {
		try {
			if (!hasResource(ResourceType.WHEAT, 2))
				return false;
			if (!hasResource(ResourceType.ORE, 3))
				return false;
			return true;
		} catch (ResourceException e) {
			//TODO add log "fix enums in " print stackTrace
		}
		return false;
	}
	
	/**
	 * @return Boolean indicating if player has resources for development
	 */
	public Boolean hasDevelopmentCost() {
		try {
			if (!hasResource(ResourceType.SHEEP, 1))
				return false;
			if (!hasResource(ResourceType.WHEAT, 1))
				return false;
			if (!hasResource(ResourceType.ORE, 1))
				return false;
			return true;
		} catch (ResourceException e) {
			//TODO add log "fix enums in " print stackTrace
		}
		return false;
	}
	

	/**
	 * @pre hasSettlementCost
	 * @post wood = wood - 1
	 * @post brick = brick - 1
	 * @post wheat = wheat - 1
	 * @post sheep = sheep - 1
	 * 			
	 */
	public void buySettlement() {}
	
	
	/**
	 * 
	 * @pre hasCityCost
	 * @post wheat = wheat - 2
	 * @post ore = ore - 3
	 */
	public void buyCity() {}
	
	
	/**
	 * @pre hasRoadCost
	 * @post wood = wood - 1
	 * @post brick = brick - 1
	 */
	public void buyRoad() {}
	
	
	/**
	 * @pre hasDevelopmentCost
	 * @post wheat = wheat - 1
	 * @post sheep = sheep - 1
	 * @post ore = ore - 1
	 * 
	 */
	public void buyDevelopment() {}
	
	
	/**
	 * @param given The type of card being given by the player
	 * @param received The type of card being received by the player
	 * @post type given = type - 4
	 * @post type received = type + 1
	 * 
	 */
	public void buyResource(ResourceType given, ResourceType received) {}
	
	
	/**
	 * @param given The type of card being given by the player
	 * @param received The type of card being received by the player
	 * @post type given = type - 3
	 * @post type received = type + 1
	 */
	public void buyResourceWith3Port(ResourceType given, ResourceType received) {}

	/**
	 * @param given The type of card being given by the player
	 * @param received The type of card being received by the player
	 * @post type given = type - 2
	 * @post type received = type + 1
	 */
	public void buyResourceWith2Port(ResourceType given, ResourceType received) {}
	
}