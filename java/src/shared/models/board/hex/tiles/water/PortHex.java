package shared.models.board.hex.tiles.water;

import shared.models.board.edge.EdgeDirection;

public class PortHex extends WaterHex{
	
	private EdgeDirection portDirection;
	private PortType portType;
	
	public PortHex(PortType portType) throws NullPortTypeException {
		if (portType == null) 
			throw new NullPortTypeException();
		this.setPortType(portType);
	}

	/**
	 * @param portType the portType to set
	 */
	public void setPortType(PortType portType) {
		this.portType = portType;
	}

	/**
	 * @return the portDirection
	 */
	public EdgeDirection getPortDirection() {
		return portDirection;
	}

	/**
	 * @param portDirection the portDirection to set
	 */
	public void setPortDirection(EdgeDirection portDirection) {
		this.portDirection = portDirection;
	}

	/**
	 * @return the portType
	 */
	public PortType getPortType() {
		return portType;
	}
}
