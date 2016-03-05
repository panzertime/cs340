package client.turntracker;

import client.base.*;
import shared.model.board.piece.PieceType;
import shared.model.definitions.CatanColor;

/**
 * Interface for the turn tracker view, which displays whose turn it is, and
 * what state the game is in
 */
public interface ITurnTrackerView extends IView
{
	
	/**
	 * Sets the color to display for the local player
	 * 
	 * @param value
	 *            The local player's color
	 */
	void setLocalPlayerColor(CatanColor value);
	
	/**
	 * Initializes the properties for a player in the turn tracker display
	 * 
	 * @param playerIndex
	 *            The player's index (0 - 3)
	 * @param playerName
	 *            The player's name
	 * @param playerColor
	 *            The player's color
	 */
	void initializePlayer(int playerIndex, String playerName,
						  CatanColor playerColor);
	
	/**
	 * Updates the properties for a player in the turn tracker display
	 * 
	 * @param playerIndex
	 *            The player's index (0-3)
	 * @param points
	 *            The number of victory points the player has
	 * @param highlight
	 *            Whether or not the player's display should be highlighted
	 * @param largestArmy
	 *            Whether or not the player has the largest army
	 * @param longestRoad
	 *            Whether or not the player has the longest road
	 */
	void updatePlayer(int playerIndex, int points, boolean highlight,
					  boolean largestArmy, boolean longestRoad);
	
	/**
	 * Updates the game state button's message and enable state
	 * 
	 * @param stateMessage
	 *            The new message to be displayed in the game state button
	 * @param enable
	 *            Whether or not the game state button should be enabled
	 */
	void updateGameState(String stateMessage, boolean enable);

	/**
	 * Added by Joshua:
	 * This is to be used to update the color when a client changes colors
	 * in two different clients. Not the most necessary thing, but it will
	 * definitely improve the quality of the product.
	 * @pre The user has been initialized
	 * @post The users color will now reflect that passed in here
	 * @param playerIndex numer 1 - 4
	 * @param updatedColor Color to change for the given player
	 */
	void updateColor(int playerIndex, CatanColor updatedColor);
}

