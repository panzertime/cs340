package client.map.pseudo;

import shared.model.board.vertex.VertexLocation;
import shared.model.definitions.CatanColor;

public class PseudoCity {
	
	public PseudoCity(VertexLocation vertLoc, CatanColor color) {
		this.vertLoc = vertLoc;
		this.color = color;
	}
	
	private VertexLocation vertLoc;
	private CatanColor color;
	
	/**
	 * @return the vertLoc
	 */
	public VertexLocation getVertLoc() {
		return vertLoc;
	}
	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
}
