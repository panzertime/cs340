package client.servercommunicator;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.net.*;
import java.io.*;


/**
 * Concrete implementation of IServerProxy for use with a real network server */
public class ServerProxy implements IServerProxy{
	
	/**
	 *	constructs new ServerProxy
	 */
	public ServerProxy(){
		serverURL = "";
		userCookie = "";
		gameCookie = "";
	}

	private String serverURL;

	private String userCookie;

	private String gameCookie;

	public void setURL(String URL){
		serverURL = URL;
	}

	private String matchBrackets(String matchable){
		char bracket = matchable.charAt(matchable.length() - 1);
		String closer;
		if(bracket == ']'){
			// System.out.println("Matching a [");		
			closer = "[";
		}
		else {
			// System.out.println("Matching a {");
			closer = "{";
		}
		return closer + matchable;
	}
	
	private JSONObject makeJSON(String stringJSON)
			throws ServerProxyException{
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(stringJSON);
	
			return json;
		}
		catch(Exception e){
			// System.out.println("Problem parsing JSON: " + stringJSON);
			e.printStackTrace();
			throw new ServerProxyException("JSON probably invalid", e);
		}
	}

	private JSONArray makeArray(String stringJSON)
			throws ServerProxyException{
		try {
			JSONParser parser = new JSONParser();
			JSONArray json = (JSONArray) parser.parse(stringJSON);
	
			return json;
		}
		catch(Exception e){
			// System.out.println("Problem parsing JSON: " + stringJSON);
			e.printStackTrace();
			throw new ServerProxyException("JSON probably invalid", e);
		}
	}


	/**
	*	Returns a String which represents the body of the HTTP Response
	*	@param method HTTP method of call
	*	@param endpoint the API endpoint to call
	*	@param arguments the JSON to send to that endpoint
	*/
	private String submitRequest(String method, String endpoint, JSONObject arguments)
			throws ServerProxyException {

		try {
			String cookie = "catan.user=" + userCookie;
			if(!gameCookie.equals("")){
				cookie = cookie + "; " + gameCookie;
			}
			URLConnection connectionSeed = new URL(serverURL + endpoint).openConnection();
			HttpURLConnection connection = (HttpURLConnection) connectionSeed;
			connection.setRequestProperty("Cookie", cookie);
			// System.out.println("Cookie for POST " + endpoint + " is: " + cookie);
			// System.out.println("User cookie: " + userCookie);
			// System.out.println("Game cookie: " + gameCookie);
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Content-Length", String.valueOf(arguments.toJSONString().length()));

			OutputStream requestBody = 
				new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			requestBody.write(arguments.toJSONString().getBytes());
			requestBody.flush();
			requestBody.close();

			// System.out.println("POST arguments are: " + arguments.toJSONString());
	
			if (connection.getResponseCode() != 200) {
				String problemMessage = "Request returned 400, server says: "
					+ connection.getResponseMessage();
				System.out.println(problemMessage);
				throw new ServerProxyException(problemMessage);
			}

			
			connection.getContent();
			connection.getContentType();

			
			DataInputStream responseBody = 
				new DataInputStream(new BufferedInputStream(connection.getInputStream()));

			StringBuilder JSONBuilder = new StringBuilder();
			InputStreamReader JSONReader = new InputStreamReader(responseBody);
//			if(JSONReader.ready()){
//				System.out.println("Reading in body of request					))<>((");
				int letter = JSONReader.read();
//				System.out.println((char) letter);
				
				letter = JSONReader.read();
			//	letter = (int) '[';
//				System.out.println("First (second) character in JSON: " + (char) letter);
				while(letter >= 0){
//					System.out.println("Adding to JSON string: " + (char) letter);
					JSONBuilder.append((char) letter);
					letter = JSONReader.read();
				}
//			}
			JSONReader.close();
//			System.out.println("Matching brackets for string: " + JSONBuilder.toString());
			return matchBrackets(JSONBuilder.toString());

		}

		catch(Exception e){
			e.printStackTrace();
			throw new ServerProxyException("Exception during HTTP request submission", e);
		}
	}

	/**
	*	Returns a String which represents the body of the HTTP Response
	*	@param method HTTP method of call
	*	@param endpoint the API endpoint to call
	*/
	private String submitRequest(String method, String endpoint)
			throws ServerProxyException {

		try {

			String cookie = "catan.user=" + userCookie;
			if(!gameCookie.equals("")){
				cookie = cookie + "; " + gameCookie;
			}
			URLConnection connectionSeed = new URL(serverURL + endpoint).openConnection();
			HttpURLConnection connection = (HttpURLConnection) connectionSeed;
			connection.setRequestProperty("Cookie", cookie);
			// System.out.println("Cookie for GET " + endpoint + " is: " + cookie);
			// System.out.println("User cookie: " + userCookie);
			// System.out.println("Game cookie: " + gameCookie);
			connection.setRequestMethod(method);

			
			if (connection.getResponseCode() != 200) {
				String problemMessage = "Request returned 400, " + endpoint + " says: "
					+ connection.getResponseMessage();
				
				throw new ServerProxyException(problemMessage);
			}

			connection.getContent();
			connection.getContentType();
			
			DataInputStream responseBody = 
				new DataInputStream(new BufferedInputStream(connection.getInputStream()));

			StringBuilder JSONBuilder = new StringBuilder();
			InputStreamReader JSONReader = new InputStreamReader(responseBody);
//			if(JSONReader.ready()){
		//		System.out.println("Reading in body of request					))<>((");
				int letter = JSONReader.read();
//				System.out.println((char) letter);
				
				letter = JSONReader.read();
			//	letter = (int) '[';
			//	System.out.println((char) letter);
				while(letter >= 0){
			//		System.out.println((char) letter);
					JSONBuilder.append((char) letter);
					letter = JSONReader.read();
				}
//			}
			JSONReader.close();
			return matchBrackets(JSONBuilder.toString());
		}

		catch(Exception e){
			e.printStackTrace();
			throw new ServerProxyException("Exception during HTTP request submission", e);

		}
	}

		
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
	public JSONObject loginUser(JSONObject credentials) 
			throws ServerProxyException {
		try {
			URLConnection connectionSeed = new URL(serverURL + "/user/login").openConnection();
			HttpURLConnection connection = (HttpURLConnection) connectionSeed;
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			connection.setRequestProperty( "Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty( "Content-Length", String.valueOf(credentials.toJSONString().length()));

			OutputStream requestBody = 
				new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			requestBody.write(credentials.toJSONString().getBytes());
			requestBody.flush();
			requestBody.close();


	
			if (connection.getResponseCode() != 200) {
				String problemMessage = "Request returned 400, server says: "
					+ connection.getResponseMessage();
				System.out.println(problemMessage);
				throw new ServerProxyException(problemMessage);
			}
			
			DataInputStream responseBody = 
				new DataInputStream(new BufferedInputStream(connection.getInputStream()));

			StringBuilder JSONBuilder = new StringBuilder();
			InputStreamReader JSONReader = new InputStreamReader(responseBody);
			
			int letter = JSONReader.read();
			while(letter != -1){
				JSONBuilder.append((char) letter);
				letter = JSONReader.read();
			}
			
			JSONReader.close();


			if (JSONBuilder.toString().equals("Success")){
				userCookie = connection.getHeaderField("Set-cookie");
				userCookie = userCookie.substring(11);
				userCookie = userCookie.substring(0, userCookie.length() - 8);
				// System.out.println("Setting cookie: " + userCookie);

				return makeJSON(URLDecoder.decode(userCookie, "UTF-8"));
			}
			else {
				throw new ServerProxyException("Login failed, server says: " + JSONBuilder.toString());
			}
		}

		catch(Exception e){
			System.out.println("Login exception: " );
			e.printStackTrace();
			throw new ServerProxyException("Exception during HTTP request submission", e);
		}

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
	public JSONObject registerUser(JSONObject credentials)
			throws ServerProxyException {
		try {
			URLConnection connectionSeed = new URL(serverURL + "/user/register").openConnection();
			HttpURLConnection connection = (HttpURLConnection) connectionSeed;
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			connection.setRequestProperty( "Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty( "Content-Length", String.valueOf(credentials.toJSONString().length()));

			OutputStream requestBody = 
				new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			requestBody.write(credentials.toJSONString().getBytes());
			requestBody.flush();
			requestBody.close();


	
			if (connection.getResponseCode() != 200) {
				String problemMessage = "Request returned 400, server says: "
					+ connection.getResponseMessage();
				System.out.println(problemMessage);
				throw new ServerProxyException(problemMessage);
			}
			
			DataInputStream responseBody = 
				new DataInputStream(new BufferedInputStream(connection.getInputStream()));

			StringBuilder JSONBuilder = new StringBuilder();
			InputStreamReader JSONReader = new InputStreamReader(responseBody);
			
			int letter = JSONReader.read();
			while(letter != -1){
				JSONBuilder.append((char) letter);
				letter = JSONReader.read();
			}
			
			JSONReader.close();



			if (JSONBuilder.toString().equals("Success")){
				userCookie = connection.getHeaderField("Set-cookie");
				userCookie = userCookie.substring(11);
				userCookie = userCookie.substring(0, userCookie.length() - 8);
				

				return makeJSON(URLDecoder.decode(userCookie, "UTF-8"));
			}
			else {
				throw new ServerProxyException("Login failed, server says: " + JSONBuilder.toString());
			}
		}

		catch(Exception e){
			System.out.println("Registration exception: " );
			e.printStackTrace();
			throw new ServerProxyException("Exception during HTTP request submission", e);
		}

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
	public JSONArray listGames() throws ServerProxyException{
		try {
			return makeArray(submitRequest("GET", "/games/list"));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}			
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
		try{
			return makeJSON(submitRequest("POST", "/games/create", createGameRequest));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}			
	}
	
	/**
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
		try {
			String cookie = "catan.user=" + userCookie;		
			URLConnection connectionSeed = new URL(serverURL + "/games/join").openConnection();
			HttpURLConnection connection = (HttpURLConnection) connectionSeed;
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Cookie", cookie);			
			connection.setDoOutput(true);
			
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Content-Length", String.valueOf(joinGameRequest.toJSONString().length()));

			OutputStream requestBody = 
				new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			requestBody.write(joinGameRequest.toJSONString().getBytes());
			requestBody.flush();
			requestBody.close();


	
			if (connection.getResponseCode() != 200) {
				String problemMessage = "Request returned 400, server says: "
					+ connection.getResponseMessage();
				System.out.println(problemMessage);
				throw new ServerProxyException(problemMessage);
			}
			
			DataInputStream responseBody = 
				new DataInputStream(new BufferedInputStream(connection.getInputStream()));

			StringBuilder JSONBuilder = new StringBuilder();
			InputStreamReader JSONReader = new InputStreamReader(responseBody);
			
			int letter = JSONReader.read();
			while(letter != -1){
				JSONBuilder.append((char) letter);
				letter = JSONReader.read();
			}
			
			JSONReader.close();



			if (JSONBuilder.toString().equals("Success")){
				gameCookie = connection.getHeaderField("Set-cookie");
				String[] cookieParts = gameCookie.split(";");
				gameCookie = cookieParts[0];
				

				return true;
			}
			else {
				throw new ServerProxyException("Join game failed, server says: " + JSONBuilder.toString());
			}
		}

		catch(Exception e){
			System.out.println("Registration exception: " );
			e.printStackTrace();
			throw new ServerProxyException("Exception during HTTP request submission", e);
		}

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
		try {
			if(submitRequest("POST", "/games/save", saveGameRequest).equals("Success")){
				return true;
			}
			return false;
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}	
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
	public boolean loadGame(JSONObject loadGameRequest)
			throws ServerProxyException {
		try {
			if(submitRequest("POST", "/games/load", loadGameRequest).equals("Success")){
				return true;
			}
			return false;
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}	
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
	public JSONObject getModel(Integer currentVersion)
			throws ServerProxyException  {
		try {
			String call;
			if(currentVersion == null) {
				call= "/game/model";
			} else {
				call= "/game/model?version=" + currentVersion;
			}
			String response = submitRequest("GET", call);
			if(response.equals("{true\"")){
				return null;
			}
			return makeJSON(submitRequest("GET", call));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}	
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
		try {
			return makeJSON(submitRequest("POST", "/game/reset"));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}	
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
		try {
			return makeJSON(submitRequest("GET", "/game/commands"));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}	
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
		try {
			return makeJSON(submitRequest("POST", "/game/commands", listOfCommands));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}	
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
		try {
			 if (submitRequest("POST", "/game/addAI", addAIRequest).equals("{uccess")) {
			 	return true;
			}
			return false;

		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}
	}
	
	/**
	 * Returns the different type of AI players available
	 * @pre none
	 * @post A list of different types of AI players is given
	 * @return A string array of different type of AI players 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONArray listAI() throws ServerProxyException {
		try {
			return makeArray(submitRequest("GET", "/game/listAI"));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}
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
		try {
			return makeJSON(submitRequest("POST", "/moves/sendChat", sendChat));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}
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
		try {
			return makeJSON(submitRequest("POST", "/moves/rollNumber", rollNumber));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}
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
		try {
			return makeJSON(submitRequest("POST", "/moves/robPlayer", robPlayer));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

	}
	
	/**
	 * Signals that the player's turn is ended
	 * @pre User logged in and member of game
	 * @return An updated game model
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject finishTurn(JSONObject finishMove) throws ServerProxyException {
		try {
			return makeJSON(submitRequest("POST", "/moves/finishTurn", finishMove));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

	}
	
	/**
	 * Signals that the player would like to buy a devCard
	 * @pre User is logged in and member of game
	 * @return an updated game model 
	 * @throws ServerProxyException
	 */
	 @Override
	public JSONObject buyDevCard(JSONObject buyDev) throws ServerProxyException {
		try {
			return makeJSON(submitRequest("POST", "/moves/buyDevCard", buyDev));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}
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
		try {
			return makeJSON(submitRequest("POST", "/moves/Year_of_Plenty", resources));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
		try {
			return makeJSON(submitRequest("POST", "/moves/Road_Building", edgeLocations));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}
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
		try {
			return makeJSON(submitRequest("POST", "/moves/Soldier", soldierArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
		try {
			return makeJSON(submitRequest("POST", "/moves/Monopoly", resource));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
	public JSONObject monument(JSONObject monumentArgs) throws ServerProxyException{
		try {
			return makeJSON(submitRequest("POST", "/moves/Monument", monumentArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
		try {
			return makeJSON(submitRequest("POST", "/moves/buildRoad", buildRoadArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
		try {
			return makeJSON(submitRequest("POST", "/moves/buildSettlement", buildSettlementArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
		try {
			return makeJSON(submitRequest("POST", "/moves/buildCity", buildCityArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
		try {
			return makeJSON(submitRequest("POST", "/moves/offerTrade", offerTradeArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
		try {
			return makeJSON(submitRequest("POST", "/moves/acceptTrade", acceptTradeArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}
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
		try {
			return makeJSON(submitRequest("POST", "/moves/maritimeTrade", maritimeTradeArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
		try {
			return makeJSON(submitRequest("POST", "/moves/discardCards", discardArgs));
		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}

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
	public boolean changeLogLevel(JSONObject logLevel) throws ServerProxyException{
		try {	
			 if (submitRequest("POST", "/util/changeLogLevel", logLevel).equals("Success")) {
			 	return true;
			}
			return false;

		}
		catch(Exception e) {
			throw new ServerProxyException(e);
		}
	}
}
