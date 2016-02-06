package shared.models.board.hex.tiles.water;

import shared.models.board.edge.EdgeDirection;
import shared.models.board.hex.HexLocation;

public class PortHex extends WaterHex{
	
	private EdgeDirection portDirection;
	private PortType portType;
	
	public PortHex(HexLocation hexLocation, PortType portType, EdgeDirection portDirection) throws IllegalArgumentException {
		super(hexLocation);
		if (portType == null) 
			throw new IllegalArgumentException();
		setPortType(portType);
		setPortDirection(portDirection);
	}

	/**
	 * @param portType the portType to set
	 */
	private void setPortType(PortType portType) {
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
	private void setPortDirection(EdgeDirection portDirection) {
		this.portDirection = portDirection;
	}

	/**
	 * @return the portType
	 */
	public PortType getPortType() {
		return portType;
	}
}
