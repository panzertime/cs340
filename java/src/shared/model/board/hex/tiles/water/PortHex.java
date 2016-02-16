package shared.model.board.hex.tiles.water;

import org.json.simple.JSONObject;

import shared.model.board.edge.EdgeDirection;
import shared.model.board.hex.HexLocation;
import shared.model.exceptions.BadJSONException;

public class PortHex extends WaterHex {

	private EdgeDirection portDirection;
	private PortType portType;

	public PortHex(HexLocation hexLocation, PortType portType, EdgeDirection portDirection)
			throws IllegalArgumentException {
		super(hexLocation);
		if (portType == null)
			throw new IllegalArgumentException();
		setPortType(portType);
		setPortDirection(portDirection);
	}

	/**
	 * @param portType
	 *            the portType to set
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
	 * @param portDirection
	 *            the portDirection to set
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

	@Override
	public boolean equals(JSONObject jsonHex) {
		if (!super.equals(jsonHex))
			return false;
		String direction = (String) jsonHex.get("direction");
		try {
			if (portDirection != EdgeDirection.fromJSON(direction))
				return false;
		} catch (BadJSONException e) {
			// this will never happen, because the json will always be good
			// during testing
		}

		Object resource = jsonHex.get("resource");
		if (resource == null)
			return true;
		try {
			if (portType != PortType.fromJSON((String) resource))
				return false;
		} catch (BadJSONException e) {
			// this will never happen, because the json will always be good
			// during testing
		}
		return true;
	}
}
