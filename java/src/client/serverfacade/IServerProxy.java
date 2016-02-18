package client.serverfacade;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.net.*;
import java.io.*;



/**
 * This interface is to be implemented for use on the clients machine.
 * The object that implements this class will act like a server for the game
 * model. Simultaneously, requests will be sent to the server to retrieve
 * the necessary information for the game model.
 */
public interface IServerProxy {

	/**
	 * used to set the URL of a proxy
	 * @param URL the new URL of the proxy
	 */
	public void setURL(String URL);
	
	
	/**
	 * Checks the server to see if the given credentials are valid. If so
	 * it logs the user into the system
	 * @pre credentials are not null
	 * @post if the given credentials are valid it will the set catanUser
	 * cookie and return true. Otherwise it will just return false.
	 * If something went wrong with the connection it will throws
	 * a ServerProxyException
	 * @param credentials username and password
	 * @return whether or not it was successful
	 * @throws ServerProxyException problems with connection or in request
	 */
	public JSONObject loginUser(JSONObject credentials) 
			throws ServerProxyException;
	
	/**
	 * Creates a new account for the given credentials. It will also log the
	 * user into the system simultaneously.
	 * @pre credentials are not null and the given username is not already in
	 * use
	 * @post if successful, the new user is created in the system and its
	 * cantanUser cookie is set. It will return true. Otherwise it returns
	 * false. It something went wrong with the connection it will throws 
	 * a ServerProxyException.
	 * @param credentials username and password of new user
	 * @return whether or not it was successful
	 * @throws ServerProxyException problems with connection or in request
	 */
	public JSONObject registerUser(JSONObject credentials)
			throws ServerProxyException;
	
	/**
	 * Returns a list of all games currently in progress.
	 * @pre none
	 * @post will return a list of all the games if successful. Otherwise
	 * it will throws a ServerProxyException.
	 * @return A list of all games in progress if successful. Otherwise null
	 * @throws ServerProxyException problems with connection
	 */
	public JSONArray listGames() throws ServerProxyException;
	
	/**
	 * Creates a new game on the server with the given parameters.
	 * @pre createGameRequest is not null. Name is not null, randomTiles
	 * randomNumbers and randomPorts contain valid boolean values.
	 * @post If successful, a new game is created on the server and it
	 * returns info about the game. If not will throws a ServerProxyException
	 * @param createGameRequest Include the boolean options for a new game
	 * as well as the game name.
	 * @return description of newly created game
	 * @throws ServerProxyException problems with connection or in request
	 */
	public JSONObject createGame(JSONObject createGameRequest) 
		throws ServerProxyException;
	
	/**
	 * Adds a user to a given game.
	 * @pre joinGameRequest contains a valid game ID and color
	 * @post User is added to the given game. In the case the server is unable
	 * to do so a ServerProxyException is throwsn.
	 * @param joinGameRequest ID of game and color of user
	 * @return whether or not the user was successfully added
	 * @throws ServerProxyException problems with connection or in request
	 */
	public boolean joinGame(JSONObject joinGameRequest) 
		throws ServerProxyException;
	
	/**
	 * Saves the current state of the game. Should only be used for debugging
	 * purposes.
	 * @pre game ID is valid and file name is not empty
	 * @post The given game is saved on the server with that name if 
	 * successful. Otherwise it throws an exception with a message.
	 * @param saveGameRequest Game ID and file name to be saved under
	 * @return Whether or not it was able to save the game
	 * @throws ServerProxyException problems with connection or in request
	 */
	public boolean saveGame(JSONObject saveGameRequest)
		throws ServerProxyException;
	
	/**
	 * Loads a previous game saved for debugging purposes.
	 * @pre The given file name exists on the server
	 * @post The specified game from the file is loaded into Catan if
	 * successful. Otherwise an exception is thrown. 
	 * @param loadGameRequest file name of previously saved game
	 * @return The specified game from the file
	 * @throws ServerProxyException problems with connection or in request
	 */
	public boolean loadGame(JSONObject loadGameRequest)
		throws ServerProxyException;
	
	/**
	 * Checks to see if the given model version is current. If so, it does
	 * nothing. Otherwise it returns the full game model.
	 * @pre User must be logged in and in a game. If a version
	 * number is included, it must be a valid integer.
	 * @post It will either return an updated game model or true. If an error
	 * occurs it will throw an exception. 
	 * @param versionNumber optional check to see if model version is current
	 * @return the current state of the game or true if the model is current
	 * @throws ServerProxyException problems with connection or in request
	 */
	public JSONObject getModel(int currentVersion)
		throws ServerProxyException;
	
	/**
	 * Returns a model of the state of a game right after the initial placement
	 * round if the game were the default. For server created games, the game
	 * reverts back to before the initial placement round.
	 * @pre none
	 * @post A reset game model is returned as described here
	 * @return A reset game model
	 * @throws ServerProxyException problems with connection
	 */
	public JSONObject reset() throws ServerProxyException;	
	
