package client.map.state;

import client.main.ClientPlayer;
import client.map.MapController;
import client.modelfacade.CanModelFacade;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;

public class SetupMapState extends MapState {
	
	public SetupMapState(MapController mapController) {
		super(mapController);
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return CanModelFacade.sole().canSetupRoad(edgeLoc);
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
		DoModelFacade.sole().doBuildRoad(edgeLoc);
		mapController.getView().placeRoad(edgeLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
	}

	public void placeSettlement(VertexLocation vertLoc) {
		DoModelFacade.sole().doBuildSettlement(vertLoc);
		mapController.getView().placeSettlement(vertLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
	}

	public void placeCity(VertexLocation vertLoc) {
	}

}
