package shared.models.board.piece;

import shared.models.Player;
import shared.models.board.edge.Edge;

public class Road {

	protected Player owner;
	protected Edge edge;
	
	public Road (Player owner) throws NullPlayerException {
		if (owner == null)
			throw new NullPlayerException();
		this.owner = owner;
	}
	
	/**
	 * @return the edge
	 */
	public Edge getEdge() {
		return edge;
	}
	
	/**
	 * @param edge the edge to set
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
}
