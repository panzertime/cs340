package shared.models.board.peice;

import shared.models.Player;
import shared.models.board.vertex.Vertex;
import shared.models.hand.ResourceException;
import shared.models.hand.ResourceType;

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
	
	/**
	 * @param type the resource to generate
	 * @throws ResourceException 
	 */
	public abstract void generateResources(ResourceType type) throws ResourceException;
}
