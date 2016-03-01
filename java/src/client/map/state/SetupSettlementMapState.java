package client.map.state;

import client.main.ClientPlayer;
import client.map.MapController;
import client.modelfacade.CanModelFacade;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import shared.logger.Log;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.PieceType;
import shared.model.board.vertex.VertexLocation;

public class SetupSettlementMapState extends MapState {
	
	public SetupSettlementMapState(MapController mapController) {
		super(mapController);
		mapController.startMove(PieceType.SETTLEMENT, true, true);
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return false;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return CanModelFacade.sole().canSetupSettlement(vertLoc);
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
		Log.debug("BUILD THE DAMN SETTLEMENT");
		DoModelFacade.sole().doSetupSettlement(vertLoc);
		mapController.getView().placeSettlement(vertLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
	}

	public void placeCity(VertexLocation vertLoc) {
	}
	
	public Boolean canCancelDrop() {
		return false;
	}

}
