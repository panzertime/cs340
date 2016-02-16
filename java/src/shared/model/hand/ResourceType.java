package shared.model.hand;

import shared.model.board.hex.tiles.water.PortType;

public enum ResourceType
{
	WOOD, BRICK, SHEEP, WHEAT, ORE;
	
	private PortType portType;
	
	
	static
	{
		WOOD.portType = PortType.WOOD;
		BRICK.portType = PortType.BRICK;
		SHEEP.portType = PortType.SHEEP;
		WHEAT.portType = PortType.WHEAT;
		ORE.portType = PortType.ORE;
	}

	public PortType getPortType()
	{
		return portType;
	}
}

