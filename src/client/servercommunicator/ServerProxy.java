package cs340.client.servercommunivator;

/**
 * Concrete implementation of IServerProxy for use with a real network server */
public class ServerProxy implements IServerProxy{
	
	/**
	 *	constructs new ServerProxy
	 */
	public ServerProxy(){
		};
		
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
	 @Override
	public boolean loginUser(JSONObject credentials) 
			throws ServerProxyException {
			return true;
	}
	
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
	 @Override
	public boolean registerUser(JSONObject credentials)
			throws ServerProxyException {
			return true;
	}
	
	/**
	 * Returns a list of all games currently in progress.
	 * @pre none
	 * @post will return a list of all the games if successful. Otherwise
	 * it will throws a ServerProxyException.
	 * @return A list of all games in progress if successful. Otherwise null
	 * @throws ServerProxyException problems with connection
	 */
	 @Override
	public JSONObject listGames() throws ServerProxyException{
		return new JSONObject();
	}
	
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
	 @Override
	public JSONObject createGame(JSONObject createGameRequest) 
		throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * Adds a user to a given game.
	 * @pre joinGameRequest contains a valid game ID and color
	 * @post User is added to the given game. In the case the server is unable
	 * to do so a ServerProxyException is throwsn.
	 * @param joinGameRequest ID of game and color of user
	 * @return whether or not the user was successfully added
	 * @throws ServerProxyException problems with connection or in request
	 */
	 @Override
	public boolean joinGame(JSONObject joinGameRequest) 
		throws ServerProxyException {
		return new JSONObject();
	}
	
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
	 @Override
	public boolean saveGame(JSONObject saveGameRequest)
		throws ServerProxyException {
		return true;
	}

	
	/**
	 * Loads a previous game saved for debugging purposes.
	 * @pre The given file name exists on the server
	 * @post The specified game from the file is loaded into Catan if
	 * successful. Otherwise an exception is thrown. 
	 * @param loadGameRequest file name of previously saved game
	 * @return The specified game from the file
	 * @throws ServerProxyException problems with connection or in request
	 */
	 @Override
	public JSONObject loadGame(JSONObject loadGameRequest)
		throws ServerProxyException {
		return new JSONObject();
	}
	
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
	 @Override
	public JSONObject getModel(JSONObject versionNumber)
		throws ServerProxyException  {
		return new JSONObject();
	}
	
	/**
	 * Returns a model of the state of a game right after the initial placement
	 * round if the game were the default. For server created games, the game
	 * reverts back to before the initial placement round.
	 * @pre none
	 * @post A reset game model is returned as described here
	 * @return A reset game model
	 * @throws ServerProxyException problems with connection
	 */
	 @Override
	public JSONObject reset() throws ServerProxyException  {
		return new JSONObject();
	}	
	
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
	 @Override
	public JSONObject getCommands() throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * Executes the given commands on the game. Ideally a game would have been
	 * restarted. This is for debugging purposes.
	 * @pre User is logged on and currently in a game
	 * @post The state of the game will follow the flow of the given commands
	 * @param listOfCommands Commands to be executed in the game
	 * @return Game model after the given commands have been applied
	 * @throws ServerProxyException if a problem with connection or parameters
	 */
	 @Override
	public JSONObject executeCommands(JSONObject listOfCommands) 
			throws ServerProxyException {
		return new JSONObject();
	}
	
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
	 @Override
	public boolean addAI(JSONObject addAIRequest) 
			throws ServerProxyException {
		return true;
	}
	
