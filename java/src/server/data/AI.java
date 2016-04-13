package server.data;

import java.util.HashMap;
import java.util.Random;

import client.modelfacade.DoModelFacade;
import shared.model.Model;
import shared.model.board.edge.Edge;
import shared.model.board.hex.Hex;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.City;
import shared.model.board.piece.Settlement;
import shared.model.board.vertex.Vertex;
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public abstract class AI extends User {

	public AI() {
		
	}
	
	
	public abstract String getAIType();
	//added as Model listener
	//notified when Model initiated or version updated
	public void play(Model game, int playerIndex) throws ViolatedPreconditionException {
		if (game.isStateDiscarding()) {
			
			this.AIDiscard(game, playerIndex);
		}
		if (game.hasTradeOffer()) {game.doAcceptTrade(false, playerIndex);}
		if (game.isTurn(playerIndex)) {
			if (game.isStateRolling()){
				Random rand = new Random();
				int n = 0;
				n = rand.nextInt(6) + 1;
				n += rand.nextInt(6) + 1;
				game.doRollNumber(n, playerIndex);
				this.play(game, playerIndex);
			}
			else if (game.isStateRobbing()) {
				this.AIRob(game, playerIndex);
			}
			else if (game.isStatePlaying()) {
				this.AIPlay(game, playerIndex);
			}
			else if (game.isStateSetupSettlement())
			{
				this.AISetupSettlement(game, playerIndex);
			}
			else if (game.isStateSetupRoad()){ 
				this.AISetupRoad(game, playerIndex);
			}
		}
		
		
	}

	abstract void AIDiscard(Model game, int playerIndex) throws ViolatedPreconditionException;
	
	public void AIRob(Model game, int playerIndex) throws ViolatedPreconditionException {
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
								if (game.canRobPlayerFrom(h.getHexLocation(), playerIndex, i))
								{game.doRobPlayer(h.getHexLocation(), i, playerIndex);
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
								if (game.canRobPlayerFrom(h.getHexLocation(), playerIndex, i))
								{game.doRobPlayer(h.getHexLocation(), i, playerIndex);
								return;}
							}
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
			if (game.canPlaceRobber(playerIndex, new HexLocation(x, y)))
				{game.doRobPlayer(new HexLocation(x, y), -1, playerIndex);
				return;}
		}
		if (x < 0) lowerLimit--;
		if (x >= 0) upperLimit--;
	}
	}
	
	abstract void AIPlay(Model game, int playerIndex) throws ViolatedPreconditionException;

	void AISetupSettlement(Model game, int playerIndex) throws ViolatedPreconditionException {
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
	abstract void AISetupRoad(Model game, int playerIndex) throws ViolatedPreconditionException; 
	

//DUMMY AI FUNCTIONS	
//	public void AIDiscard(Model game) throws ViolatedPreconditionException {
//		int totalcards = 0;
//		for (ResourceType type: ResourceType.values()) {
//			totalcards += game.getResourceAmount(type, this.getIndex());
//		}
//		int discardNeeded = totalcards / 2;
//		HashMap<ResourceType, Integer> discardedCards = new HashMap<ResourceType, Integer>();
//		for (ResourceType type: ResourceType.values()) {
//			int value = 0;
//			for (int i = 0; i < game.getResourceAmount(type, this.getIndex()); i++) {
//				if (i < discardNeeded) {
//					discardNeeded--;
//					value++;
//				}
//			}
//			discardedCards.put(type, value);
//		}
//		game.doDiscardCards(discardedCards, this.getIndex());
//	}
//	
//	public void AIPlay(Model game) throws ViolatedPreconditionException {
//		game.doFinishTurn(this.getIndex());
//		//this.play();
//	}
//	
//	public void AISetupSettlement(Model game) throws ViolatedPreconditionException {
//		Integer upperLimit = new Integer(2);
//		Integer lowerLimit = new Integer(0);
//		for (Integer x = new Integer(-2); x <=2; x++)
//		{
//			for (Integer y = lowerLimit; y <= upperLimit; y++)
//			{
//				HexLocation hexLoc = new HexLocation(x, y);
//				for (VertexDirection dir: VertexDirection.values())
//				{
//					VertexLocation v = new VertexLocation(hexLoc, dir);
//					if (game.canSetupSettlement(this.getIndex(), v)) {
//						game.doBuildSettlement(true, v, this.getIndex());
//					}
//				}
//
//			}
//			if (x < 0) lowerLimit--;
//			if (x >= 0) upperLimit--;
//		}
//
//		this.play(game);
//	}
//	
//	public void AISetupRoad(Model game) throws ViolatedPreconditionException {
//		for (Settlement s: game.getPlayerFromIndex(this.getIndex()).getSettlements()) {
//			if (s.isPlaced()) {
//				for (Edge e: s.getVertex().getAllEdges())
//				{
//					if (game.canSetupRoad(this.getIndex(), e.getEdgeLocation()))
//						game.doBuildRoad(true, e.getEdgeLocation(), this.getIndex());
//				}
//			}
//		}
//		this.play(game);
//	}
//	
//	public void AIRob(Model game) throws ViolatedPreconditionException {
//		Integer upperLimit = new Integer(2);
//		Integer lowerLimit = new Integer(0);
//		for (Integer x = new Integer(-2); x <=2; x++)
//		{
//			for (Integer y = lowerLimit; y <= upperLimit; y++)
//			{
//				if (game.canPlaceRobber(this.getIndex(), new HexLocation(x, y)))
//					game.doRobPlayer(new HexLocation(x, y), -1, this.getIndex());
//			}
//			if (x < 0) lowerLimit--;
//			if (x >= 0) upperLimit--;
//		}
//
//		this.play(game);	}

}
