package shared.model.board.piece;

import org.json.simple.JSONObject;

import shared.logger.Log;
import shared.model.Player;
import shared.model.board.edge.Edge;
import shared.model.board.edge.EdgeDirection;
import shared.model.board.hex.HexLocation;
import shared.model.exceptions.BadJSONException;

public class Road {

	protected Player owner;
	protected Edge edge;

	public Road(Player owner) throws IllegalArgumentException {
		if (owner == null)
			throw new IllegalArgumentException();
		this.owner = owner;
	}

	/**
	 * @return the edge
	 */
	public Edge getEdge() {
		return edge;
	}

	/**
	 * @param edge
	 *            the edge to set
	 */
	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	public Boolean isPlaced() {
		if (edge == null)
			return false;
		return true;
	}

	public boolean equals(JSONObject jsonHex) {
		if (!isPlaced())
			return false;
		JSONObject jsonHexLoc = (JSONObject) jsonHex.get("location");
		HexLocation hexLoc = edge.getEdgeLocation().getHexLoc();
		EdgeDirection edgeDir = edge.getEdgeLocation().getDir();
		if (hexLoc.getX() != (Long) jsonHexLoc.get("x") && hexLoc.getNeighborLoc(edgeDir).getX() != (Long) jsonHexLoc.get("x"))
			return false;
		if (hexLoc.getY() != (Long) jsonHexLoc.get("y") && hexLoc.getNeighborLoc(edgeDir).getY() != (Long) jsonHexLoc.get("y"))
			return false;
		String direction = (String) jsonHexLoc.get("direction");
		try {
			if (edgeDir != EdgeDirection.fromJSON(direction) && edgeDir.toOpposite() != EdgeDirection.fromJSON(direction))
				return false;
		} catch (BadJSONException e) {
			// this will never happen, because the json will always be good
			// during testing
		}
		return true;
	}
	
	public boolean marked = false;
	
	public boolean isMarked()
	{
		return marked;
	}
	public void setMarked(boolean mark)
	{
		this.marked = mark;
	}
}
