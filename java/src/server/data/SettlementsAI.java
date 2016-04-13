package server.data;

import java.util.HashMap;

import shared.model.Model;
import shared.model.board.edge.Edge;
import shared.model.board.hex.Hex;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.tiles.land.ClayHex;
import shared.model.board.hex.tiles.land.FieldHex;
import shared.model.board.hex.tiles.land.ForestHex;
import shared.model.board.hex.tiles.land.MountainHex;
import shared.model.board.hex.tiles.land.PastureHex;
import shared.model.board.piece.Road;
import shared.model.board.piece.Settlement;
import shared.model.board.vertex.Vertex;
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class SettlementsAI extends AI {

	public String getAIType() { return "Settlements";}
	public SettlementsAI() {
		
	}
	public SettlementsAI(User userByName) {
		this.setUserID(userByName.getID());
		this.setPassword(userByName.getPassword());
		this.setUsername(userByName.getUsername());
	}
	
	@Override
	void AIDiscard(Model game, int playerIndex) throws ViolatedPreconditionException {
		int totalcards = 0;
		for (ResourceType type: ResourceType.values()) {
			totalcards += game.getResourceAmount(type, playerIndex);
		}
		int discardNeeded = totalcards / 2;
		HashMap<ResourceType, Integer> discardedCards = new HashMap<ResourceType, Integer>();
		
		for (int j = ResourceType.values().length - 1; j >= 0; j--) {
			int value = 0;
			for (int i = 0; i < game.getResourceAmount(ResourceType.values()[j], playerIndex); i++) {
				if (i < discardNeeded) {
					discardNeeded--;
					value++;
				}
			}
			discardedCards.put(ResourceType.values()[j], value);
		}
		game.doDiscardCards(discardedCards, playerIndex);
		
	}

	@Override
	void AIPlay(Model game, int playerIndex) throws ViolatedPreconditionException {
		// TODO Auto-generated method stub
		ResourceType resourceNeeded = ResourceType.WOOD;
		if (game.getResourceAmount(ResourceType.SHEEP, playerIndex) == 0)
			resourceNeeded = ResourceType.SHEEP;
		if (game.getResourceAmount(ResourceType.WHEAT, playerIndex) == 0)
			resourceNeeded = ResourceType.WHEAT;
		if (game.getResourceAmount(ResourceType.BRICK, playerIndex) == 0)
			resourceNeeded = ResourceType.BRICK;
		if (game.getResourceAmount(ResourceType.WOOD, playerIndex) == 0)
			resourceNeeded = ResourceType.WOOD;
		if (game.getResourceAmount(ResourceType.ORE, playerIndex) > 4 && game.canMaritimeTrade(playerIndex, 4, ResourceType.ORE, resourceNeeded)) game.doMaritimeTrade(4, ResourceType.ORE, resourceNeeded, playerIndex);
		
		
		if (game.canBuySettlement(playerIndex)) {
			for (Road r: game.getPlayerFromIndex(playerIndex).getRoads()) {
			if (r.isPlaced()) {
				for (Vertex v: r.getEdge().getAllVertices()) {
				if (game.canBuildSettlement(playerIndex, v.getVertexLocation()))
					game.doBuildSettlement(false, v.getVertexLocation(), playerIndex);
			
			}
			}
			}
		}
		if (game.canBuyRoad(playerIndex)) {
			for (Road r: game.getPlayerFromIndex(playerIndex).getRoads()) {
			if (r.isPlaced()) {
				for (Vertex v: r.getEdge().getAllVertices()) {
				for (Edge e: v.getAllEdges())
				{
					if (game.canBuildRoad(playerIndex, e.getEdgeLocation()))
					{	game.doBuildRoad(false, e.getEdgeLocation(), playerIndex);
					
					}
				}
			}
			}
			}
			}
		
		if (game.canFinishTurn(playerIndex)) game.doFinishTurn(playerIndex);
	}

	@Override
	void AISetupSettlement(Model game, int playerIndex) throws ViolatedPreconditionException {
		// TODO Auto-generated method stub
		Integer upperLimit = new Integer(2);
		Integer lowerLimit = new Integer(0);
		for (Integer x = new Integer(-2); x <=2; x++)
		{
			for (Integer y = lowerLimit; y <= upperLimit; y++)
			{
				HexLocation hexLoc = new HexLocation(x, y);
				for (VertexDirection dir: VertexDirection.values())
				{
					VertexLocation v = new VertexLocation(hexLoc, dir);
					int howmany = 0;
					for (Hex h: game.getBoard().getVertexAt(v).getAllHexes()) {
						if (h instanceof ForestHex || h instanceof ClayHex || h instanceof FieldHex || h instanceof PastureHex)
							howmany++;
					}
					if (howmany >= 3) {
					if (game.canSetupSettlement(playerIndex, v)) {
						game.doBuildSettlement(true, v, playerIndex);
						return;
					}}
				}

			}
			if (x < 0) lowerLimit--;
			if (x >= 0) upperLimit--;
		}
		upperLimit = new Integer(2);
		lowerLimit = new Integer(0);
		for (Integer x = new Integer(-2); x <=2; x++)
		{
			for (Integer y = lowerLimit; y <= upperLimit; y++)
			{
				HexLocation hexLoc = new HexLocation(x, y);
				for (VertexDirection dir: VertexDirection.values())
				{
					VertexLocation v = new VertexLocation(hexLoc, dir);
					int howmany = 0;
					for (Hex h: game.getBoard().getVertexAt(v).getAllHexes()) {
						if (h instanceof ForestHex || h instanceof ClayHex || h instanceof FieldHex || h instanceof PastureHex)
							howmany++;
					}
					if (howmany >= 2) {
					if (game.canSetupSettlement(playerIndex, v)) {
						game.doBuildSettlement(true, v, playerIndex);
						return;
					}}
				}

			}
			if (x < 0) lowerLimit--;
			if (x >= 0) upperLimit--;
		}

		
		super.AISetupSettlement(game, playerIndex);
	}

	@Override
	void AISetupRoad(Model game, int playerIndex) throws ViolatedPreconditionException {
		// TODO Auto-generated method stub
		for (Settlement s: game.getPlayerFromIndex(playerIndex).getSettlements()) {
		if (s.isPlaced()) {
			for (Edge e: s.getVertex().getAllEdges())
			{
				if (game.canSetupRoad(playerIndex, e.getEdgeLocation())) {
					game.doBuildRoad(true, e.getEdgeLocation(), playerIndex);
					return; }
			}
		}
	}
	}
	
	
}
