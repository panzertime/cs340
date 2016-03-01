package client.turntracker;

import java.util.ArrayList;

import client.base.Controller;
import client.main.ClientPlayer;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.definitions.CatanColor;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements
		ITurnTrackerController, GetModelFacadeListener {
	
	private int[] playerPoints;
	private boolean[] playerRoad;
	private boolean[] playerArmy;
	private CatanColor[] playerColor;
	private boolean[] playerTurn;
	
	private final String WAITING = "Waiting for Other Players";
	private final String FINISH = "Finish Turn";
	private final String ROLL = "Roll the Dice";
	private final String DISCARD = "Discard Cards";
	private final String ROBBER = "Place the Robber";
	private final String TRADE = "Accept or Reject Trade";

	public TurnTrackerController(ITurnTrackerView view) {

		super(view);

		GetModelFacade.registerListener(this);
		playerPoints = new int[4];
		playerRoad = new boolean[4];
		playerArmy = new boolean[4];
		playerColor = new CatanColor[4];
		playerTurn = new boolean[4];
	}

	@Override
	public ITurnTrackerView getView() {
		return (ITurnTrackerView) super.getView();
	}

	@Override
	public void endTurn() {
		DoModelFacade.sole().doEndTurn();
	}

	@Override
	public void update() {
		ArrayList<Integer> playerIndices = (ArrayList<Integer>) GetModelFacade
				.sole().getPlayerIndices();
		
		//Update Turn stuff
		int clientIndex = ClientPlayer.sole().getUserIndex();
		
		//update Game state
		if(GetModelFacade.sole().isStateDiscarding()) {
			this.getView().updateGameState(DISCARD, false);
		} else if(GetModelFacade.sole().isTurn(clientIndex)) {
			if(GetModelFacade.sole().isStateRolling()) {
				this.getView().updateGameState(ROLL, false);
			} else if(GetModelFacade.sole().isStateRobbing()) {
				this.getView().updateGameState(ROBBER, false);
			} else {
				this.getView().updateGameState(FINISH, true);
			}
		} else {
			if(GetModelFacade.sole().hasTradeOffer()) {
				this.getView().updateGameState(TRADE, false);
			} else {
				this.getView().updateGameState(WAITING, false);
			}
		}

		for (Integer playerIndex : playerIndices) {
			
			//check to see if we need to update our colors
			CatanColor updatedColors = GetModelFacade.sole().getPlayerColor(playerIndex);
			if(!playerColor.equals(updatedColors)) {
				String playerName = GetModelFacade.sole().getPlayerName(playerIndex);
				this.getView().initializePlayer(playerIndex,
						playerName,
						updatedColors);
				this.playerColor[playerIndex] = updatedColors;
				if(clientIndex == playerIndex) {
					ClientPlayer.sole().setUserColor(updatedColors);
					this.getView().setLocalPlayerColor(updatedColors);
				}
			}	

			//check update in players:
			// 1 - points
			// 2 - turn
			// 3 - largest army status
			// 4 - lognest road status
			int updatedPoints = GetModelFacade.sole().getPoints(playerIndex);
			boolean updatedHighlight = GetModelFacade.sole().isTurn(playerIndex);
			boolean updatedLargestArmy = GetModelFacade.sole().isLargestArmy(
					playerIndex);
			boolean updatedLongestRoad = GetModelFacade.sole().isLongestRoad(
					playerIndex);
			
			if(updatedPoints != this.playerPoints[playerIndex] || 
					updatedHighlight != this.playerTurn[playerIndex] || 
					updatedLargestArmy != this.playerArmy[playerIndex] ||
					updatedLongestRoad != this.playerRoad[playerIndex])
			{
				//if there is an inconsistency, update it in the view
				//and save the new values
				this.playerPoints[playerIndex] = updatedPoints;
				this.playerTurn[playerIndex] = updatedHighlight;
				this.playerArmy[playerIndex] = updatedLargestArmy;
				this.playerRoad[playerIndex] = updatedLongestRoad;
				this.getView().updatePlayer(playerIndex, updatedPoints, updatedHighlight,
						updatedLargestArmy, updatedLongestRoad);
			}	
		}
	}

}
