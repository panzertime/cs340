package server.data;

import java.util.HashMap;

import shared.model.Model;
import shared.model.board.edge.Edge;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.Settlement;
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
		game.doFinishTurn(playerIndex);
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
					if (game.canSetupSettlement(playerIndex, v)) {
						{	game.doBuildSettlement(true, v, playerIndex);
						return;}
					}
				}

			}
			if (x < 0) lowerLimit--;
			if (x >= 0) upperLimit--;
		}
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
