package shared.models.board.hex;

import shared.models.board.edge.BadEdgeDirectionException;
import shared.models.board.edge.Edge;
import shared.models.board.edge.EdgeDirection;
import shared.models.board.vertex.BadVertexDirectionException;
import shared.models.board.vertex.Vertex;
import shared.models.board.vertex.VertexDirection;

public abstract class Hex {
	
	protected HexType hexType;
	private HexLocation hexlocation;
	private Edge[] edges = new Edge[6];
	protected Vertex[] verts = new Vertex[6];

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
	public void setEdge(EdgeDirection dir, Edge edge) throws BadEdgeDirectionException {
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
		throw new BadEdgeDirectionException();
	}

	/**
	 * @param dir the direction of the edge to get
	 * @param edge the edge to get
	 * @throws BadEdgeDirectionException
	 */
	public Edge getEdge(EdgeDirection dir) throws BadEdgeDirectionException {
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
		throw new BadEdgeDirectionException();
	}

	/**
	 * @param dir the direction of the vertex to set
	 * @param edge the vertex to set
	 * @throws BadEdgeDirectionException
	 */
	public void setVertex(VertexDirection dir, Vertex vertex) throws BadVertexDirectionException {
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
		throw new BadVertexDirectionException();
	}

	/**
	 * @param dir the direction of the vertex to get
	 * @param edge the vertex to get
	 * @throws BadEdgeDirectionException
	 */
	public Vertex getVertex(VertexDirection dir) throws BadVertexDirectionException {
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
		throw new BadVertexDirectionException();
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