package client.turntracker;

import java.util.ArrayList;

import client.base.*;
import client.main.ClientPlayer;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.board.piece.PieceType;
import shared.model.definitions.CatanColor;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, GetModelFacadeListener {

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		GetModelFacade.registerListener(this);
		//initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

	}
	
	private void initFromModel() {
		
		getView().setLocalPlayerColor(ClientPlayer.sole().getUserColor());
		
		//this.getView().initializePlayer(ClientPlayer.sole().getUserIndex(), ClientPlayer.sole().getUserName(), this.getView().);
		
	}
	
	public void update()
	{
		GetModelFacade getModelFacade = GetModelFacade.sole();
		ArrayList<Integer> playerIndices = getModelFacade.getPlayerIndices();
		
		for (Integer playerIndex: playerIndices)
		{
			
			int points = getModelFacade.getPoints(playerIndex);
			boolean highlight = getModelFacade.isTurn(playerIndex);
			boolean largestArmy = getModelFacade.isLargestArmy(playerIndex);
			boolean longestRoad = getModelFacade.isLongestRoad(playerIndex);
			
			this.getView().updatePlayer(playerIndex, points, highlight, largestArmy, longestRoad);			
			
		}
	}


}

