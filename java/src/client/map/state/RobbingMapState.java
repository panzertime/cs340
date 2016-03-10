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

public class RobbingMapState extends MapState {
	
	public HexLocation robberLoc;
	public RobbingMapState(MapController mapController) {
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
		return CanModelFacade.sole().canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
	}

	public void placeSettlement(VertexLocation vertLoc) {
	}

	public void placeCity(VertexLocation vertLoc) {
	}

	public void placeRobber(HexLocation hexLoc) {
		robberLoc = hexLoc;
		mapController.getView().placeRobber(hexLoc);
		mapController.getRobView().setPlayers(GetModelFacade.sole().getRobbablePlayer(hexLoc));
		mapController.getRobView().showModal();
	}

	public void robPlayer(RobPlayerInfo victim) {
		if (victim.getPlayerIndex() != ClientPlayer.sole().getUserIndex()) {
			DoModelFacade.sole().doRobPlayer(robberLoc, victim.getPlayerIndex());
		} else {
			DoModelFacade.sole().doRobPlayer(robberLoc, -1);
		}
		mapController.getRobView().setPlayers(null);
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