	/**
	 * Gets a list of all the commands called in the game. Used for debugging
	 * purposes.
	 * @pre You must be logged in and playing a game
	 * @post either throws an error with connection or returns a list of
	 * commands
	 * @return A list of every command that has been executed in the game so
	 * far
	 * @throws ServerProxyException problems with the connection
	 */
	public JSONObject getCommands() throws ServerProxyException;
	
	/**
	 * Executes the given commands on the game. Ideally a game would have been
	 * restarted. This is for debugging purposes.
	 * @pre User is logged on and currently in a game
	 * @post The state of the game will follow the flow of the given commands
	 * @param listOfCommands Commands to be executed in the game
	 * @return Game model after the given commands have been applied
	 * @throws ServerProxyException if a problem with connection or parameters
	 */
	public JSONObject executeCommands(JSONObject listOfCommands) 
			throws ServerProxyException;
	
	/**
	 * Adds an AI player to the current game
	 * @pre User must be logged into the system and have joined a game
	 * @post The selected AI player is added to the game and the server assigns
	 * a name and color.
	 * @param addAIRequest type of AI to add to the game (only LARGEST_ARMY 
	 * currently exists)
	 * @return true if successful
	 * @throws ServerProxyException Not a valid AI Request, Connection error
	 */
	public boolean addAI(JSONObject addAIRequest) 
			throws ServerProxyException;
	
	/**
	 * Returns the different type of AI players available
	 * @pre none
	 * @post A list of different types of AI players is given
	 * @return A string array of different type of AI players 
	 * @throws ServerProxyException
	 */
	public JSONArray listAI() throws ServerProxyException;
	
	/**
	 * Sends a message to chat part of the model
	 * @pre User is logged in and has joined a game
	 * @post chat contains new message at the end
	 * @param sendChat type, playerIndex and content
	 * @return An updated game model 
	 * @throws ServerProxyException Problem with connection or given command
	 */
	public JSONObject sendChat(JSONObject sendChat) 
			throws ServerProxyException;
	/**
	 * Tells the server what number was rolled.
	 * @pre User is logged in, has joined a game, it's his turn and the model
	 * status is 'Rolling'
	 * @post Model status is now 'discarding', 'robbing' or 'playing'.
	 * @param rollNumber type(rollNumber), playerIndex and die number(2-12)
	 * @return An updated game model 
	 * @throws ServerProxyException Problem with connection or given command
	 */
	public JSONObject rollNumber(JSONObject rollNumber) 
			throws ServerProxyException;
	
	/**
	 * Moves the robber and reallocates the robbed resource card, if there is
	 * one.
	 * @pre User is logged in, has joined a game, the robber has moved and the 
	 * player being robbed has resource cards, if there is a player being 
	 * robbed.
	 * @post The robber is moved and the robbed resource cards have been 
	 * changed.
	 * @param robPlayer type(robPlayer), playerIndex, victimIndex and location
	 * @return An updated game model 
	 * @throws ServerProxyException Problem with connection or given command
	 */
	public JSONObject robPlayer(JSONObject robPlayer) 
			throws ServerProxyException;
	
    /**
     * tells the server that the current state for the player is over
     * @pre User is logged in, in a game, it his turn, and the client is
     * 'playing'
     * @post cards in new dev hand are transfered to old dev card hand and the
     * next player has his turn.
     * @return gameModel
     * @throws ServerProxyException Problem with connection
     */
	public JSONObject finishTurn(JSONObject JSONObject) 
			throws ServerProxyException;
	
    /**
     * Tells server that player is buying a dev card
     * @pre User is logged in and in a game, user has required resources, and
     * there are still dev cards left.
     * @post If the user is eligible to buy a dev card, he will receive one at
     * random. If it's a monument card it will be added to the old devcard
     * hand, otherwise it goes to the new hand.
     * @param buyDevCard and playerIndex
     * @return updated Game Model
     * @throws ServerProxyException Problem with connection
     */
	public JSONObject buyDevCard(JSONObject buyDevCard) 
			throws ServerProxyException;
	
    /**
     * Tells the server what cards the player took from the bank
     * @pre User is logged in, in a game, on his turn, client model is
     * 'playing', he has not yet used a non-monument dev card, the two
     * specified dev cards are in the bank.
     * @post User gains two specified resources
     * @param Year_of_Plenty, playerIndex, resource1, resource 2
     * @return updated game model
     * @throws ServerProxyException Problem with connection
     */
	public JSONObject yearOfPlenty(JSONObject yearOfPlenty) 
			throws ServerProxyException;
	
