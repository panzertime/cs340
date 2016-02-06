package shared.models.board.hex;

import org.json.simple.JSONObject;

import shared.logger.Log;
import shared.models.board.edge.Edge;
import shared.models.board.edge.EdgeDirection;
import shared.models.board.vertex.Vertex;
import shared.models.board.vertex.VertexDirection;
import shared.models.exceptions.BadJSONException;

public abstract class Hex {
	
	protected HexType hexType;
	private HexLocation hexLocation;
	private Edge[] edges = new Edge[6];
	protected Vertex[] verts = new Vertex[6];
	
	public Hex(HexLocation hexLocation) {
		this.hexLocation = hexLocation;
	}
	/**
	 * @return Boolean the ability of the hex to hold pieces
	 */
	public abstract Boolean isBuildable();
	
	/**
	 * @return the hexType
	 */
	public HexType getHexType() {
		return hexType;
	}

	/**
	 * @param hexType the hexType to set
	 */
	public void setHexType(HexType hexType) {
		this.hexType = hexType;
	}
	
	/**
	 * @param dir the direction of the edge to set
	 * @param edge the edge to set
	 * @throws BadEdgeDirectionException
	 */
	public void setEdge(EdgeDirection dir, Edge edge) {
		switch (dir) {
			case North: 
				edges[0] = edge;
				return;
			case NorthEast: 
				edges[1] = edge;
				return;
			case SouthEast: 
				edges[2] = edge;
				return;
			case South: 
				edges[3] = edge;
				return;
			case SouthWest: 
				edges[4] = edge;
				return;
			case NorthWest: 
				edges[5] = edge;
				return;
		}
		Log.error("Board is Broken");
		assert false;
	}

	/**
	 * @param dir the direction of the edge to get
	 * @param edge the edge to get
	 * @throws BadEdgeDirectionException
	 */
	public Edge getEdge(EdgeDirection dir) {
		switch (dir) {
			case North: 
				return edges[0];
			case NorthEast: 
				return edges[1];
			case SouthEast: 
				return edges[2];
			case South: 
				return edges[3];
			case SouthWest: 
				return edges[4];
			case NorthWest: 
				return edges[5];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}

	/**
	 * @param dir the direction of the vertex to set
	 * @param edge the vertex to set
	 * @throws BadEdgeDirectionException
	 */
	public void setVertex(VertexDirection dir, Vertex vertex) {
		switch (dir) {
			case NorthEast: 
				verts[0] = vertex;
				return;
			case East: 
				verts[1] = vertex;
				return;
			case SouthEast: 
				verts[2] = vertex;
				return;
			case SouthWest: 
				verts[3] = vertex;
				return;
			case West: 
				verts[4] = vertex;
				return;
			case NorthWest: 
				verts[5] = vertex;
				return;
		}
		Log.error("Board is Broken");
		assert false;
	}

	/**
	 * @param dir the direction of the vertex to get
	 * @param edge the vertex to get
	 * @throws BadEdgeDirectionException
	 */
	public Vertex getVertex(VertexDirection dir) {
		switch (dir) {
			case NorthEast: 
				return verts[0];
			case East: 
				return verts[1];
			case SouthEast: 
				return verts[2];
			case SouthWest: 
				return verts[3];
			case West: 
				return verts[4];
			case NorthWest: 
				return verts[5];
		}
		Log.error("Board is Broken");
		assert false;
		return null;
	}


	/**
	 * @return the hexlocation
	 */
	public HexLocation getHexLocation() {
		return hexLocation;
	}
	
	
    public boolean equals(JSONObject jsonHex) {
    	JSONObject jsonHexLoc = (JSONObject) jsonHex.get("Location");
    	if (hexLocation.getX() != (Long) jsonHexLoc.get("x"))
    		return false;
    	if (hexLocation.getY() != (Long) jsonHexLoc.get("y"))
    		return false;
    	return true;
    }
}
