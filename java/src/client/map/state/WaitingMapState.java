package client.map.state;

import client.data.RobPlayerInfo;
import client.map.MapController;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;

public class WaitingMapState  extends MapState{
	
	public WaitingMapState(MapController mapController) {
		super(mapController);
	}
	
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return false;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return false;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return false;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return false;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
	}

	public void placeSettlement(VertexLocation vertLoc) {
	}

	public void placeCity(VertexLocation vertLoc) {
	}

	public void placeRobber(HexLocation hexLoc) {
	}

	public void robPlayer(RobPlayerInfo victim) {
	}
	
	public Boolean canCancelDrop() {
		return true;
	}
}
