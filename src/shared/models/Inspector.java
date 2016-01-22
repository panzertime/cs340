package shared.models;

import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.VertexLocation;
import shared.models.exceptions.NonMoveException;
import shared.models.exceptions.PlayingException;
import shared.models.exceptions.TradeException;
import shared.models.hand.ResourceType;

/** This class is owned by the ModelFacade. Its purpose is to lighten the load
 * of the Facade by taking care of the can functions.
 */


public class Inspector {

	/**
	 * Checks the model to see if a city may be placed in the given vertex
	 * @pre vertLoc is valid, and game is in stater where user can build a city
	 * @post Map shows if a city may be placed there
	 * @param vertLoc
	 * @return True if user may place city there, false otherwise
	 * @throws NonMoveException Pre conditions violated
	 */
	public boolean canPlaceCity(VertexLocation vertLoc) 
			throws NonMoveException {
				return false;
		
	}
	
	/**
	 * Checks to see if a user may place a road at a given edge
	 * @pre edgeLoc is valid, game is in state for user to place road
	 * @post Map reflects valid result
	 * @param edgeLoc location of edge to place road
	 * @return Whether or not a user may place a road
	 * @throws NonMoveException Pre condition violated
	 */
	public boolean canPlaceRoad(EdgeLocation edgeLoc) throws NonMoveException {
		return false;
		
	}
	
	/**
	 * Checks the model to see if the robber may be placed on the given hex
	 * @pre hexLoc is valid, game is in state to move robber
	 * @post Robbers ability to move on the map is shown
	 * @param hexLoc Location of hex to place robber
	 * @return Whether or not a robber can move there
	 * @throws NonMoveException Pre-condition violation
	 */
	public boolean canPlaceRobber(HexLocation hexLoc) throws NonMoveException {
		return false;
		
	}
	
	/**
	 * Checks the model to see if a user may place a settlement there.
	 * @pre vertLoc is valid, game is in state where user can place settlement.
	 * @post Screen will reflect result
	 * @param vertLoc Location to put the user
	 * @return Whether or not a user may build a settlement there
	 * @throws NonMoveException Pre-Conditions violated
	 */
	public boolean canPlaceSettlement(VertexLocation vertLoc) 
			throws NonMoveException {
		return false;
		
	}
	
	
	
	/**
	 * Checks the model to see if a user can trade that much of one resource
	 * @pre User is logged in, in a game, in his turn
	 * @post nothing
	 * @param resource Resource to trade
	 * @param amount Amount of resource
	 * @return Whether or not the user can trade
	 * @throws TradeException Pre condtion violation
	 */
	public boolean canMakeTrade(ResourceType resource, int amount)
		throws PlayingException {
			return false;
		
	}
	
}