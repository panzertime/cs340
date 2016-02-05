package shared.models.board.piece;

import shared.models.Player;
import shared.models.board.vertex.Vertex;
import shared.models.hand.ResourceType;
import shared.models.hand.exceptions.ResourceException;

public abstract class Building {
	
	protected Player owner;
	protected Vertex vertex;
	
	public Building (Player owner) throws NullPlayerException {
		if (owner == null)
			throw new NullPlayerException();
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
}
