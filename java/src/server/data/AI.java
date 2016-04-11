package server.data;

import java.util.HashMap;
import java.util.Random;

import client.modelfacade.DoModelFacade;
import shared.model.Model;
import shared.model.board.edge.Edge;
import shared.model.board.hex.HexLocation;
import shared.model.board.piece.Settlement;
import shared.model.board.vertex.Vertex;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class AI extends User {

	int index;
	Model game;
	
	public AI() {
		
	}
	public AI(Model game) {
		this.game = game;
	}
	
	public void setIndex() {}
	
	public int getIndex() { return index; }
	
	//added as Model listener
	//notified when Model initiated or version updated
	public void play() throws ViolatedPreconditionException {
		if (game.isStateDiscarding()) {
			
			this.AIDiscard();
		}
		else if (game.isTurn(index)) {
			if (game.isStateRolling()){
				Random rand = new Random();
				int n = 0;
				n = rand.nextInt(6) + 1;
				n += rand.nextInt(6) + 1;
				game.doRollNumber(n, this.getIndex());
				this.play();
			}
			else if (game.isStateRobbing()) {
				this.AIRob();
			}
			else if (game.isStatePlaying()) {
				this.AIPlay();
			}
			else if (game.isStateSetupSettlement())
			{
				this.AISetupSettlement();
			}
			else if (game.isStateSetupRoad()){ 
				this.AISetupRoad();
			}
		}
		
		
	}
	
	public void AIDiscard() throws ViolatedPreconditionException {
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
		this.play();
	}
	
	public void AIPlay() throws ViolatedPreconditionException {
		game.doFinishTurn(this.getIndex());
		//this.play();
	}
	
	public void AISetupSettlement() throws ViolatedPreconditionException {
		Integer upperLimit = new Integer(2);
		Integer lowerLimit = new Integer(0);
		for (Integer x = new Integer(-2); x <=2; x++)
		{
			for (Integer y = lowerLimit; y <= upperLimit; y++)
			{
				for (Vertex v: game.getBoard().getHexAt(new HexLocation(x, y)).getVerts())
				{
					if (game.canBuildSettlement(this.getIndex(), v.getVertexLocation())) {
						game.doBuildSettlement(true, v.getVertexLocation(), this.getIndex());
					}
				}

			}
			if (x < 0) lowerLimit--;
			if (x >= 0) upperLimit--;
		}

		this.play();
	}
	
	public void AISetupRoad() throws ViolatedPreconditionException {
		for (Settlement s: game.getPlayerFromIndex(this.getIndex()).getSettlements()) {
			if (s.isPlaced()) {
				for (Edge e: s.getVertex().getAllEdges())
				{
					if (game.canBuildRoad(this.getIndex(), e.getEdgeLocation()))
						game.doBuildRoad(true, e.getEdgeLocation(), this.getIndex());
				}
			}
		}
		this.play();
	}
	
	public void AIRob() throws ViolatedPreconditionException {
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

		this.play();	}

}