	/**
	 * Returns the different type of AI players available
	 * @pre none
	 * @post A list of different types of AI players is given
	 * @return A string array of different type of AI players 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject listAI() throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * Sends a message to chat part of the model
	 * @pre User is logged in and has joined a game
	 * @post chat contains new message at the end
	 * @param sendChat type, playerIndex and content
	 * @return An updated game model 
	 * @throws ServerProxyException Problem with connection or given command
	 */
	 @Override
	public JSONObject sendChat(JSONObject sendChat) 
			throws ServerProxyException {
		return new JSONObject();
	}
	/**
	 * Tells the server what number was rolled.
	 * @pre User is logged in, has joined a game, it's his turn and the model
	 * status is 'Rolling'
	 * @post Model status is now 'discarding', 'robbing' or 'playing'.
	 * @param rollNumber type(rollNumber), playerIndex and die number(2-12)
	 * @return An updated game model 
	 * @throws ServerProxyException Problem with connection or given command
	 */
	 @Override
	public JSONObject rollNumber(JSONObject rollNumber) 
			throws ServerProxyException {
		return new JSONObject();
	}
	
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
	 @Override
	public JSONObject robPlayer(JSONObject robPlayer) 
			throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * Signals that the player's turn is ended
	 * @pre User logged in and member of game
	 * @return An updated game model
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject finishTurn() throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * Signals that the player would like to buy a devCard
	 * @pre User is logged in and member of game
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject buyDevCard() throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * User wants to play yearOfPlenty card
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns the card, has not played non-monument card this turn,
	 *	specified resources are in the bank
	 * @post User gains the two specified resources
	 * @param resources type(resources), resource1 and resource2
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject yearOfPlenty(JSONObject resources) throws ServerProxyException {
		return new JSONObject();
	}

	/**
	 * User wants to build a road
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns the card, has not played non-monument card this turn,
	 *	first road loc. is connected to your existing road, second road loc. is 
	 *	connected to that or another one of your roads, neither is on water,
	 *	you have at least two unused roads
	 * @post User has two fewer unused roads, two new roads appear at specified location, 
	 *	Player with longest road has "longest road" award
	 * @param edgeLocations type(edgeLocations), spot1, spot2
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject roadBuilding(JSONObject edgeLocations) throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * User wants to play a soldier card
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns the card, has not played non-monument card this turn,
	 *	robber not already at that location, if the player is being robbed,
	 *	they must have resource cards
	 * @post Robber in new location, player being robbed gives you a resource card,
	 *	player with most soldier cards owns "largest army" award, player may not
	 *	play other dev cards, aside from monument cards
	 * @param soldierArgs type(soldierArgs), location, victimIndex
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject soldier(JSONObject soldierArgs) throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * User wants to use a monopoly card
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns the card, has not played non-monument card this turn
	 * @param resource type(resource), resource
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject monopoly(JSONObject resource) throws ServerProxyException {
		return new JSONObject();
	}
	
	/**
	 * User wants to play a monument card
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns the card, has not played non-monument card this turn,
	 *	has enough monument cards to win the game
	 * @post User gains a victory point
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject monument() throws ServerProxyException{
		return new JSONObject();
	}
	
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
	 @Override
	public JSONObject buildRoad(JSONObject buildRoadArgs) throws ServerProxyException{
		return new JSONObject();
	}
	
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
	 @Override
	public JSONObject buildSettlement(JSONObject buildSettlementArgs) throws ServerProxyException{
		return new JSONObject();
	}
	
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
	 @Override
	public JSONObject buildCity(JSONObject buildCityArgs) throws ServerProxyException{
		return new JSONObject();
	}
	
	/**
	 * User wants to make a trade
	 * @pre User is logged in and member of game, users's turn, client is "Playing," 
	 *	user owns resources on offer
	 * @post trade is offered to other player
	 * @param offerTradeArgs type(offerTradeArgs), offer, receiver
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject offerTrade(JSONObject offerTradeArgs) throws ServerProxyException{
		return new JSONObject();
	}
	
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
	 @Override
	public JSONObject acceptTrade(JSONObject acceptTradeArgs) throws ServerProxyException{
		return new JSONObject();
	}
	
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
	 @Override
	public JSONObject maritimeTrade(JSONObject maritimeTradeArgs) throws ServerProxyException{
		return new JSONObject();
	}
	
	/**
	 * User intends to discard cards
	 * @pre User is logged in and member of game, users's turn,
	 *	client status is "Discarding," user has more than 7 cards, user has cards to discard
	 * @post user loses these resources, if user is last to discard, client status changes to "Robbing"
	 * @param discardArgs type(discardArgs), discardedCards
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject discardCards(JSONObject discardArgs) throws ServerProxyException{
		return new JSONObject();
	}
	
	/**
	 * Client for some reason intends to adjust server log level
	 * @pre Valid log level specified: SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST
	 * @post if successful: return HTTP200 with "Success" in body, server uses that logging level
	 *	if unsuccessful: return HTTP400 with error message in body
	 * @param logLevel type(logLevel), logLevel
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject changeLogLevel(JSONObject logLevel) throws ServerProxyException{
		return new JSONObject();
	}
}



