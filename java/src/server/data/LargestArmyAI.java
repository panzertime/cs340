package server.data;

import java.util.HashMap;

import shared.model.Model;
import shared.model.board.edge.Edge;
import shared.model.board.hex.Hex;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.tiles.land.FieldHex;
import shared.model.board.hex.tiles.land.MountainHex;
import shared.model.board.hex.tiles.land.PastureHex;
import shared.model.board.piece.City;
import shared.model.board.piece.Settlement;
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;
import shared.model.hand.development.DevCardType;

public class LargestArmyAI extends AI {

	public String getAIType() { return "LargestArmy";}
	public LargestArmyAI() {
		
	}
	public LargestArmyAI(User userByName) {
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
		
		for (ResourceType type: ResourceType.values()) {
			int value = 0;
			for (int i = 0; i < game.getResourceAmount(type, playerIndex); i++) {
				
				if (i < discardNeeded) {
					discardNeeded--;
					value++;
				}
			}
			discardedCards.put(type, value);
		}
		if (game.canDiscardCard(discardedCards, playerIndex)) game.doDiscardCards(discardedCards, playerIndex);
		
	}

	@Override
	void AIPlay(Model game, int playerIndex) throws ViolatedPreconditionException {
		// TODO Auto-generated method stub
		ResourceType resourceNeeded = ResourceType.ORE;
		if (game.getResourceAmount(ResourceType.SHEEP, playerIndex) == 0)
			resourceNeeded = ResourceType.SHEEP;
		if (game.getResourceAmount(ResourceType.WHEAT, playerIndex) == 0)
			resourceNeeded = ResourceType.WHEAT;
		if (game.getResourceAmount(ResourceType.ORE, playerIndex) == 0)
			resourceNeeded = ResourceType.ORE;
		if (game.getResourceAmount(ResourceType.WOOD, playerIndex) > 4 && game.canMaritimeTrade(playerIndex, 4, ResourceType.WOOD, resourceNeeded)) game.doMaritimeTrade(4, ResourceType.WOOD, resourceNeeded, playerIndex);
		if (game.getResourceAmount(ResourceType.BRICK, playerIndex) > 4 && game.canMaritimeTrade(playerIndex, 4, ResourceType.BRICK, resourceNeeded))game.doMaritimeTrade(4, ResourceType.BRICK, resourceNeeded, playerIndex);

		if (game.hasDevCardEnabled(DevCardType.KNIGHT, playerIndex)) {
			this.AIKnight(game, playerIndex);
		}
		
		if (game.canBuyDevCard(playerIndex)) game.doBuyDevCard(playerIndex);
		
		if (game.canUseMonument(playerIndex)) game.doMonument(playerIndex);
		
		if (game.canFinishTurn(playerIndex)) game.doFinishTurn(playerIndex);
	}

	void AIKnight(Model game, int playerIndex) throws ViolatedPreconditionException {
		for (Integer i: game.getPlayerIndices())
		{
			if (i != playerIndex)
			{
				for (City c: game.getPlayerFromIndex(i).getCities())
				{
					if (c.isPlaced())
					{
						for (Hex h: c.getVertex().getAllHexes()) {
							if (h.isBuildable()) {
								if (game.canUseSoldier(playerIndex, h.getHexLocation(), i))
								{game.doSoldier(h.getHexLocation(), i, playerIndex);
								return;}
							}
						}
					}
				}
				for (Settlement s: game.getPlayerFromIndex(i).getSettlements())
				{
					if (s.isPlaced())
					{
						for (Hex h: s.getVertex().getAllHexes()) {
							if (h.isBuildable()) {
								if (game.canUseSoldier(playerIndex, h.getHexLocation(), i))
								{	game.doSoldier(h.getHexLocation(), i, playerIndex);
								}
								return;}
							}
						}
					}
				}
			}
		Integer upperLimit = new Integer(2);
		Integer lowerLimit = new Integer(0);
		for (Integer x = new Integer(-2); x <=2; x++)
		{
			for (Integer y = lowerLimit; y <= upperLimit; y++)
			{
				if (game.canUseSoldier(playerIndex, new HexLocation(x, y), -1))
					{game.doSoldier(new HexLocation(x, y), -1, playerIndex);
					return;}
			}
			if (x < 0) lowerLimit--;
			if (x >= 0) upperLimit--;
		}

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
						if (h instanceof MountainHex || h instanceof FieldHex || h instanceof PastureHex)
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
						if (h instanceof MountainHex || h instanceof FieldHex || h instanceof PastureHex)
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
				if (game.canSetupRoad(playerIndex, e.getEdgeLocation()))
				{	game.doBuildRoad(true, e.getEdgeLocation(), playerIndex);
					return;}
			}
		}
	}

	}


}
