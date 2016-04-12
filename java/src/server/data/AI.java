package server.data;

import java.util.HashMap;
import java.util.Random;

import client.modelfacade.DoModelFacade;
import shared.model.Model;
import shared.model.board.edge.Edge;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.Settlement;
import shared.model.board.vertex.Vertex;
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class AI extends User {

	int index;
	
	public AI() {
		
	}
	
	public void setIndex(int playerIndex) {index = playerIndex;}
	
	public int getIndex() { return index; }
	
	//added as Model listener
	//notified when Model initiated or version updated
	public void play(Model game) throws ViolatedPreconditionException {
		if (game.isStateDiscarding()) {
			
			this.AIDiscard(game);
		}
		if (game.hasTradeOffer()) { this.AITradeOffer(game);}
		if (game.isTurn(index)) {
			if (game.isStateRolling()){
				Random rand = new Random();
				int n = 0;
				n = rand.nextInt(6) + 1;
				n += rand.nextInt(6) + 1;
				game.doRollNumber(n, this.getIndex());
				this.play(game);
			}
			else if (game.isStateRobbing()) {
				this.AIRob(game);
			}
			else if (game.isStatePlaying()) {
				this.AIPlay(game);
			}
			else if (game.isStateSetupSettlement())
			{
				this.AISetupSettlement(game);
			}
			else if (game.isStateSetupRoad()){ 
				this.AISetupRoad(game);
			}
		}
		
		
	}
	
	public void AITradeOffer(Model game) throws ViolatedPreconditionException {
		game.doAcceptTrade(false, this.getIndex());
		
	}
	public void AIDiscard(Model game) throws ViolatedPreconditionException {
		int totalcards = 0;
		for (ResourceType type: ResourceType.values()) {
			totalcards += game.getResourceAmount(type, this.getIndex());
		}
		int discardNeeded = totalcards / 2;
		HashMap<ResourceType, Integer> discardedCards = new HashMap<ResourceType, Integer>();
		for (ResourceType type: ResourceType.values()) {
			int value = 0;
			for (int i = 0; i < game.getResourceAmount(type, this.getIndex()); i++) {
				if (i < discardNeeded) {
					discardNeeded--;
					value++;
				}
			}
			discardedCards.put(type, value);
		}
		game.doDiscardCards(discardedCards, this.getIndex());
		for (int i = 0; i < discardNeeded; i++) {
			
		}
	}
	
	public void AIPlay(Model game) throws ViolatedPreconditionException {
		game.doFinishTurn(this.getIndex());
		//this.play();
	}
	
	public void AISetupSettlement(Model game) throws ViolatedPreconditionException {
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
					if (game.canSetupSettlement(this.getIndex(), v)) {
						game.doBuildSettlement(true, v, this.getIndex());
					}
				}

			}
			if (x < 0) lowerLimit--;
			if (x >= 0) upperLimit--;
		}

		this.play(game);
	}
	
	public void AISetupRoad(Model game) throws ViolatedPreconditionException {
		for (Settlement s: game.getPlayerFromIndex(this.getIndex()).getSettlements()) {
			if (s.isPlaced()) {
				for (Edge e: s.getVertex().getAllEdges())
				{
					if (game.canSetupRoad(this.getIndex(), e.getEdgeLocation()))
						game.doBuildRoad(true, e.getEdgeLocation(), this.getIndex());
				}
			}
		}
		this.play(game);
	}
	
	public void AIRob(Model game) throws ViolatedPreconditionException {
		Integer upperLimit = new Integer(2);
		Integer lowerLimit = new Integer(0);
		for (Integer x = new Integer(-2); x <=2; x++)
		{
			for (Integer y = lowerLimit; y <= upperLimit; y++)
			{
				if (game.canPlaceRobber(this.getIndex(), new HexLocation(x, y)))
					game.doRobPlayer(new HexLocation(x, y), -1, this.getIndex());
			}
			if (x < 0) lowerLimit--;
			if (x >= 0) upperLimit--;
		}

		this.play(game);	}

}
