package client.map.state;

import client.map.MapController;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;

public abstract class MapState {
	
	protected MapController mapController;
	
	public MapState(MapController mapController) {
		this.mapController = mapController;
	}
	
	public abstract boolean canPlaceRoad(EdgeLocation edgeLoc);

	public abstract boolean canPlaceSettlement(VertexLocation vertLoc);

	public abstract boolean canPlaceCity(VertexLocation vertLoc);

	public abstract boolean canPlaceRobber(HexLocation hexLoc);

	public abstract void placeRoad(EdgeLocation edgeLoc);

	public abstract void placeSettlement(VertexLocation vertLoc);

	public abstract void placeCity(VertexLocation vertLoc);
	
	public abstract Boolean canCancelDrop();
}
