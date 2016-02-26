package client.map;

import client.base.Controller;
import client.data.RobPlayerInfo;
import client.map.pseudo.PseudoCity;
import client.map.pseudo.PseudoHex;
import client.map.pseudo.PseudoRoad;
import client.map.pseudo.PseudoSettlement;
import client.modelfacade.CanModelFacade;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.PieceType;
import shared.model.board.vertex.VertexLocation;
import shared.model.definitions.CatanColor;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements GetModelFacadeListener, IMapController {
	
	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
		
		initFromModel();
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
	
	protected void initFromModel() {
		
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
		//CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex);
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		DoModelFacade.sole().doBuildRoad(edgeLoc);
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		DoModelFacade.sole().doBuildSettlement(vertLoc);
		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		DoModelFacade.sole().doBuildCity(vertLoc);
		getView().placeCity(vertLoc, CatanColor.ORANGE);
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
		// TODO Auto-generated method stub
		
	}
	
}

