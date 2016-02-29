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

	private boolean initialized;
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
		ArrayList<Integer> playerIndices = (ArrayList<Integer>) GetModelFacade.sole().getPlayerIndices();
		this.getView().setLocalPlayerColor(ClientPlayer.sole().getUserColor());
		
		for (Integer playerIndex: playerIndices)
		{
			
			int points = GetModelFacade.sole().getPoints(playerIndex);
			boolean highlight = GetModelFacade.sole().isTurn(playerIndex);
			boolean largestArmy = GetModelFacade.sole().isLargestArmy(playerIndex);
			boolean longestRoad = GetModelFacade.sole().isLongestRoad(playerIndex);
			
			this.getView().updatePlayer(playerIndex, points, highlight, largestArmy, longestRoad);
			
		}
	}


}

