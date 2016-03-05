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
	private boolean initialized;

	public TurnTrackerController(ITurnTrackerView view) {

		super(view);

		GetModelFacade.registerListener(this);
		playerPoints = new int[4];
		playerRoad = new boolean[4];
		playerArmy = new boolean[4];
		playerColor = new CatanColor[4];
		playerTurn = new boolean[4];
		initialized = false;
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
		int clientIndex = ClientPlayer.sole().getUserIndex();
		if(!initialized) {
			initialize(clientIndex);
			initialized = true;
		}		
		
		for(int playerIndex = 0; playerIndex < 4; playerIndex++) {
			CatanColor updatedColor = 
					GetModelFacade.sole().getPlayerColor(playerIndex);
			if(!this.playerColor[playerIndex].equals(updatedColor)) {
				this.getView().updateColor(playerIndex, updatedColor);
				if(playerIndex == clientIndex) {
					getView().setLocalPlayerColor(updatedColor);
					ClientPlayer.sole().setUserColor(updatedColor);
				}
			}
			
			int updatedPoints = GetModelFacade.sole().getPoints(playerIndex);
			boolean updatedHighlight = GetModelFacade.sole().isTurn(playerIndex);
			boolean updatedLargestArmy = GetModelFacade.sole().isLargestArmy(
					playerIndex);
			boolean updatedLongestRoad = GetModelFacade.sole().isLongestRoad(
					playerIndex);
			
			
			if(isUpdatedInfo(updatedPoints, updatedHighlight, 
					updatedLargestArmy, updatedLongestRoad, playerIndex)) {
				
				this.getView().updatePlayer(playerIndex, updatedPoints, 
						updatedHighlight, updatedLargestArmy, 
						updatedLongestRoad);
				
				updateInfo(updatedPoints, updatedHighlight, updatedLargestArmy,
						updatedLongestRoad, playerIndex);
			}
			
			updateGameState(clientIndex);
		}
	}

	private boolean isUpdatedInfo(int updatedPoints, boolean updatedHighlight,
			boolean updatedLargestArmy,	boolean updatedLongestRoad, 
			int playerIndex) {
		boolean result = false;
		if(updatedPoints != this.playerPoints[playerIndex] || 
				updatedHighlight != this.playerTurn[playerIndex] || 
				updatedLargestArmy != this.playerArmy[playerIndex] ||
				updatedLongestRoad != this.playerRoad[playerIndex]) {
			result = true;
		}
		return result;
	}

	private void updateInfo(int updatedPoints, boolean updatedHighlight,
			boolean updatedLargestArmy,	boolean updatedLongestRoad, 
			int playerIndex) {
		this.playerPoints[playerIndex] = updatedPoints;
		this.playerTurn[playerIndex] = updatedHighlight;
		this.playerArmy[playerIndex] = updatedLargestArmy;
		this.playerRoad[playerIndex] = updatedLongestRoad;		
	}

	private void initialize(int clientIndex) {
		for(int playerIndex = 0; playerIndex < 4; playerIndex++) {
			String playerName = 
					GetModelFacade.sole().getPlayerName(playerIndex);
			CatanColor playerColor = 
					GetModelFacade.sole().getPlayerColor(playerIndex);
			getView().initializePlayer(playerIndex, playerName, playerColor);
			this.playerColor[playerIndex] = playerColor;
			if(playerIndex == clientIndex) {
				getView().setLocalPlayerColor(playerColor);
				ClientPlayer.sole().setUserColor(playerColor);
			}
		}
		updateGameState(clientIndex);
	}

	private void updateGameState(int clientIndex) {
		if(GetModelFacade.sole().mustDiscard()) {
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
	}

}