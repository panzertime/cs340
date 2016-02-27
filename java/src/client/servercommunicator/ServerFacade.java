package client.servercommunicator;

import java.util.List;
import java.util.Map;

import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;
import shared.model.definitions.CatanColor;
import shared.model.hand.ResourceType;

import org.json.simple.*;
import org.json.simple.parser.*;

public class ServerFacade {
	
	private static ServerFacade _instance;

	private int player_id;
	
	private ServerFacade(){
		proxy = new FakeProxy();
		player_id = -1;
	}

	private IServerProxy proxy;
	
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
			return player_id;

		}
		catch(Exception e){
			throw new ServerException("Problem registering user", e);
		}
	}

	//GAMES(PRE-GAME)
		//USED BY MODEL
	public List getGames() 
			throws ServerException {
		try {
			return proxy.listGames();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ServerException(e);
		}
	}

	public Map createNewGame(boolean randomTiles, boolean randomNumbers, 
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
	public Map getModel(Integer version) 
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
	public Map reset()
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
	public Map postCommands(Map commands)
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
	public Map getCommands()
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
	public List listAI() 
		throws ServerException {
		try {
			return proxy.listAI();
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	//MOVES
	public Map sendChat(int playerIndex, String message)
			throws ServerException {
		try {
			//String content = "{type: \"sendChat\", " +
			//			"playerIndex: " + playerIndex + ", " +
			//			"content: \"" + message + "\"}";
			//JSONObject args = makeJSON(content);
			//return proxy.sendChat(args);
			JSONObject jsonChat = new JSONObject();
			jsonChat.put("type", "sendChat");
			jsonChat.put("playerIndex", playerIndex);
			jsonChat.put("content", message);
			return proxy.sendChat(jsonChat);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	public Map rollNumber(int playerIndex, int number) 
			throws ServerException {
		try {
			String content = "{type: \"rollNumber\", " +
						"playerIndex: " + playerIndex + ", " +
						"number: " + number + "}";
			JSONObject args = makeJSON(content);
			return proxy.rollNumber(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map robPlayer(int playerIndex, int victimIndex, 
			HexLocation location) 
			throws ServerException {
		try {
			String content = "{type: \"robPlayer\", " +
						"playerIndex: " + playerIndex + ", " +
						"victimIndex: " + victimIndex + ", " +
						"location: " + location + "}";
			JSONObject args = makeJSON(content);
			return proxy.robPlayer(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map finishTurn(int playerIndex) throws ServerException {
		try {
			String content = "{type: \"finishTurn\", " +
						"playerIndex: " + playerIndex +  "}";
			JSONObject args = makeJSON(content);
			return proxy.finishTurn(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map buyDevCard(int playerIndex) throws ServerException {
		try {
			String content = "{type: \"buyDevCard\", " +
						"playerIndex: " + playerIndex + "}";
			JSONObject args = makeJSON(content);
			return proxy.buyDevCard(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map yearOfPlenty(int playerIndex, ResourceType resource1, 
			ResourceType resource2) throws ServerException {
		try {
			String content = "{type: \"Year_of_Plenty\", " +
						"playerIndex: " + playerIndex + ", " +
						"resource1: " + resource1.toString() + ", " +
						"resource2: " + resource2.toString() + "}";
			JSONObject args = makeJSON(content);
			return proxy.yearOfPlenty(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map roadBuilding(int playerIndex, EdgeLocation spot1, 
			EdgeLocation spot2) throws ServerException {
		try {
			String content = "{type: \"Road_Building\", " +
						"playerIndex: " + playerIndex + ", " +
						"spot1: " + spot1.toString() + ", " +
						"spot2: " + spot2.toString() + "}";
			JSONObject args = makeJSON(content);
			return proxy.roadBuilding(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}


	public Map soldier(int playerIndex, int victimIndex, 
			HexLocation location) throws ServerException {
		try {
			String content = "{type: \"Soldier\", " +
						"playerIndex: " + playerIndex + ", " +
						"victimIndex: " + victimIndex + ", " +
						"location: " + location.toString() + "}";
			JSONObject args = makeJSON(content);
			return proxy.soldier(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map monopoly(ResourceType resource, int playerIndex) 
			throws ServerException {
		try {
			String content = "{type: \"Monopoly\", " +
						"resource: \"" + resource.toString().toLowerCase() + "\", " +
						"playerIndex: " + playerIndex + "}";
			JSONObject args = makeJSON(content);
			return proxy.monopoly(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map monument(int playerIndex) throws ServerException {
		try {
			String content = "{type: \"Monument\", " +
						"playerIndex: " + playerIndex + "}";
			JSONObject args = makeJSON(content);
			return proxy.monument(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map buildRoad(int playerIndex, EdgeLocation roadLocation, 
			boolean free) throws ServerException {
		try {
			String content = "{type: \"buildRoad\", " +
						"playerIndex: " + playerIndex + ", " +
						"roadLocation: \"" + roadLocation.toString() + "\", " +
						"free: " + free + "}";
			JSONObject args = makeJSON(content);
			return proxy.buildRoad(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map buildSettlement(int playerIndex, VertexLocation vertLoc, 
			boolean setupMode) throws ServerException {
		try {
			String content = "{type: \"buildSettlement\", " +
						"playerIndex: " + playerIndex + ", " +
						"vertexLocation: " + vertLoc.toString() + ", " +
						"free: " + setupMode + "}";
			JSONObject args = makeJSON(content);
			return proxy.buildSettlement(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map buildCity(int playerIndex, VertexLocation vertLoc) 
			throws ServerException {
		try {
			String content = "{type: \"buildCity\", " +
						"playerIndex: " + playerIndex + ", " +
						"vertexLocation: " + vertLoc.toString() +  "}";
			JSONObject args = makeJSON(content);
			return proxy.buildCity(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map offerTrade(int playerIndex, Map<ResourceType, Integer> offer,
			int receiver) throws ServerException {
		try {
			String resList = "{brick: " + offer.get(ResourceType.BRICK) +
						", ore: " + offer.get(ResourceType.ORE) +
						", sheep: " + offer.get(ResourceType.SHEEP) +
						", wheat: " + offer.get(ResourceType.WHEAT) +
						", wood: " + offer.get(ResourceType.WOOD) + "}";

			String content = "{type: \"offerTrade\", " +
						"playerIndex: " + playerIndex + ", " +
						"offer: " + resList + ", " +
						"receiver : " + receiver + "}";
			JSONObject args = makeJSON(content);
			return proxy.offerTrade(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map acceptTrade(int playerIndex, boolean willAccept) 
			throws ServerException {
		try {
			String content = "{type: \"acceptTrade\", " +
						"playerIndex: " + playerIndex + ", " +
						"willAccept: " + willAccept +  "}";
			JSONObject args = makeJSON(content);
			return proxy.acceptTrade(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map maritimeTrade(int playerIndex, int ratio,
			ResourceType inputResource, ResourceType outputResource) 
			throws ServerException {
		try {
			String content = "{type: \"maritimeTrade\", " +
						"playerIndex: " + playerIndex + ", " +
						"ratio: " + ratio + ", " +
						"inputResource: " + inputResource.toString().toLowerCase() + ", " +
						"outputResource: " + outputResource.toString().toLowerCase() + "}";
			JSONObject args = makeJSON(content);
			return proxy.maritimeTrade(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map discard(int playerIndex, Map<ResourceType, Integer> discardedCards)
			throws ServerException {
		try {
			StringBuilder resList = new StringBuilder();
			resList.append("{");
			resList.append("\"brick\" : " + discardedCards.get("BRICK") + ", ");
			resList.append("\"ore\" : " + discardedCards.get("ORE") + ", ");
			resList.append("\"sheep\" : " + discardedCards.get("SHEEP") + ", ");
			resList.append("\"wheat\" : " + discardedCards.get("WHEAT") + ", ");
			resList.append("\"wood\" : " + discardedCards.get("WOOD") + "}");

			String content = "{type: \"discardCards\", " +
						"playerIndex: " + playerIndex + ", " +
						"discardedCards: " + resList.toString() + "}";
			JSONObject args = makeJSON(content);
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
}
