package shared.models.board.vertex;

import shared.logger.Log;
import shared.models.board.edge.Edge;
import shared.models.board.hex.Hex;
import shared.models.board.hex.HexNotLinkedException;
import shared.models.board.hex.HexType;
import shared.models.board.hex.tiles.water.PortHex;
import shared.models.board.hex.tiles.water.PortType;
import shared.models.board.piece.Building;
import shared.models.board.piece.PositionTakenException;
import shared.models.hand.ResourceType;
import shared.models.hand.exceptions.ResourceException;

public class Vertex {
	
	private Hex[] hexes;
	private Edge[] edges;
	private VertexLocation vertexLocation;
	private Building building;
	
	public Vertex (VertexLocation vertexLoc, Hex hex0, Edge edge0, Hex hex1, Edge edge1, Hex hex2, Edge edge2) {
		hexes = new Hex[3];
		edges = new Edge[3];
		this.vertexLocation = vertexLoc;
		building = null;
		
		hexes[0] = hex0;
		hexes[1] = hex1;
		hexes[2] = hex2;
		edges[0] = edge0;
		edges[1] = edge1;
		edges[2] = edge2;
	}

	/**
	 * @pre hex is either hexes[0], hexes[1], or hexes[2];
	 * @param hex one of three possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Left hex attached to this if facing the vertex from param hex
	 */
	public Hex getLeftHex(Hex hex){
		if (hex == hexes[0]) {
			return hexes[1];
		} else if (hex == hexes[1]) {
			return hexes[2];
		} else if (hex == hexes[2]) {
			return hexes[0];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	/**
	 * @pre hex is either hexes[0], hexes[1], or hexes[2];
	 * @param hex one of three possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Right hex attached to this if facing the vertex from param hex
	 */
	public Hex getRightHex(Hex hex) {
		if (hex == hexes[0]) {
			return hexes[2];
		} else if (hex == hexes[2]) {
			return hexes[1];
		} else if (hex == hexes[1]) {
			return hexes[0];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
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
		if (hexes[0].isBuildable())
			return true;
		if (hexes[1].isBuildable())
			return true;
		if (hexes[2].isBuildable())
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

	public Edge getLeftEdge(Edge edge) {
		if (edge == edges[0]) {
			return edges[1];
		} else if (edge == edges[1]) {
			return edges[2];
		} else if (edge == edges[2]) {
			return edges[0];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public Edge getRightEdge(Edge edge) {
		if (edge == edges[0]) {
			return edges[2];
		} else if (edge == edges[2]) {
			return edges[1];
		} else if (edge == edges[1]) {
			return edges[0];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}

	public Boolean hasPort(PortType portType) {
		for (Edge edge : edges) {
			Hex hexLeft = edge.getLeftHex(this);
			if (hexLeft != null) {
				if (hexLeft instanceof PortHex) {
					if (hexLeft.getEdge(((PortHex) hexLeft).getPortDirection()) == edge) {
						return true;
					}
				}
			}
			Hex hexRight = edge.getLeftHex(this);
			if (hexRight != null) {
				if (hexRight instanceof PortHex) {
					if (hexRight.getEdge(((PortHex) hexRight).getPortDirection()) == edge) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
