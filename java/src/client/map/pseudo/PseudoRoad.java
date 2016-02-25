package client.map.pseudo;

import shared.model.board.edge.EdgeLocation;
import shared.model.definitions.CatanColor;

public class PseudoRoad {
	
	public PseudoRoad(EdgeLocation edgeLoc, CatanColor color) {
		this.edgeLoc = edgeLoc;
		this.color = color;
	}
	
	private EdgeLocation edgeLoc;
	private CatanColor color;
	
	/**
	 * @return the edgeLoc
	 */
	public EdgeLocation getEdgeLoc() {
		return edgeLoc;
	}
	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
}
