package shared.models.board.vertex;

import shared.models.board.edge.Edge;
import shared.models.board.hex.Hex;
import shared.models.board.hex.HexNotLinkedException;
import shared.models.board.piece.Building;
import shared.models.board.piece.PositionTakenException;
import shared.models.hand.ResourceType;
import shared.models.hand.exceptions.ResourceException;

public class Vertex {
	
	private Hex hexs[] = new Hex[3];
	private Edge edges[] = new Edge[3];
	private VertexLocation vertexLocation;
	private Building building;

	/**
	 * @pre hex is either hexs[0], hexs[1], or hexs[2];
	 * @param hex one of three possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Left hex attached to this if facing the vertex from param hex
	 */
	public Hex getLeftHex(Hex hex) throws HexNotLinkedException {
		if (hex == hexs[0]) {
			return hexs[1];
		} else if (hex == hexs[1]) {
			return hexs[2];
		} else if (hex == hexs[2]) {
			return hexs[0];
		}
		throw new HexNotLinkedException();
	}
	
	/**
	 * @pre hex is either hexs[0], hexs[1], or hexs[2];
	 * @param hex one of three possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Right hex attached to this if facing the vertex from param hex
	 */
	public Hex getRightHex(Hex hex) throws HexNotLinkedException {
		if (hex == hexs[0]) {
			return hexs[2];
		} else if (hex == hexs[2]) {
			return hexs[1];
		} else if (hex == hexs[1]) {
			return hexs[0];
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

	public boolean isBuildable() {
		if (hexs[0].isBuildable())
			return true;
		if (hexs[1].isBuildable())
			return true;
		if (hexs[2].isBuildable())
			return true;
		return false;
	}

	public boolean hasBuilding() {
		if (this.building != null)
			return true;
		return false;
	}

	public Edge[] getAllEdges() {
		return edges;
	}

	public Edge getLeftEdge(Edge edge) throws EdgeNotLinkedException {
		if (edge == edges[0]) {
			return edges[1];
		} else if (edge == edges[1]) {
			return edges[2];
		} else if (edge == edges[2]) {
			return edges[0];
		}
		throw new EdgeNotLinkedException();
	}
	
	public Edge getRightEdge(Edge edge) throws EdgeNotLinkedException {
		if (edge == edges[0]) {
			return edges[2];
		} else if (edge == edges[2]) {
			return edges[1];
		} else if (edge == edges[1]) {
			return edges[0];
		}
		throw new EdgeNotLinkedException();
	}

}
