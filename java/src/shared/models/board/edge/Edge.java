package shared.models.board.edge;

import java.util.ArrayList;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexNotLinkedException;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;
import shared.models.board.vertex.Vertex;

public class Edge {
	
	private Hex hexs[] = new Hex[2];
	private Vertex verts[] = new Vertex[2];
	private EdgeLocation edgeLocation;
	private Road road;

	/**
	 * @pre hex is either hexs[0] or hexs[1];
	 * @param hex one of two possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Other hex attached to this
	 */
	public Hex getOtherHex(Hex hex) throws HexNotLinkedException {
		if (hex == hexs[0]) {
			return hexs[1];
		} else if (hex == hexs[1]) {
			return hexs[0];
		}
		throw new HexNotLinkedException();
	}
	
	public Vertex getOtherVertex(Vertex vertex) throws VertexNotLinkedException {
		if (vertex == verts[0]) {
			return verts[1];
		} else if (vertex == verts[1]) {
			return verts[0];
		}
		throw new VertexNotLinkedException();
	}

	/**
	 * @return the edgeLocation
	 */
	public EdgeLocation getEdgeLocation() {
		return edgeLocation;
	}

	/**
	 * @param edgeLocation the edgeLocation to set
	 */
	public void setEdgeLocation(EdgeLocation edgeLocation) {
		this.edgeLocation = edgeLocation.getNormalizedLocation();
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
