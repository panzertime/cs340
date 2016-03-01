package client.map;

import client.base.Controller;
import client.data.RobPlayerInfo;
import client.main.ClientPlayer;
import client.map.pseudo.PseudoCity;
import client.map.pseudo.PseudoHex;
import client.map.pseudo.PseudoRoad;
import client.map.pseudo.PseudoSettlement;
import client.map.state.MapState;
import client.map.state.PlayingMapState;
import client.map.state.RobbingMapState;
import client.map.state.SetupRoadMapState;
import client.map.state.SetupSettlementMapState;
import client.map.state.WaitingMapState;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.logger.Log;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.PieceType;
import shared.model.board.vertex.VertexLocation;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements GetModelFacadeListener, IMapController {
	
	
	private MapState state;
	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView) {
		super(view);
		GetModelFacade.registerListener(this);
		state = new WaitingMapState(this);
		setRobView(robView);
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	public IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	protected void updateView() {
		for (PseudoHex hex : GetModelFacade.sole().getPseudoHexes()) {
			getView().addHex(hex.getHexLoc(), hex.getHexType());
			if (hex.getProductionNum() != null)
				getView().addNumber(hex.getHexLoc(), hex.getProductionNum());
			if (hex.getPortType() != null) 
				getView().addPort(hex.getPortLocation(), hex.getPortType());
		}
		
		for (PseudoRoad road : GetModelFacade.sole().getPseudoRoads()) {
			getView().placeRoad(road.getEdgeLoc(), road.getColor());
		}
		
		for (PseudoSettlement settlement : GetModelFacade.sole().getPseudoSettlements()) {
			getView().placeSettlement(settlement.getVertLoc(), settlement.getColor());
		}
		
		for (PseudoCity city : GetModelFacade.sole().getPseudoCities()) {
			getView().placeCity(city.getVertLoc(), city.getColor());
		}
		
		getView().placeRobber(GetModelFacade.sole().getRobberLocation());
	}
	
	protected void updateState() {
		if (GetModelFacade.sole().isStateWaiting()) {
			state = new WaitingMapState(this);
			Log.debug("MapController.state - Waiting");
		} else if (GetModelFacade.sole().isStateSetupSettlement()) {
			if (!(state instanceof SetupSettlementMapState))
				state = new SetupSettlementMapState(this);
			Log.debug("MapController.state - Setup:Settlement");
		} else if (GetModelFacade.sole().isStateSetupRoad()) {
			if (!(state instanceof SetupRoadMapState))
				state = new SetupRoadMapState(this);
			Log.debug("MapController.state - Setup:Road");
		} else if (GetModelFacade.sole().isStateRobbing()) {
			state = new RobbingMapState(this);
			Log.debug("MapController.state - Robbing");
		} else if (GetModelFacade.sole().isStatePlaying()) {
			state = new PlayingMapState(this);
			Log.debug("MapController.state - Playing");
		}
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return state.canPlaceRoad(edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return state.canPlaceSettlement(vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return state.canPlaceCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return state.canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		state.placeRoad(edgeLoc);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		state.placeSettlement(vertLoc);
	}

	public void placeCity(VertexLocation vertLoc) {
		state.placeCity(vertLoc);
	}

	public void placeRobber(HexLocation hexLoc) {
		state.placeRobber(hexLoc);
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, ClientPlayer.sole().getUserColor(), state.canCancelDrop());
	}
	
	public void cancelMove() {
		
	}
	
	/**
	 * 
	 */
	public void playSoldierCard() {
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		state.robPlayer(victim);
	}

	@Override
	public void update() {
		updateView();
		updateState();
	}
	
}

