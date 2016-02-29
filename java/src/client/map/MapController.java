package client.map;

import client.base.Controller;
import client.data.RobPlayerInfo;
import client.map.pseudo.PseudoCity;
import client.map.pseudo.PseudoHex;
import client.map.pseudo.PseudoRoad;
import client.map.pseudo.PseudoSettlement;
import client.map.state.MapState;
import client.map.state.PlayingMapState;
import client.map.state.SetupMapState;
import client.map.state.WaitingMapState;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.logger.Log;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.PieceType;
import shared.model.board.vertex.VertexLocation;
import shared.model.definitions.CatanColor;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements GetModelFacadeListener, IMapController {
	
	
	private MapState state;
	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView) {
		super(view);
		GetModelFacade.registerListener(this);
		setRobView(robView);
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
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
	}
	
	protected void updateState() {
		if (GetModelFacade.sole().isStateWaiting())
			state = new WaitingMapState(this);
		else if (GetModelFacade.sole().isStateSetup())
			state = new SetupMapState(this);
		else if (GetModelFacade.sole().isStatePlaying())
			state = new PlayingMapState(this);
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
		getView().placeRobber(hexLoc);
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}

	@Override
	public void update() {
		Log.debug("Updated Map!");
		updateView();
		updateState();
	}
	
}

