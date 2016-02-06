package shared.models.board.edge;

import shared.logger.Log;
import shared.models.board.hex.Hex;
import shared.models.board.hex.HexNotLinkedException;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;
import shared.models.board.vertex.Vertex;

public class Edge {
	
	private Hex[] hexs = new Hex[2];
	private Vertex[] verts = new Vertex[2];
	private EdgeLocation edgeLocation;
	private Road road;

	
	public Edge(EdgeLocation edgeLocation, Hex hex0, Hex hex1) {
		this.edgeLocation = edgeLocation;
		hexs = new Hex[2];
		hexs[0] = hex0;
		hexs[1] = hex1;
		verts = new Vertex[2];
		road = null;
	}
	/**
	 * @pre hex is either hexs[0] or hexs[1];
	 * @param hex one of two possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Other hex attached to this
	 */
	public Hex getOtherHex(Hex hex) {
		if (hex == hexs[0]) {
			return hexs[1];
		} else if (hex == hexs[1]) {
			return hexs[0];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public Vertex getOtherVertex(Vertex vertex) {
		if (vertex == verts[0]) {
			return verts[1];
		} else if (vertex == verts[1]) {
			return verts[0];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public Hex getLeftHex(Vertex vertex) {
		if (vertex == verts[0]) {
			return hexs[1];
		} else if (vertex == verts[1]) {
			return hexs[0];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public Hex getRightHex(Vertex vertex) {
		if (vertex == verts[0]) {
			return hexs[0];
		} else if (vertex == verts[1]) {
			return hexs[1];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public void setLeftVertex(Hex hex, Vertex vertex) {
		if (hex == hexs[0]) {
			verts[0] = vertex;
		} else if (hex == hexs[1]) {
			verts[1] = vertex;
		}
		Log.error("Board is Broken");
		assert false;
	}
	
	public void setRightVertex(Hex hex, Vertex vertex) {
		if (hex == hexs[0]) {
			verts[1] = vertex;
		} else if (hex == hexs[1]) {
			verts[0] = vertex;
		}
		Log.error("Board is Broken");
		assert false;
	}

	/**
	 * @return the edgeLocation
	 */
	public EdgeLocation getEdgeLocation() {
		return edgeLocation;
	}

	/**
	 * @return the road
	 */
	public Road getRoad() {
		return road;
	}

	/**
	 * @param road the road to set
	 * @throws PositionTakenException 
	 */
	public void setRoad(Road road) throws PositionTakenException {
		if (this.road != null)
			throw new PositionTakenException();
		this.road = road;
	}

	public boolean hasRoad() {
		if (road != null)
			return true;
		return false;
	}

	public boolean isBuildable() {
		if (hexs[0].isBuildable())
			return true;
		if (hexs[1].isBuildable())
			return true;
		return false;
	}

	public Vertex[] getAllVertices() {
		return verts;
	}

}