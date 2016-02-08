package shared.models.board.edge;

import shared.logger.Log;
import shared.models.board.hex.Hex;
import shared.models.board.hex.HexNotLinkedException;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;
import shared.models.board.vertex.Vertex;

public class Edge {
	
	private Hex hex0;
	private Hex hex1;
	private Vertex vertex0;
	private Vertex vertex1;
	private EdgeLocation edgeLocation;
	private Road road;

	
	public Edge(EdgeLocation edgeLocation, Hex hex0, Hex hex1) {
		this.edgeLocation = edgeLocation;
		this.hex0 = hex0;
		this.hex1 = hex1;
		road = null;
	}
	/**
	 * @pre hex is either hex0 or hex1;
	 * @param hex one of two possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Other hex attached to this
	 */
	public Hex getOtherHex(Hex hex) {
		if (hex == hex0) {
			return hex1;
		} else if (hex == hex1) {
			return hex0;
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public Vertex getOtherVertex(Vertex vertex) {
		if (vertex == vertex0) {
			return vertex1;
		} else if (vertex == vertex1) {
			return vertex0;
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public Hex getLeftHex(Vertex vertex) {
		if (vertex == vertex0) {
			return hex1;
		} else if (vertex == vertex1) {
			return hex0;
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public Hex getRightHex(Vertex vertex) {
		if (vertex == vertex0) {
			return hex0;
		} else if (vertex == vertex1) {
			return hex1;
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}
	
	public void setLeftVertex(Hex hex, Vertex vertex) {
		if (hex == hex0) {
			vertex0 = vertex;
			return;
		} else if (hex == hex1) {
			vertex1 = vertex;
			return;
		}
		Log.error("Board is Broken");
		assert false;
	}
	
	public void setRightVertex(Hex hex, Vertex vertex) {
		if (hex == hex0) {
			vertex1 = vertex;
			return;
		} else if (hex == hex1) {
			vertex0 = vertex;
			return;
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
		if (hex0 != null && hex0.isBuildable())
			return true;
		if (hex1 != null && hex1.isBuildable())
			return true;
		return false;
	}

	public Vertex[] getAllVertices() {
		Vertex[] verts = new Vertex[2];
		verts[0] = vertex0;
		verts[1] = vertex1;
		return verts;
	}

}