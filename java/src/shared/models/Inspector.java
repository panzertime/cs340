package shared.models;

/** This class is owned by the ModelFacade. Its purpose is to lighten the load
 * of the Facade by taking care of the can functions.
 */
public class Inspector {

	public boolean canSendChat() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canRollNumber() {
		// TODO Auto-generated method stub
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
	public boolean canPlaceRobber() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canFinishTurn() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canBuyDevCard() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canUseYearOfPlenty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canUseRoadBuilder() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canUseSoldier() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canUseMonopoly() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canUseMonument() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Checks to see if a user may place a road at a given edge
	 * @pre edgeLoc is valid, game is in state for user to place road
	 * @post Map reflects valid result
	 * @return Whether or not a user may place a road
	 */
	public boolean canBuildRoad() {
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
	public boolean canBuildSettlement() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Checks the model to see if a city may be placed in the given vertex
	 * @pre vertLoc is valid, and game is in stater where user can build a city
	 * @post Map shows if a city may be placed there
	 * @param vertLoc
	 * @return True if user may place city there, false otherwise
	 */
	public boolean canBuildCity() {
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
	public boolean canOfferTrade() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canAcceptTrade() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canMaritimeTrade() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canDiscardCards() {
		// TODO Auto-generated method stub
		return false;
	}
	
}