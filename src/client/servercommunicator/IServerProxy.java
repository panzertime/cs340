package cs340.client.servercommunivator;

/**
 * This interface is to be implemented for use on the clients machine.
 * The object that implements this class will act like a server for the game
 * model. Simultaneously, requests will be sent to the server to retrieve
 * the necessary information for the game model.
 */
public interface IServerProxy {
	
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
	public boolean loginUser(JSONObject credentials) 
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
	public boolean registerUser(JSONObject credentials)
			throws ServerProxyException;
	
	/**
	 * Returns a list of all games currently in progress.
	 * @pre none
	 * @post will return a list of all the games if successful. Otherwise
	 * it will throws a ServerProxyException.
	 * @return A list of all games in progress if successful. Otherwise null
	 * @throws ServerProxyException problems with connection
	 */
	public JSONObject listGames() throws ServerProxyException;
	
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
	public JSONObject loadGame(JSONObject loadGameRequest)
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
	public JSONObject getModel(JSONObject versionNumber)
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
	public JSONObject listAI() throws ServerProxyException;
	
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
	 * 
	 * @pre 
	 * @post 
	 * @return 
	 * @throws ServerProxyException
	 */
	public JSONObject finishTurn() throws ServerProxyException;
	
	public JSONObject buyDevCard() throws ServerProxyException;
	
	public JSONObject yearOfPlenty() throws ServerProxyException;
	
	public JSONObject roadBuilding() throws ServerProxyException;
	
	public JSONObject soldier() throws ServerProxyException;
	
	public JSONObject monopoly() throws ServerProxyException;
	
	public JSONObject monument() throws ServerProxyException;
	
	public JSONObject buildRoad() throws ServerProxyException;
	
	public JSONObject buildSettlement() throws ServerProxyException;
	
	public JSONObject buildCity() throws ServerProxyException;
	
	public JSONObject offerTrade() throws ServerProxyException;
	
	public JSONObject acceptTrade() throws ServerProxyException;
	
	public JSONObject maritimeTrade() throws ServerProxyException;
	
	public JSONObject discardCards() throws ServerProxyException;
	
	public JSONObject changeLogLevel() throws ServerProxyException;
}
