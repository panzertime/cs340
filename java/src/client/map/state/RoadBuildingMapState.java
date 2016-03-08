package client.map.state;

import client.data.RobPlayerInfo;
import client.main.ClientPlayer;
import client.map.MapController;
import client.modelfacade.CanModelFacade;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.PieceType;
import shared.model.board.vertex.VertexLocation;

public class RoadBuildingMapState extends MapState {
	
	private EdgeLocation edgeLocPrevious;
	
	public RoadBuildingMapState(MapController mapController) {
		super(mapController);
	}
	
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		if (edgeLocPrevious == null) {
			return CanModelFacade.sole().canSetupRoad(edgeLoc);
		} else {
			return CanModelFacade.sole().canUseRoadBuilding(edgeLocPrevious, edgeLoc);
		}
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
		if (edgeLocPrevious == null) {
			edgeLocPrevious = edgeLoc;
			mapController.getView().placeRoad(edgeLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
			mapController.startMove(PieceType.ROAD, true, true);
		} else {
			DoModelFacade.sole().doUseRoadBuilding(edgeLocPrevious, edgeLoc);
			mapController.getView().placeRoad(edgeLoc, GetModelFacade.sole().getPlayerColor(ClientPlayer.sole().getUserIndex()));
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
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
		mapController.startMove(PieceType.ROAD, true, true);
	}
	
	public Boolean canCancelDrop() {
		return true;
	}
	
	public void cancelMove() {
		if (edgeLocPrevious != null) {
			DoModelFacade.sole().doUseRoadBuilding(edgeLocPrevious, edgeLocPrevious);
		}
		mapController.setState(new PlayingMapState(mapController));
	}
}
