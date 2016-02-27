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

	boolean initialized;
	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		GetModelFacade.registerListener(this);
	}
	
	@Override
	public ITurnTrackerView getView() {
		initialized = false;
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

	}
	
	private void initFromModel() {
		GetModelFacade getModelFacade = GetModelFacade.sole();
		ArrayList<Integer> playerIndices = (ArrayList<Integer>) getModelFacade.getPlayerIndices();
		
		for (Integer playerIndex: playerIndices)
		{
		
		this.getView().initializePlayer(playerIndex, getModelFacade.getPlayerName(playerIndex), getModelFacade.getPlayerColor(playerIndex));
	
		}
		initialized = true;
	}
	
	@Override
	public void update()
	{
		if (!initialized)
			initFromModel();
		GetModelFacade getModelFacade = GetModelFacade.sole();
		ArrayList<Integer> playerIndices = (ArrayList<Integer>) getModelFacade.getPlayerIndices();
		
		
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

