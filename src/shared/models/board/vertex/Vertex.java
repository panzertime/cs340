package shared.models.board.vertex;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexNotLinkedException;
import shared.models.board.piece.Building;
import shared.models.board.piece.PositionTakenException;
import shared.models.hand.ResourceException;
import shared.models.hand.ResourceType;

public class Vertex {
	
	private Hex hex0;
	private Hex hex1;
	private Hex hex2;
	private VertexLocation vertexLocation;
	private Building building;

	/**
	 * @Pre hex is either hex0, hex1, or hex2;
	 * @param hex one of three possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Left hex attached to this if facing the vertex from param hex
	 */
	public Hex getLeftHex(Hex hex) throws HexNotLinkedException {
		if (hex == hex0) {
			return hex1;
		} else if (hex == hex1) {
			return hex2;
		} else if (hex == hex2) {
			return hex0;
		}
		throw new HexNotLinkedException();
	}
	
	/**
	 * @Pre hex is either hex0, hex1, or hex2;
	 * @param hex one of three possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Right hex attached to this if facing the vertex from param hex
	 */
	public Hex getRightHex(Hex hex) throws HexNotLinkedException {
		if (hex == hex0) {
			return hex2;
		} else if (hex == hex2) {
			return hex1;
		} else if (hex == hex1) {
			return hex0;
		}
		throw new HexNotLinkedException();
	}

	/**
	 * @return the vertexLocation
	 */
	public VertexLocation getVertexLocation() {
		return vertexLocation;
	}

	/**
	 * @param vertexLocation the vertexLocation to set
	 */
	public void setVertexLocation(VertexLocation vertexLocation) {
		this.vertexLocation = vertexLocation.getNormalizedLocation();
	}
	
	public void produce(ResourceType type) throws ResourceException {
		if (building != null)
			building.produce(type);
	}

	/**
	 * @return the building
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 * @throws PositionTakenException 
	 */
	public void setBuilding(Building building) throws PositionTakenException {
		if (this.building != null)
			throw new PositionTakenException();
		this.building = building;
	}

}
