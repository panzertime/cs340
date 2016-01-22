package shared.models.board.hex;

import shared.models.board.edge.Edge;
import shared.models.board.vertex.Vertex;

public class Hex {
	
	private HexType hexType;
	private HexLocation hexlocation;
	private Edge[] edges = new Edge[6];
	private Vertex[] verts = new Vertex[6];

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
	 * @param edge the edge to set at the North Position
	 */
	public void setEdgeN(Edge edge) {
		this.edges[0] = edge;
	}
	
	/**
	 * @param edge the edge to set at the NorthEast Position
	 */
	public void setEdgeNE(Edge edge) {
		this.edges[1] = edge;
	}
	
	/**
	 * @param edge the edge to set at the SouthEast Position
	 */
	public void setEdgeSE(Edge edge) {
		this.edges[2] = edge;
	}

	/**
	 * @param edge the edge to set at the South Position
	 */
	public void setEdgeS(Edge edge) {
		this.edges[3] = edge;
	}
	
	/**
	 * @param edge the edge to set at the SouthWest Position
	 */
	public void setEdgeSW(Edge edge) {
		this.edges[4] = edge;
	}

	/**
	 * @param edge the edge to set at the NorthWest Position
	 */
	public void setEdgeNW(Edge edge) {
		this.edges[5] = edge;
	}

	/**
	 * @return edge the edge at the North Position
	 */
	public Edge getEdgeN() {
		return this.edges[0];
	}
	
	/**
	 * @return edge the edge at the NorthEast Position
	 */
	public Edge getEdgeNE() {
		return this.edges[1];
	}
	
	/**
	 * @return edge the edge at the SouthEast Position
	 */
	public Edge getEdgeSE() {
		return this.edges[2];
	}

	/**
	 * @return edge the edge at the South Position
	 */
	public Edge getEdgeS() {
		return this.edges[3];
	}
	
	/**
	 * @return edge the edge at the SouthWest Position
	 */
	public Edge getEdgeSW() {
		return this.edges[4];
	}

	/**
	 * @return edge the edge at the NorthWest Position
	 */
	public Edge getEdgeNW() {
		return this.edges[5];
	}
	
	/**
	 * @param vertex the vertex to set at the NorthEast Position
	 */
	public void setVertexNE(Vertex vertex) {
		this.verts[0] = vertex;
	}

	/**
	 * @param vertex the vertex to set at the East Position
	 */
	public void setVertexE(Vertex vertex) {
		this.verts[1] = vertex;
	}
	
	/**
	 * @param vertex the vertex to set at the SouthEast Position
	 */
	public void setVertexSE(Vertex vertex) {
		this.verts[2] = vertex;
	}

	/**
	 * @param vertex the vertex to set at the SouthWest Position
	 */
	public void setVertexSW(Vertex vertex) {
		this.verts[3] = vertex;
	}

	/**
	 * @param vertex the vertex to set at the West Position
	 */
	public void setVertexW(Vertex vertex) {
		this.verts[4] = vertex;
	}
	
	/**
	 * @param vertex the vertex to set at the NorthWest Position
	 */
	public void setVertexNW(Vertex vertex) {
		this.verts[5] = vertex;
	}

	/**
	 * @return vertex the vertex at the NorthEast Position
	 */
	public Vertex getVertexNE() {
		return this.verts[0];
	}

	/**
	 * @return vertex the vertex at the East Position
	 */
	public Vertex getVertexE() {
		return this.verts[1];
	}
	
	/**
	 * @return vertex the vertex at the SouthEast Position
	 */
	public Vertex getVertexSE() {
		return this.verts[2];
	}

	/**
	 * @return vertex the vertex at the SouthWest Position
	 */
	public Vertex getVertexSW() {
		return this.verts[3];
	}

	/**
	 * @return vertex the vertex at the West Position
	 */
	public Vertex getVertexW() {
		return this.verts[4];
	}
	
	/**
	 * @return vertex the vertex at the NorthWest Position
	 */
	public Vertex getVertexNW() {
		return this.verts[5];
	}

	/**
	 * @return the hexlocation
	 */
	public HexLocation getHexlocation() {
		return hexlocation;
	}

	/**
	 * @param hexlocation the hexlocation to set
	 */
	public void setHexlocation(HexLocation hexlocation) {
		this.hexlocation = hexlocation;
	}
}
