package shared.models.board.hex.tiles.water;

import shared.models.exceptions.BadJSONException;

public enum PortType
{
	
	WOOD, BRICK, SHEEP, WHEAT, ORE, THREE;

	public static PortType fromJSON(String jsonResourceType) throws BadJSONException {
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
}

