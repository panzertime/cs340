package client.map.state;

import client.main.ClientPlayer;
import client.map.MapController;
import client.modelfacade.CanModelFacade;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.PieceType;
import shared.model.board.vertex.VertexLocation;

public class SetupRoadMapState extends MapState {
	
	public SetupRoadMapState(MapController mapController) {
		super(mapController);
		mapController.startMove(PieceType.ROAD, true, false);
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return CanModelFacade.sole().canSetupRoad(edgeLoc);
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
		DoModelFacade.sole().doSetupRoad(edgeLoc);
		mapController.getView().placeRoad(edgeLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
		DoModelFacade.sole().doEndTurn();
	}

	public void placeSettlement(VertexLocation vertLoc) {
	}

	public void placeCity(VertexLocation vertLoc) {
	}
	
	public Boolean canCancelDrop() {
		return false;
	}

}
