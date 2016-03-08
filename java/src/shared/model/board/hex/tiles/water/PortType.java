package shared.model.board.hex.tiles.water;

import shared.model.exceptions.BadJSONException;
import shared.model.hand.ResourceType;

public enum PortType
{
	
	WOOD, BRICK, SHEEP, WHEAT, ORE, THREE;

	private ResourceType resource;
	
	public static PortType fromJSON(String jsonResourceType) throws BadJSONException {
		System.out.println("JSONResource for each port type" + jsonResourceType);
		
		switch (jsonResourceType) {
		case "Wood":
			return PortType.WOOD;
		case "Brick":
			return PortType.BRICK;
		case "Sheep":
			return PortType.SHEEP;
		case "Wheat":
			return PortType.WHEAT;
		case "Ore":
			return PortType.ORE;
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
		THREE.resource = null;
	}

	public ResourceType getResourceType()
	{
		return resource;
	}
}