    /**
     * Tells the server what road was built
     * @pre User is logged in, in a game, its his turn, the client is
     * 'playing', the road location is valid, open, connected to another road
     * owned by the player, the location is not on water, the required
     * resources are had, and it has no adjacent road in the setup round
     * @param Road_Building, playerIndex, (edgeLocation) spot1, spot2
     * @param 	edgeLocation(x,y, direction)
     * @return updated Game Model
     * @throws ServerProxyException Problem with connection
     */
	public JSONObject roadBuilding(JSONObject roadBuilding) 
			throws ServerProxyException;
	
    /**
     * updates the soldier command on the server
     * @pre User is logged in, in a game, it is her turn, client model status
     * is 'playing', she has not yet played non-monument dev card. Robber is
     * not being kept in the same location, player being robbed (if exists)
     * has resource cards.
     * @post robber has new location, robbed player loses cards, current player
     * gains them, largest army is awarded - if applicable, you are no longer
     * able to play more development cards.
     * @param type, playerIndex, victimIndex, location(x,y)
     * @return updated game model
     * @throws ServerProxyException Problem with connection
     */
	public JSONObject soldier(JSONObject soldier) throws ServerProxyException;
	
	/**
	 * User wants to use a monopoly card
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns the card, has not played non-monument card this turn
	 * @param resource type(resource), resource
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject monopoly(JSONObject resource) throws ServerProxyException;
	
	/**
	 * User wants to play a monument card
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns the card, has not played non-monument card this turn,
	 *	has enough monument cards to win the game
	 * @post User gains a victory point
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject monument(JSONObject monumentArgs) throws ServerProxyException;
	
	/**
	 * User wants to place a road on the map
	 * @pre User is logged in and member of game, users's turn, client is "Playing,"
	 *	road location is open, road location connected to player's existing 
	 *	road, player owns required resources, if during setup, must attach to
	 *	settlement without an existing road
	 * @post User has lost the resources needed for the road, road is on map at
	 *	proper location, player with longest road owns "longest road" award
	 * @param buildRoadArgs type(buildRoadArgs), free, roadLocation
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject buildRoad(JSONObject buildRoadArgs) throws ServerProxyException;
	
	/**
	 * User wants to place a settlement on the map
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	settlement location is open, settlement location connected to player's 
	 *	existing road (except during setup), player owns required resources,
	 *	not adjacent to existing settlement
	 * @post User has lost the resources needed for the settlement, settlement is on 
	 *	map at proper location
	 * @param buildSettlementArgs type(buildSettlementArgs), free, vertexLocation
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject buildSettlement(JSONObject buildSettlementArgs) throws ServerProxyException;
	
	/**
	 * User wants to place a city on the map
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	city location contains users's settlement, user owns required resources
	 * @post User has lost the resources needed for the city, city is on map at
	 *	proper location, player gets settlement back
	 * @param buildCityArgs type(buildCityArgs), vertexLocation
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject buildCity(JSONObject buildCityArgs) throws ServerProxyException;
	
	/**
	 * User wants to make a trade
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns resources on offer
	 * @post trade is offered to other player
	 * @param offerTradeArgs type(offerTradeArgs), offer, receiver
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject offerTrade(JSONObject offerTradeArgs) throws ServerProxyException;
	
	/**
	 * User must respond to a trade offer
	 * @pre User is logged in and member of game, users's turn,
	 *	domestic trade has been offered to user, user has required resources
	 * @post if accepted: user and offering player swap specified resources,
	 *	if denied: no resources are exchanged; trade offer removed
	 * @param acceptTradeArgs type(acceptTradeArgs), willAccept
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject acceptTrade(JSONObject acceptTradeArgs) throws ServerProxyException;
	
	/**
	 * User wants to make maritime trade
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns resources he wants to give, if ratio is less than 4 player must
	 *	have correct port
	 * @post trade is executed (resources move to bank, requested resource to player)
	 * @param maritimeTradeArgs type(maritimeTradeArgs), ratio, inputResource, inputResource
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject maritimeTrade(JSONObject maritimeTradeArgs) throws ServerProxyException;
	
	/**
	 * User intends to discard cards
	 * @pre User is logged in and member of game, users's turn,
	 *	client status is "Discarding," user has more than 7 cards, user has cards to discard
	 * @post user loses these resources, if user is last to discard, client status changes to "Robbing"
	 * @param discardArgs type(discardArgs), discardedCards
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public JSONObject discardCards(JSONObject discardArgs) throws ServerProxyException;
	
	/**
	 * Client for some reason intends to adjust server log level
	 * @pre Valid log level specified: SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST
	 * @post if successful: return HTTP200 with "Success" in body, server uses that logging level
	 *	if unsuccessful: return HTTP400 with error message in body
	 * @param logLevel type(logLevel), logLevel
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	public boolean changeLogLevel(JSONObject logLevel) throws ServerProxyException;
}