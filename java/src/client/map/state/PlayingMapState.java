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

public class PlayingMapState extends MapState {
	
	public PlayingMapState(MapController mapController) {
		super(mapController);
	}
	
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return CanModelFacade.sole().canBuildRoad(edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return CanModelFacade.sole().canBuildSettlement(vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return CanModelFacade.sole().canBuildCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return CanModelFacade.sole().canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		DoModelFacade.sole().doBuildRoad(edgeLoc);
		mapController.getView().placeRoad(edgeLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
	}

	public void placeSettlement(VertexLocation vertLoc) {
		DoModelFacade.sole().doBuildSettlement(vertLoc);
		mapController.getView().placeSettlement(vertLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
	}

	public void placeCity(VertexLocation vertLoc) {
		DoModelFacade.sole().doBuildCity(vertLoc);
		mapController.getView().placeCity(vertLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
	}

	public void placeRobber(HexLocation hexLoc) {
		mapController.getView().placeRobber(hexLoc);
		mapController.getRobView().showModal();
	}

	public void robPlayer(RobPlayerInfo victim) {
		DoModelFacade.sole().doRobPlayer(GetModelFacade.sole().getRobberLocation(), victim.getPlayerIndex());
	}
	
	public Boolean canCancelDrop() {
		return true;
	}
}
