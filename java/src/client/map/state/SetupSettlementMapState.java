package client.map.state;

import client.data.RobPlayerInfo;
import client.main.ClientPlayer;
import client.map.MapController;
import client.modelfacade.CanModelFacade;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;

public class SetupSettlementMapState extends MapState {
	
	public SetupSettlementMapState(MapController mapController) {
		super(mapController);
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
		DoModelFacade.sole().doSetupSettlement(vertLoc);
		mapController.getView().placeSettlement(vertLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
	}

	public void placeCity(VertexLocation vertLoc) {
	}

	public void placeRobber(HexLocation hexLoc) {
	}

	public void robPlayer(RobPlayerInfo victim) {
	}
	
	public void playSoldierCard() {
	}
	
	public void playRoadBuildingCard() {	
	}
	
	public Boolean canCancelDrop() {
		return false;
	}
	
	public void cancelMove() {
	}
}
