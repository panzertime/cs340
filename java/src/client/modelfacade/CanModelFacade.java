package client.modelfacade;

import java.util.Map;

import client.main.ClientPlayer;
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
	 */
	public boolean canRollDice() {
		return this.gameModel.canRollNumber(ClientPlayer.sole().getUserIndex());
	}

	/**
	 * Asks the inspector if the robber may be placed on the given hex
	 * @pre hexLoc is valid, game is in state to move robber
	 * @post Robbers ability to move on the map is shown
	 * @param hexLoc Location of hex to place robber
	 * @return Whether or not a robber can move there
	 * @pre-condition violation
	 */
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return this.gameModel.canPlaceRobber(ClientPlayer.sole().getUserIndex(), hexLoc);
	}
	
	
	public boolean canRobPlayerFrom(HexLocation robberLoc, Integer playerIndex) {
		return this.gameModel.canRobPlayerFrom(robberLoc, ClientPlayer.sole().getUserIndex(), playerIndex);
	}

	/**
	 * Checks the model to see if the current player can buy dev card
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 */
	public boolean canBuyDevCard() {
		return this.gameModel.canBuyDevCard(ClientPlayer.sole().getUserIndex());
	}
	public boolean canSeePlayDevCard() {
		return this.gameModel.isTurn(ClientPlayer.sole().getUserIndex());
	}
	
	public boolean canBuyRoad() {
		return this.gameModel.canBuyRoad(ClientPlayer.sole().getUserIndex());
	}
	public boolean canBuySettlement() {
		return this.gameModel.canBuySettlement(ClientPlayer.sole().getUserIndex());
	}
	public boolean canBuyCity() {
		return this.gameModel.canBuyCity(ClientPlayer.sole().getUserIndex());
	}

	/**
	 * Checks the model to see if the current player can use year of plenty
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param one first resource
	 * @param two second resource
	 * @return post
	 */
	public boolean canUseYearOfPlenty(ResourceType one, ResourceType two) {
		return this.gameModel.canUseYearOfPlenty(ClientPlayer.sole().getUserIndex(), one, two);
	}

	/**
	 * Checks the model to see if the current player can DiscardCards
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param one Edge to build on
	 * @param two Edge to build on
	 * @return post
	 */
	public boolean canUseRoadBuilding(EdgeLocation one, EdgeLocation two) {
		return this.gameModel.canUseRoadBuilding(ClientPlayer.sole().getUserIndex(), one, two);
	}
	
	public Boolean canUseRoadBuildingSingle(EdgeLocation one) {
		return this.gameModel.canUseRoadBuildingSingle(ClientPlayer.sole().getUserIndex(), one);
	}

	/**
	 * Checks the model to see if the current player can use soldier
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param newRobberLocation Change Robber Hex
	 * @param victimIndex Player to Rob
	 * @return post
	 */
	public boolean canUseSoldier(HexLocation newRobberLocation, int victimIndex) {
		return this.gameModel.canUseSoldier(ClientPlayer.sole().getUserIndex(), newRobberLocation, victimIndex);
	}

	/**
	 * Checks the model to see if the current player can use monopoly
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param type Resource to Monopolize
	 * @return post
	 */
	public boolean canUseMonopoly(ResourceType type) {
		return this.gameModel.canUseMonopoly(ClientPlayer.sole().getUserIndex(), type);
	}

	/**
	 * Checks the model to see if the current player can use monument
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 */
	public boolean canUseMonument() {
		return this.gameModel.canUseMonument(ClientPlayer.sole().getUserIndex());
	}

	/**
	 * Checks to see if a user may place a road at a given edge
	 * @pre edgeLoc is valid, game is in state for user to place road
	 * @post Map reflects valid result
	 * @param edgeLoc location of edge to place road
	 * @return Whether or not a user may place a road
	 * @pre condition violated
	 */
	public boolean canBuildRoad(EdgeLocation edgeLoc) {
		return this.gameModel.canBuildRoad(ClientPlayer.sole().getUserIndex(), edgeLoc);
	}

	public boolean canSetupRoad(EdgeLocation edgeLoc) {
		return this.gameModel.canSetupRoad(ClientPlayer.sole().getUserIndex(), edgeLoc);
	}


	/**
	 * Asks the inspector if a user may place a settlement there.
	 * @pre vertLoc is valid, game is in state where user can place settlement
	 * @post Screen will reflect result
	 * @param vertLoc Location to put the user
	 * @return Whether or not a user may build a settlement there
	 * @pre-Conditions violated
	 */
	public boolean canBuildSettlement(VertexLocation vertLoc) {
		return this.gameModel.canBuildSettlement(ClientPlayer.sole().getUserIndex(), vertLoc);
	}
	
	public boolean canSetupSettlement(VertexLocation vertLoc) {
		return this.gameModel.canSetupSettlement(ClientPlayer.sole().getUserIndex(), vertLoc);
	}

	/**
	 * Asks the inspector if a city may be placed in the given vertex
	 * @pre vertLoc is valid, and game is in stater where user can build a 
	 * city
	 * @post Map shows if a city may be placed there
	 * @param vertLoc City Location
	 * @return True if user may place city there, false otherwise
	 * @pre conditions violated
	 */
	public boolean canBuildCity(VertexLocation vertLoc) {
		return this.gameModel.canBuildCity(ClientPlayer.sole().getUserIndex(), vertLoc);
	}
	

	public boolean canDomesticTrade() {
		return gameModel.canDomesticTrade(ClientPlayer.sole().getUserID());
	}
	
	/**
	 * Checks the model through the inspector to see if a user can trade
	 * @pre User is logged in, in a game, in his turn
	 * @post nothing
	 * @param resources Resource to trade
	 * @param receiverIndex the destination player
	 * @return Whether or not the user can trade
	 * @pre condtion violation
	 */
	public boolean canOfferTrade(Map<ResourceType, Integer> resources, Integer receiverIndex) {
		return gameModel.canOfferTrade(ClientPlayer.sole().getUserIndex(), resources, receiverIndex);
	}
	
	/**
	 * Checks the model to see if the current player can accept trade
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 */
	public boolean canAcceptTrade() {
		return gameModel.canAcceptTrade(ClientPlayer.sole().getUserID());
	}
	
	public boolean canViewTrade() {
		return gameModel.canViewTrade(ClientPlayer.sole().getUserIndex());
	}

	
	/**
	 * Checks the model to see if the current player can maritime trade
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param ratio Port
	 * @param inputType Resource
	 * @param outputType Resource
	 * @return post
	 */
	public boolean canMaritimeTrade(int ratio, ResourceType inputType, ResourceType outputType)
			{
		return this.gameModel.canMaritimeTrade(ClientPlayer.sole().getUserIndex(), ratio, inputType, outputType);
	}
	
	/**
	 * Checks if a trade offer is possible
	 * @pre none
	 * @post the highest valid ratio
	 * @param inputType the type we are considering offering for trade
	 * @return the highest valid trading ratio for that resource type
	 */
	public int canOfferMaritime (ResourceType inputType)
			{
		return this.gameModel.canOfferMaritime(ClientPlayer.sole().getUserIndex(), inputType);
	}

	/**
	 * Checks if the bank has enough cards to respond to a maritime trade offer
	 * @pre none, but you should call canOfferMaritime first
	 * @post the bank tells you whether or not it's ready to trade a given resource
	 * @param outputType the type we want to get back from trade
	 * @return TRUE if this trade is available, FALSE if not
	 */
	public boolean canReceiveMaritime (ResourceType outputType)
			{
		return this.gameModel.canReceiveMaritime(ClientPlayer.sole().getUserIndex(), outputType);
	}	


	/**
	 * Checks the model to see if the current player can DiscardCards
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param resources resources they have
	 * @return whether or not this operation is valid
	 */
	public boolean canDiscardCards(Map<ResourceType, Integer> resources) {
		return this.gameModel.canDiscardCard(ClientPlayer.sole().getUserIndex(), resources);
	}
	
	public boolean shouldDiscard() {
		return this.gameModel.shouldDiscard(ClientPlayer.sole().getUserIndex());
	}

	/**
	 * Checks the model to see if the current player can finish his turn
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 */
	public boolean canEndTurn() {
		return this.gameModel.canFinishTurn(ClientPlayer.sole().getUserIndex());
	}

	public boolean canSendChat() {
		return this.gameModel.canSendChat(ClientPlayer.sole().getUserIndex());
	}

}
