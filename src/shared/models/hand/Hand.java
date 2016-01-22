package shared.models.hand;

public class Hand {
	
	private Integer wood = 0;
	private Integer brick = 0;
	private Integer sheep = 0;
	private Integer wheat = 0;
	private Integer ore = 0;
	
	public Hand(Integer wood, Integer brick, Integer sheep, Integer wheat, Integer ore) {
		this.wood = wood;
		this.brick = brick;
		this.sheep = sheep;
		this.wheat = wheat;
		this.ore = ore;
	}
	
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
}
