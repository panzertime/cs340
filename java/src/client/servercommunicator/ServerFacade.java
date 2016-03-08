package client.servercommunicator;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import client.data.Games;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;
import shared.model.definitions.CatanColor;
import shared.model.hand.ResourceType;

public class ServerFacade {
	
	private static ServerFacade _instance;

	private int player_id;
	
	private ServerFacade(){
		proxy = new FakeProxy();
		player_id = -1;
		poller = new ServerPoller();
	}

	private IServerProxy proxy;
	
	private ServerPoller poller;
	
	public static ServerFacade get_instance() {
		if(_instance == null) {
			_instance = new ServerFacade();
		}
		
		return _instance;
	}

	public void setProxy(IServerProxy newProxy){
		proxy = newProxy;
	}
	
	private JSONObject makeJSON(String stringJSON)
			throws ServerProxyException{
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(stringJSON);
	
			return json;
		}
		catch(Exception e){
			// System.out.println("The offending JSON: ");
			// System.out.println(stringJSON);
			throw new ServerProxyException("JSON probably invalid", e);
		}
	}

	public int get_player_id(){
		return player_id;
	}
	

	//USER
	public int login(String username, String password) 
			throws ServerException {
		try {
			String credentials = "{ \"username\" : \"" + username 
						+ "\", \"password\" : \"" + password
						+ "\" }";
			JSONObject args = makeJSON(credentials);
			JSONObject cookie = proxy.loginUser(args);

			player_id = ((Long) cookie.get("playerID")).intValue();
			poller.start();
			poller.setPollerJoinGameState();
			return player_id;
		}
		catch(Exception e){
			e.printStackTrace();
		
			throw new ServerException("Problem logging in", e);
		}

	}

	public int register(String username, String password) 
			throws ServerException {
		try {
			String credentials = "{ \"username\" : \"" + username 
						+ "\", \"password\" : \"" + password
						+ "\" }";
			JSONObject args = makeJSON(credentials);
			JSONObject cookie = proxy.registerUser(args);
		//	System.out.println("Cookie is: " + cookie.toJSONString());
			player_id = ((Long) cookie.get("playerID")).intValue();
			poller.start();
			poller.setPollerJoinGameState();
			return player_id;

		}
		catch(Exception e){
			throw new ServerException("Problem registering user", e);
		}
	}

	//GAMES(PRE-GAME)
		//USED BY MODEL
	public JSONArray getGames() 
			throws ServerException {
		try {
			return proxy.listGames();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ServerException(e);
		}
	}

	public JSONObject createNewGame(boolean randomTiles, boolean randomNumbers, 
				boolean randomPorts, String name) 
				throws ServerException{
		try {
			String newGame = "{ \"randomTiles\" : " + randomTiles
					+ ", \"randomNumbers\" : " + randomNumbers
					+ ", \"randomPorts\" : " + randomPorts
					+ ", \"name\" : \"" + name + "\"}";
			JSONObject args = makeJSON(newGame);
			return proxy.createGame(args);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ServerException(e);
		}
	}

	public void joinGame(int gameID, CatanColor color) 
				throws ServerException{
		try {
			String joinGame = "{ \"id\" : " + gameID
					+ ", \"color\" : \"" + color.toString().toLowerCase() + "\" }";
			// System.out.println("Attempting to join with: " + joinGame);
			JSONObject args = makeJSON(joinGame);
			if(proxy.joinGame(args) == false){
				throw new ServerException("Join game failed");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ServerException(e);
		}
	}
	
		//ONLY USED BY SERVER COMMUNICATOR	
	public void saveGame(int gameID, String fileName) 
				throws ServerException{
		try {
			String saveGame = "{ id : " + gameID
				+ ", name : \"" + fileName + "\"}";
			JSONObject args = makeJSON(saveGame);
			if(proxy.saveGame(args) == false){
				throw new ServerException("Save game failed");
			}
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	
	public void loadGame(String fileName) 
				throws ServerException {
		try {
			String loadGame = "{name : \"" + fileName + "\"}";
			JSONObject args = makeJSON(loadGame);
			if(proxy.loadGame(args) == false){
				throw new ServerException("Load game failed");
			}
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	//GAMES(POST-JOIN)
	/**
	 * Used by the model when joining a game or by the poller to check for
	 * updates.
	 * @param version current Version number of game(optional = null)
	 * @return Updated game model OR Null if there is no update
	 */
	public JSONObject getModel(Integer version) 
			throws ServerException{
		try {
			return proxy.getModel(version);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
		//THIS METHOD IS JUST USED BY THE SERVER COMMUNICATOR
	/**
	 * Just used by the server communicator
	 */
	public JSONObject reset()
			throws ServerException {
		try {
			return proxy.reset();
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	/**
	 * 
	 * @param commands list of commands in the game
	 * @return Game Model
	 */
	public JSONObject postCommands(Map commands)
			throws ServerException {
		try {
			return proxy.executeCommands(new JSONObject(commands));
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	/**
	 * 
	 * @return list of commands in the game thus far
	 */
	public JSONObject getCommands()
			throws ServerException {
		try {
			return proxy.getCommands();
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
		//USED BY MODEL AS WELL
	public void addAI(String aiType)
			throws ServerException {
		try {
			JSONObject args = makeJSON("{ \"AIType\" : \"" + aiType + "\"}");
			if(!proxy.addAI(args)){
				throw new ServerException("Problem adding AI player");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ServerException(e);
		}
	}

	/**
	 * 
	 * @return list of AI Types
	 */
	public JSONArray listAI() 
		throws ServerException {
		try {
			return proxy.listAI();
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	//MOVES
	public JSONObject sendChat(int playerIndex, String message)
			throws ServerException {
		try {
			JSONObject json = new JSONObject();
			
			json.put("type", "sendChat");
			json.put("playerIndex", playerIndex);
			json.put("content", message);
			
			return proxy.sendChat(json);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	public JSONObject rollNumber(int playerIndex, int number) 
			throws ServerException {
		try {
			JSONObject json = new JSONObject();
			
			json.put("type", "rollNumber");
			json.put("playerIndex", playerIndex);
			json.put("number", number);
			
			return proxy.rollNumber(json);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject robPlayer(int playerIndex, int victimIndex, 
			HexLocation location) 
			throws ServerException {
		try {
			JSONObject json = new JSONObject();
			
			json.put("type", "robPlayer");
			json.put("playerIndex", playerIndex);
			json.put("victimIndex", victimIndex);
			json.put("location", location.toJSON());
			return proxy.robPlayer(json);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject finishTurn(int playerIndex) throws ServerException {
		try {
			JSONObject json = new JSONObject();
			
			json.put("type", "finishTurn");
			json.put("playerIndex", playerIndex);
			
			return proxy.buildSettlement(json);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject buyDevCard(int playerIndex) throws ServerException {
		try {
			/*String content = "{type: \"buyDevCard\", " +
						"playerIndex: " + playerIndex + "}";
			JSONObject args = makeJSON(content);*/
			JSONObject args = new JSONObject();
			args.put("type", "buyDevCard");
			args.put("playerIndex", playerIndex);
			
			return proxy.buyDevCard(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject yearOfPlenty(int playerIndex, ResourceType resource1, 
			ResourceType resource2) throws ServerException {
		try {
			String content = "{\"type\":\"Year_of_Plenty\", " +
						"\"playerIndex\":" + playerIndex + ", " +
						"\"resource1\":\"" + resource1.toString().toLowerCase() + "\", " +
						"\"resource2\":\"" + resource2.toString().toLowerCase() + "\"}";
			JSONObject args = makeJSON(content);
			return proxy.yearOfPlenty(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject roadBuilding(int playerIndex, EdgeLocation spot1, 
			EdgeLocation spot2) throws ServerException {
		try {
			String content = "{\"type\": \"Road_Building\", " +
						"\"playerIndex\":" + playerIndex + ", " +
						"\"spot1\":\"" + spot1.toJSON() + "\", " +
						"\"spot2\":\"" + spot2.toJSON() + "\"}";
			JSONObject args = makeJSON(content);
			return proxy.roadBuilding(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}


	public JSONObject soldier(int playerIndex, int victimIndex, 
			HexLocation location) throws ServerException {
		try {
			String content = "{\"type\":\"Soldier\", " +
						"\"playerIndex\":" + playerIndex + ", " +
						"\"victimIndex\":" + victimIndex + ", " +
						"\"location\":\"" + location.toJSON() + "\"}";
			JSONObject args = makeJSON(content);
			return proxy.soldier(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject monopoly(ResourceType resource, int playerIndex) 
			throws ServerException {
		try {
			String content = "{\"type\":\"Monopoly\", " +
						"\"resource\":\"" + resource.toString().toLowerCase() + "\", " +
						"\"playerIndex\":" + playerIndex + "}";
			JSONObject args = makeJSON(content);
			return proxy.monopoly(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject monument(int playerIndex) throws ServerException {
		try {
			String content = "{\"type\":\"Monument\", " +
						"\"playerIndex\":" + playerIndex + "}";
			JSONObject args = makeJSON(content);
			return proxy.monument(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject buildRoad(int playerIndex, EdgeLocation edgeLoc, 
			boolean setupMode) throws ServerException {
		try {			
			JSONObject json = new JSONObject();
			
			json.put("type", "buildRoad");
			json.put("playerIndex", playerIndex);
			json.put("free", setupMode);
			json.put("roadLocation", edgeLoc.toJSON());
			
			return proxy.buildRoad(json);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject buildSettlement(int playerIndex, VertexLocation vertLoc, 
			boolean setupMode) throws ServerException {
		try {
			JSONObject json = new JSONObject();
			
			json.put("type", "buildSettlement");
			json.put("playerIndex", playerIndex);
			json.put("free", setupMode);
			json.put("vertexLocation", vertLoc.toJSON());
			
			return proxy.buildSettlement(json);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject buildCity(int playerIndex, VertexLocation vertLoc) 
			throws ServerException {
		try {
			JSONObject json = new JSONObject();
			
			json.put("type", "buildCity");
			json.put("playerIndex", playerIndex);
			json.put("vertexLocation", vertLoc.toJSON());
			
			return proxy.buildCity(json);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject offerTrade(int playerIndex, Map<ResourceType, Integer> offer,
			int receiver) throws ServerException {
		try {
			JSONObject resList = new JSONObject();
			resList.put("brick", offer.get(ResourceType.BRICK));
			resList.put("ore", offer.get(ResourceType.ORE));
			resList.put("sheep", offer.get(ResourceType.SHEEP));
			resList.put("wheat", offer.get(ResourceType.WHEAT));
			resList.put("wood", offer.get(ResourceType.WOOD));
			
			JSONObject content = new JSONObject();
			content.put("type", "offerTrade");
			content.put("playerIndex", playerIndex);
			content.put("offer", resList);
			content.put("receiver", receiver);
			
			return proxy.offerTrade(content);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject acceptTrade(int playerIndex, boolean willAccept) 
			throws ServerException {
		try {
			/*String content = "{type: \"acceptTrade\", " +
						"playerIndex: " + playerIndex + ", " +
						"willAccept: " + willAccept +  "}";
			JSONObject args = makeJSON(content);*/
			JSONObject args = new JSONObject();
			args.put("type", "acceptTrade");
			args.put("playerIndex", playerIndex);
			args.put("willAccept", willAccept);
			
			return proxy.acceptTrade(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject maritimeTrade(int playerIndex, int ratio,
			ResourceType inputResource, ResourceType outputResource) 
			throws ServerException {
		try {
			String content = "{\"type\": \"maritimeTrade\", " +
						"\"playerIndex\":" + playerIndex + ", " +
						"\"ratio\":" + ratio + ", " +
						"\"inputResource\": \"" + inputResource.toString().toLowerCase() + "\", " +
						"\"outputResource\": \"" + outputResource.toString().toLowerCase() + "\"}";
			JSONObject args = makeJSON(content);
			return proxy.maritimeTrade(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public JSONObject discard(int playerIndex, Map<ResourceType, Integer> discardedCards)
			throws ServerException {
		try {
			/*StringBuilder resList = new StringBuilder();
			resList.append("{");
			resList.append("\"brick\" : " + discardedCards.get("BRICK") + ", ");
			resList.append("\"ore\" : " + discardedCards.get("ORE") + ", ");
			resList.append("\"sheep\" : " + discardedCards.get("SHEEP") + ", ");
			resList.append("\"wheat\" : " + discardedCards.get("WHEAT") + ", ");
			resList.append("\"wood\" : " + discardedCards.get("WOOD") + "}");

			String content = "{type: \"discardCards\", " +
						"playerIndex: " + playerIndex + ", " +
						"discardedCards: " + resList.toString() + "}";
			JSONObject args = makeJSON(content);*/
			JSONObject resList = new JSONObject();
			resList.put("brick", discardedCards.get(ResourceType.BRICK));
			resList.put("ore", discardedCards.get(ResourceType.ORE));
			resList.put("sheep", discardedCards.get(ResourceType.SHEEP));
			resList.put("wheat", discardedCards.get(ResourceType.WHEAT));
			resList.put("wood", discardedCards.get(ResourceType.WOOD));
			
			JSONObject args = new JSONObject();
			args.put("type", "discardCards");
			args.put("playerIndex", playerIndex);
			args.put("discardedCards", resList);
			
			return proxy.discardCards(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	//UTIL - ONLY IMPLEMENTED BY SERVER COMMUNICATOR
	public void changeLogLevel(String logLevel) throws ServerException{
		try {
			String content = "{ logLegel : \"" + logLevel + "\"}";
			JSONObject args = makeJSON(content);
			if(!proxy.changeLogLevel(args)){
				throw new ServerException("Log-level change failed");
			}
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	//JOSHUA
	//Tell server facade to put Poller into normal state behavior
	public void setPollerPlayingState() {
		poller.setPollerPlayingState();
	}

	public void updateGamesList() {
		Games.sole().getGamesFromServer();
	}

	public JSONObject getFirstModel() throws ServerException {
		poller.setPollerPlayingState();
		return getModel(null);
	}

	public void setPollerJoinGameState() {
		poller.setPollerJoinGameState();
	}
}
