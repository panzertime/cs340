package client.modelfacade;

import java.util.Map;

import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;
import shared.model.hand.ResourceType;

public class CanModelFacade extends ModelFacade {
	
	private CanModelFacade() {
		super();
	}
	
	private static CanModelFacade singleton;
	
	public static CanModelFacade sole() {
		if (singleton == null)
			singleton = new CanModelFacade();
		return singleton;
	}

	
	/**
	 * Checks the model to see if the current player can roll die
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canRollDice() throws NullPointerException {
		return this.gameModel.canRollNumber(playerInfo.getPlayerIndex());
	}

	/**
	 * Asks the inspector if the robber may be placed on the given hex
	 * @pre hexLoc is valid, game is in state to move robber
	 * @post Robbers ability to move on the map is shown
	 * @param hexLoc Location of hex to place robber
	 * @param playerIndex 
	 * @return Whether or not a robber can move there
	 * @throws NullPointerException Pre-condition violation
	 */
	public boolean canPlaceRobber(HexLocation hexLoc, int playerIndex) throws NullPointerException {
		return this.gameModel.canRobPlayer(playerInfo.getPlayerIndex(), hexLoc, playerIndex);
	}

	/**
	 * Checks the model to see if the current player can buy dev card
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canBuyDevCard() throws NullPointerException {
		return this.gameModel.canBuyDevCard(playerInfo.getPlayerIndex());
	}

	/**
	 * Checks the model to see if the current player can use year of plenty
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param one first resource
	 * @param two second resource
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseYearOfPlenty(ResourceType one, ResourceType two) throws NullPointerException {
		return this.gameModel.canUseYearOfPlenty(playerInfo.getPlayerIndex(), one, two);
	}

	/**
	 * Checks the model to see if the current player can DiscardCards
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param one Edge to build on
	 * @param two Edge to build on
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseRoadBuilding(EdgeLocation one, EdgeLocation two) throws NullPointerException {
		return this.gameModel.canUseRoadBuilding(playerInfo.getPlayerIndex(), one, two);
	}

	/**
	 * Checks the model to see if the current player can use soldier
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param newRobberLocation Change Robber Hex
	 * @param playerIndex Player to Rob
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseSoldier(HexLocation newRobberLocation, int victimIndex) throws NullPointerException {
		return this.gameModel.canUseSoldier(playerInfo.getPlayerIndex(), newRobberLocation, victimIndex);
	}

	/**
	 * Checks the model to see if the current player can use monopoly
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param type Resource to Monopolize
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseMonopoly(ResourceType type) throws NullPointerException {
		return this.gameModel.canUseMonopoly(playerInfo.getPlayerIndex(), type);
	}

	/**
	 * Checks the model to see if the current player can use monument
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseMonument() throws NullPointerException {
		return this.gameModel.canUseMonument(playerInfo.getPlayerIndex());
	}

	/**
	 * Checks to see if a user may place a road at a given edge
	 * @pre edgeLoc is valid, game is in state for user to place road
	 * @post Map reflects valid result
	 * @param edgeLoc location of edge to place road
	 * @return Whether or not a user may place a road
	 * @throws NullPointerException Pre condition violated
	 */
	public boolean canBuildRoad(EdgeLocation edgeLoc) throws NullPointerException {
		return this.gameModel.canBuildRoad(playerInfo.getPlayerIndex(), edgeLoc);
	}

	/**
	 * Asks the inspector if a user may place a settlement there.
	 * @pre vertLoc is valid, game is in state where user can place settlement
	 * @post Screen will reflect result
	 * @param vertLoc Location to put the user
	 * @return Whether or not a user may build a settlement there
	 * @throws NullPointerException Pre-Conditions violated
	 */
	public boolean canBuildSettlement(VertexLocation vertLoc) throws NullPointerException {
		return this.gameModel.canBuildSettlement(playerInfo.getPlayerIndex(), vertLoc);
	}

	/**
	 * Asks the inspector if a city may be placed in the given vertex
	 * @pre vertLoc is valid, and game is in stater where user can build a 
	 * city
	 * @post Map shows if a city may be placed there
	 * @param vertLoc City Location
	 * @return True if user may place city there, false otherwise
	 * @throws NullPointerException Pre conditions violated
	 */
	public boolean canBuildCity(VertexLocation vertLoc) throws NullPointerException {
		return this.gameModel.canBuildCity(playerInfo.getPlayerIndex(), vertLoc);
	}
	
	/**
	 * Checks the model through the inspector to see if a user can trade
	 * @pre User is logged in, in a game, in his turn
	 * @post nothing
	 * @param resource Resource to trade
	 * @param amount Amount of resource
	 * @return Whether or not the user can trade
	 * @throws NullPointerException Pre condtion violation
	 */
	public boolean canOfferTrade(Map<ResourceType, Integer> resources, Integer receiverIndex) throws NullPointerException {
		return gameModel.canOfferTrade(playerInfo.getPlayerIndex(), resources, receiverIndex);
	}
	
	/**
	 * Checks the model to see if the current player can accept trade
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canAcceptTrade() throws NullPointerException {
		return gameModel.canAcceptTrade(playerInfo.getPlayerIndex());
	}

	/**
	 * Checks the model to see if the current player can maritime trade
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param ratio Port
	 * @param input Resource
	 * @param output Resource
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canMaritimeTrade(int ratio, ResourceType inputType, ResourceType outputType)
			throws NullPointerException {
		return this.gameModel.canMaritimeTrade(playerInfo.getPlayerIndex(), ratio, inputType, outputType);
	}

	/**
	 * Checks the model to see if the current player can DiscardCards
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param resourceList resources they have
	 * @return whether or not this operation is valid
	 * @throws NullPointerException
	 * @throws BadResourceTypeException
	 */
	public boolean canDiscardCards(Map<ResourceType, Integer> resources) throws NullPointerException {
		return this.gameModel.canDiscardCard(playerInfo.getPlayerIndex(), resources);
	}

	/**
	 * Checks the model to see if the current player can finish his turn
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canEndTurn() throws NullPointerException {
		return this.gameModel.canFinishTurn(playerInfo.getPlayerIndex());
	}

	public boolean canSendChat() throws NullPointerException {
		return this.gameModel.canSendChat(playerInfo.getPlayerIndex());
	}
}