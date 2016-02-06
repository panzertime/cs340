package shared.models.board.hex;

import shared.models.exceptions.BadJSONException;
import shared.models.hand.ResourceType;

public enum HexType
{
	
	WOOD, BRICK, SHEEP, WHEAT, ORE, DESERT, WATER;
	
	private ResourceType resource;
	
	public static HexType fromJSON(String jsonResourceType) throws BadJSONException {
		switch (jsonResourceType) {
		case "Wood":
			return HexType.WOOD;
		case "Brick":
			return HexType.BRICK;
		case "Sheep":
			return HexType.SHEEP;
		case "Wheat":
			return HexType.WHEAT;
		case "Ore":
			return HexType.ORE;
		default:
			throw new BadJSONException();
		}
	}
	static
	{
		WOOD.resource = ResourceType.WOOD;
		BRICK.resource = ResourceType.BRICK;
		SHEEP.resource = ResourceType.SHEEP;
		WHEAT.resource = ResourceType.WHEAT;
		ORE.resource = ResourceType.ORE;
	}
	
	public ResourceType getResourceType()
	{
		return resource;
	}
}

