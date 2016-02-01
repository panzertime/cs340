
package shared.models;

import java.util.Map;

import client.servercommunicator.ServerFacade;
import shared.definitions.CatanColor;
import shared.models.board.locations.EdgeLocation;
import shared.models.board.locations.HexLocation;
import shared.models.board.locations.VertexLocation;
import shared.models.exceptions.DevCardException;
import shared.models.exceptions.DiscardException;
import shared.models.exceptions.MessageException;
import shared.models.exceptions.NonMoveException;
import shared.models.exceptions.PlayingException;
import shared.models.exceptions.SignInException;
import shared.models.exceptions.TradeException;
import shared.models.hand.ResourceType;

/**
 * This class sits between the Game Model, the Controllers and Server Facade.
 * Its purpose is to simplify the interactions between all three.
 */
public class ModelFacade {
	
	private Inspector inspector;
	private Model gameModel;
	
	/**
	 * Instrcutor with no params
	 */
	public ModelFacade() {

	}

	//TODO: Second constructor
	
	/**
	 * @return the inspector
	 */
	public Inspector getInspector() {
		return inspector;
	}

	/**
	 * @param inspector the inspector to set
	 */
	public void setInspector(Inspector inspector) {
		this.inspector = inspector;
	}

	//TODO: getters and setters

	/**
	 * TODO
	 */
	private void importGameModel(Model modelFromServer) {
		// TODO Auto-generated method stub
		
	}
	
	//Controller to Server Interactions
	
	//user
	/**
	 * Uses the given username and password to try and enter the server.
	 * @pre Parameter are not null
	 * @post User is logged in if credentials are valid. If not, rejected.
	 * @param username Username
	 * @param password Password
	 * @throws NonMoveException  Pre condition violated
	 */
	public void signIn(String username, String password) 
			throws SignInException {
		ServerFacade.get_Instance().login(username, password);
	}
	
	/**
	 * Creates a new user name on the server, if the usernamee is not already
	 * taken. Logs them in simultaneously.
	 * @pre Params are not null. Username is not already taken.
	 * @post In the case of success, a new user is created on the server and
	 * logged in. In case of failure, the user is notified. 
	 * @param username Username
	 * @param password Password
	 * @throws NonMoveException Precondition violation
	 */
	public void register(String username, String password) 
			throws SignInException {
		ServerFacade.get_Instance().register(username, password);
	}
	
	//games (pre-joining)
	/**
	 * Gets a list from the server of all the games available to join
	 * @pre User is logged in
	 * @post displays a list of all the games
	 * @throws NonMoveException User not logged in
	 */
	public void setupJoinGame() throws NonMoveException {
		ServerFacade.get_Instance().getGames();
	}
	
	
	/**
	 * Sends a user's input to the server to create a new game
	 * @pre name != null, and the boolean values are set
	 * @post A new game is created on the server
	 * @param name Name of the game
	 * @param randomTiles Set the tiles randomly?
	 * @param randomNumbers Set the numbers randomly?
	 * @param randomPorts Set the ports Randomly?
	 * @throws NonMoveException Precondition violated
	 */
	public void createNewGame(String name, boolean randomTiles, 
			boolean randomNumbers, boolean randomPorts) 
					throws NonMoveException {
		NewGame newGame = ServerFacade.get_Instance().createNewGame(randomTiles, randomNumbers,
				randomPorts, name);
	}
	
	/**
	 * Adds a player to the given game on the server. Receives a gameModel in 
	 * return
	 * @pre ID is valid, color is unique
	 * @post Player is added to game
	 * @param gameID Game to be added to
	 * @param color Color to represent player
	 * @throws NonMoveException Pre condition violation
	 */
	public void joinGame(int gameID, CatanColor color) 
			throws NonMoveException {
		ServerFacade.get_Instance().joinGame(gameID, color);
	}
	
	//TODO : Game save?
	//TODO : Game load?
	
	//games (game you're in)
	/**
	 * adds an AI to the given game on the server
	 * @pre user is logged in and has joined a game. There is space for another
	 * player, and the ai is LARGEST_ARMY
	 * @param aiType LARGEST_ARMY
	 * @throws NonMoveException precondtion violation
	 */
	public void addAI(String aiType) throws NonMoveException {
		ServerFacade.get_Instance().addAI(aiType);
	}
	
	public void getAI() throws NonMoveException {
		ServerFacade.get_Instance().getAI();
	}
	
	//TODO: reset, commandsIn commandsOut,

