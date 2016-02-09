package shared.models.board.piece;

import org.json.simple.JSONObject;

import shared.models.Player;
import shared.models.board.hex.HexLocation;
import shared.models.board.hex.tiles.water.PortType;
import shared.models.board.vertex.Vertex;
import shared.models.board.vertex.VertexDirection;
import shared.models.exceptions.BadJSONException;
import shared.models.hand.ResourceType;
import shared.models.hand.exceptions.ResourceException;

public abstract class Building {
	
	protected Player owner;
	protected Vertex vertex;
	
	public Building (Player owner) throws IllegalArgumentException {
		if (owner == null)
			throw new IllegalArgumentException();
		this.owner = owner;
	}
	
	/**
	 * @return the vertex
	 */
	public Vertex getVertex() {
		return vertex;
	}
	
	/**
	 * @param vertex the vertex to set
	 */
	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}
	
	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	public Boolean isPlaced() {
		if (vertex == null)
			return false;
		return true;
	}
	
	/**
	 * @param type the resource to generate
	 * @throws ResourceException 
	 */
	public abstract void produce(ResourceType type) throws ResourceException;
	
	public Boolean hasPort(PortType portType) {
		if (!isPlaced())
			return false;
		return vertex.hasPort(portType);
	}
	
	public boolean equals(JSONObject jsonHex) {
		if (!isPlaced())
			return false;
		JSONObject jsonHexLoc = (JSONObject) jsonHex.get("location");
		HexLocation hexLoc = vertex.getVertexLocation().getHexLoc();
		VertexDirection vertexDir = vertex.getVertexLocation().getDir();
		if (hexLoc.getX() != (Long) jsonHexLoc.get("x") 
				&& hexLoc.getNeighborLoc(vertexDir.toRightEdge()).getX() != (Long) jsonHexLoc.get("x")
				&& hexLoc.getNeighborLoc(vertexDir.toLeftEdge()).getX() != (Long) jsonHexLoc.get("x"))
			return false;
		if (hexLoc.getY() != (Long) jsonHexLoc.get("y")
				&& hexLoc.getNeighborLoc(vertexDir.toRightEdge()).getY() != (Long) jsonHexLoc.get("y")
				&& hexLoc.getNeighborLoc(vertexDir.toLeftEdge()).getY() != (Long) jsonHexLoc.get("y"))
			return false;
		String direction = (String) jsonHexLoc.get("direction");
		try {
			if (vertexDir != VertexDirection.fromJSON(direction) 
					&& vertexDir.toRight().toRight() != VertexDirection.fromJSON(direction)
					&& vertexDir.toLeft().toLeft() != VertexDirection.fromJSON(direction))
				return false;
		} catch (BadJSONException e) {
			// this will never happen, because the json will always be good
			// during testing
		}
		return true;
	}
}
