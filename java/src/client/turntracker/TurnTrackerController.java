package client.turntracker;

import java.util.ArrayList;

import client.base.*;
import client.main.ClientPlayer;
import client.modelfacade.get.GetModelFacade;
import shared.model.board.piece.PieceType;
import shared.model.definitions.CatanColor;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

	}
	
	private void initFromModel() {
		
		//getView().setLocalPlayerColor(ClientPlayer.sole().getUserColor());
		
		GetModelFacade getModelFacade = GetModelFacade.sole();
		ArrayList<Integer> playerIndices = getModelFacade.getPlayerIndices();
		
		for (Integer playerIndex: playerIndices)
		{
			String playerName = getModelFacade.getPlayerName(playerIndex);
			PieceType playerColor = getModelFacade.getPlayerColor(playerIndex);
			this.getView().initializePlayer(playerIndex, playerName, playerColor);
		}

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