	//moves
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in and in a game
	 * @post The chat now contains the given message. If there was an error
	 * it tries again
	 * @param message The message to be sent
	 * @throws MessageException User is not a member of a game
	 */
	public void sendMessage(String message) 
			throws MessageException {
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().sendMessage(
				playerIndex, message);
		importGameModel(modelFromServer);
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, in a game, its his turn, the client is 
	 * 'playing', all params are valid, the robber is not kept in the same
	 * location
	 * @post robber is placed in the new location, player being robbed gives
	 * away one of his resource cards randomly.
	 * @param location New location of robber
	 * @param victimIndex PlayerID of robbed person
	 * @throws PlayingException Pre-conditions violated
	 */
	public void placeRobber(HexLocation location, int victimIndex) 
			throws PlayingException{
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().robPlayer(
				playerIndex, victimIndex, location);
		importGameModel(modelFromServer);
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, in a game, it his turn, and the client is
	 * 'playing'
	 * @post cards in new dev hand are transfered to old dev card hand and the
	 * next player has his turn.
	 * @throws PlayingException Pre-condition violation
	 */
	public void endTurn()  throws PlayingException {
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().endTurn(
				playerIndex);
		importGameModel(modelFromServer);
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server. 
	 * @pre User is logged in and in a game, it's his turn, the client model's
	 * state is 'playing', he has the resources and there are devcards left
	 * @post If the user is eligible to buy a dev card, he will receive one at
	 * random
	 * @return Whether or not the user received a card
	 * @throws DevCardException
	 */
	public void buyDevCard() throws DevCardException {
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().buyDevCard(
				playerIndex);
		importGameModel(modelFromServer);
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, in a game, on his turn, client model is
	 * 'playing', he has not yet used a non-monument dev card, the two
	 * specified dev cards are in the bank.
	 * @post User gains two specified resources
	 * @param resource1 first desired resource
	 * @param resource2 second desired resource
	 * @throws DevCardException Violation of preconditions
	 */
	public void playYearOfPlentyCard(ResourceType resource1, 
			ResourceType resource2)	throws DevCardException {
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().yearOfPlentyCard(
				playerIndex, resource1,	resource2);
		importGameModel(modelFromServer);
	}
	/**
	 * TODOL
	 */
	public void roadBuilding(EdgeLocation spot1, EdgeLocation spot2) 
			throws DevCardException {
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().playRoadBuildCard(
				playerIndex, spot1, spot2);
		importGameModel(modelFromServer);
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, in a game, it is her turn, client model status
	 * is 'playing', she has not yet played non-monument dev card. Robber is
	 * not being kept in the same location, player being robbed (if exists)
	 * has resource cards.
	 * @post robber has new location, robbed player loses cards, current player
	 * gains them, largest army is awarded - if applicable, you are no longer
	 * able to play more development cards.
	 * @param location new location of the robber
	 * @param victimIndex player you robbing (-1 for no one)
	 * @throws DevCardException pre condition violation
	 */
	public void playSoldierCard(HexLocation location, int victimIndex)
			throws DevCardException {
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().playSoldierCard(
				playerIndex, victimIndex,location);
		importGameModel(modelFromServer);
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre You are logged in and currently have a game. It is your turn, the
	 * client model status is 'playing', you have a monopoly card, you
	 * have not yet played a 
	 * @post You receive every other players resource card of the same type 
	 * @throws DevCardException
	 */
	public void playMonopolyCard(ResourceType resource) 
			throws DevCardException {
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().playMonopolyCard(
				resource, playerIndex);
		importGameModel(modelFromServer);
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre You are logged in, have joined a game, it is your turn, the client
	 * model status is 'playing', you have a monument card
	 * @post you gain a victory point
	 * @throws DevCardException
	 */
	public void playMonumentCard() throws DevCardException {
		int playerIndex = this.gameModel.getPlayerIndex();
		Model modelFromServer = ServerFacade.get_Instance().playMonumentCard(
				playerIndex);
		importGameModel(modelFromServer);
	}
	
	/**
	 * TODO
	 */
	public void buildRoad(EdgeLocation spot1, EdgeLocation spot2) 
			throws DevCardException {
		//TODO
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, in a game, its his turn, the client is 
	 * 'playing', params are valid, settlement location is open, not on water,
	 * not adjacent to another settlement, connected to one of the users roads
	 * during setup, user has required resources.
	 * @post User loses resource to build, settlement is place on map 
	 * @param setupMode Is this setup?
	 * @param vertLoc Location of settlement
	 * @throws PlayingException Pre condition violation
	 */
	public void placeSettlement(boolean setupMode, VertexLocation vertLoc)
			throws PlayingException {
		
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, in a game, in their turn, the client model is
	 * 'playing', the city is a valid location and the user has the necessary
	 * resources.
	 * @post User loses the necessary resources, city is placed on the map
	 * and the user is returned a settlement.
	 * @param vertLoc Location to build city
	 * @throws PlayingException invalid pre condition
	 */
	public void buildCity(VertexLocation vertLoc) throws PlayingException {
		
	}
	
	/**
	 * Sends a trade offer to the server to be processed
	 * @pre User is logged in, in a game, in their turn, the client is
	 * 'playing', user has the offered resources
	 * @post trade gets offered to the other player(on the server model)
	 * @param offer Resources and their amounts to be given (-numbers mean
	 * the user gets those cards)
	 * @param receiver ID of the recipient of the cards
	 * @throws TradeException Pre Condition failure
	 */
	public void sendTradeOffer(Map<ResourceType, Integer> offer, int receiver)
		throws TradeException {
		
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, in a game, its his turn, the client is 
	 * 'playing', params are valid, user has resources, resources less than
	 * four are located at the correct port
	 * @post Bank and user trade resources
	 * @param ratio 2,3 or 4
	 * @param inputResource What user gives
	 * @param outputResource What user gets
	 * @throws PlayingException Pre-condition violation
	 */
	public void makeMaritimeTrade(int ratio, ResourceType inputResource,
			ResourceType outputResource) throws PlayingException {
		
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre user is logged in, in a game, been offered a domestic trade, and
	 * has the required resources
	 * @post If trade is accepted, resources are swapper, other wise there are
	 * no trades. Trade offer is removed.
	 * @param willAccept Whether or not the user accepts the trade
	 * @throws TradeException Pre condition not met
	 */
	public void acceptTrade(boolean willAccept) throws TradeException {
		
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, has a game, client model is 'discarding',
	 * user has over 7 cards, user has the cards to discard
	 * @post user loses specified cards, client model changes to 'robbing' if
	 * they are the last to discard
	 * @param discardedCards
	 * @throws DiscardException Pre-conditions not met
	 */
	public void discard(ResourceType[] discardedCards) 
			throws DiscardException {
		Model modelFromServer = ServerFacade.get_Instance().discard(discardedCards);
		importGameModel(modelFromServer);
	}
	
	
	//util
	
	//Controller to Game Model and Server Interactions
	
	/**
	 * Simulates two die, sends the result to the model and server and compares
	 * the result models.
	 * @pre User is logged in, in a game, model state is 'rolling', it's
	 * the user's turn
	 * @post client state now becomes 'discarding', 'robbing' or 'playing'
	 * @throws PlayingException pre condition violation
	 */
	public void rollDice() throws PlayingException {
		
	}
	
	//Controller to Game Model Interactions
	
	/**
	 * Checks the model to see if a user can increase a given resource.
	 * @pre user is logged in, in a game, slient model is 'discarding',
	 * user has over 7 cards
	 * @post increase button the gui will reflect limitations
	 * @param resource User's resource to increase
	 * @param amount Amount to increase it by 
	 * @return Whether or not the amount of a user's resource can decrease
	 * @throws DiscardException Pre-conditions not met
	 */
	public boolean increaseAmount(ResourceType resource, int amount) 
			throws DiscardException, TradeException {
		return false;
	}
	
	/**
	 * Asks the inspector if a city may be placed in the given vertex
	 * @pre vertLoc is valid, and game is in stater where user can build a city
	 * @post Map shows if a city may be placed there
	 * @param vertLoc
	 * @return True if user may place city there, false otherwise
	 * @throws NonMoveException Pre conditions violated
	 */
	public boolean canBuildCity(VertexLocation vertLoc) 
			throws NonMoveException {
				return false;
		
	}
	
	/**
	 * Checks to see if a user may place a road at a given edge by calling the
	 * inspector.
	 * @pre edgeLoc is valid, game is in state for user to place road
	 * @post Map reflects valid result
	 * @param edgeLoc location of edge to place road
	 * @return Whether or not a user may place a road
	 * @throws NonMoveException Pre condition violated
	 */
	public boolean canBuildRoad(EdgeLocation edgeLoc) throws NonMoveException {
		return false;
		
	}
	
	/**
	 * Asks the inspector if the robber may be placed on the given hex
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
	 * Asks the inspector if a user may place a settlement there.
	 * @pre vertLoc is valid, game is in state where user can place settlement.
	 * @post Screen will reflect result
	 * @param vertLoc Location to put the user
	 * @return Whether or not a user may build a settlement there
	 * @throws NonMoveException Pre-Conditions violated
	 */
	public boolean canBuildSettlement(VertexLocation vertLoc) 
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
	public boolean canOfferTrade(ResourceType resource, int amount)
		throws PlayingException {
			return false;
	}
	
	public boolean canRollNumber() throws PlayingException {
		return false;
	}
	
	public boolean canDiscardCards() throws PlayingException {
		return false;
	}
	
	public boolean canMaritimeTrade() throws PlayingException {
		return false;
	}
	
	public boolean canFinishTurn() throws PlayingException {
		return false;
	}
	
	public boolean canBuyDevCard() throws PlayingException {
		return false;
	}
	
	 public boolean canUseYearOfPlenty() throws PlayingException {
		return false;
	}
	 
	public boolean canUseRoadBuilder() throws PlayingException {
		return false;
	}
	
	public boolean canUseSoldier() throws PlayingException {
		return false;
	}
	
	public boolean canUseMonopoly() throws PlayingException {
		return false;
	}
	
	 public boolean canUseMonument() throws PlayingException {
		return false;
	}
	 
	 //Model to Controller Interactions
	
	/**
	 * Imports the model into the controller/view
	 * @pre none
	 * @post Map view now refelcts everything in the model
	 * @retrun Model parts
	 */
	public void initFromModel() {
		
	}
}