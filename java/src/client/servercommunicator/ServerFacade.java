package client.servercommunicator;

import java.util.List;
import java.util.Map;

import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.VertexLocation;
import shared.models.definitions.CatanColor;
import shared.models.hand.ResourceType;

import org.json.simple.*;


public class ServerFacade {
	
	private static ServerFacade _instance;
	
	private ServerFacade(){
		proxy = new FakeProxy();
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

	

	//USER
	public int login(String username, String password) 
			throws ServerException {
		try {
			String credentials = "{ username : \"" + username 
						+ "\", password : \"" + password
						+ "\" }";
			JSONObject args = new JSONObject(credentials);
			JSONObject cookie = proxy.loginUser(args);
			return cookie.get("playerID");
		}
		catch(Exception e){
			throw new ServerException("Problem logging in", e);
		}

	}

	public int register(String username, String password) {
		try {
			String credentials = "{ username : \"" + username 
						+ "\", password : \"" + password
						+ "\" }";
			JSONObject args = new JSONObject(credentials);
			JSONObject cookie = proxy.registerUser(args);
			return cookie.get("playerID");
		}
		catch(Exception e){
			throw new ServerException("Problem registering user", e);
		}

		
	}

	//GAMES(PRE-GAME)
		//USED BY MODEL
	public Map getGames() 
			throws ServerException {
		try {
			return proxy.listGames();
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public Map createNewGame(boolean randomTiles, boolean randomNumbers, 
				boolean randomPorts, String name) 
				throws ServerException{
		try {
			String newGame = "{ randomTiles : " + randomTiles
					+ ", randomNumbers : " + randomNumbers
					+ ", randomPorts : " + randomPorts
					+ ", name : \"" + name + "\"}";
			JSONObject args = new JSONObject(newGame);
			return proxy.createGame(args);
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	public void joinGame(int gameID, CatanColor color) {
				throws ServerException{
		try {
			String joinGame = "{ id : " + gameID
					+ ", color : \"" + color.toString() + "\"}";
			JSONObject args = new JSONObject(joinGame);
			if(proxy.joinGame(args) == false){
				throw ServerException("Join game failed");
			}
		}
		catch(Exception e){
			throw new ServerException(e);
		}

		
	}
	
		//ONLY USED BY SERVER COMMUNICATOR	
	public void saveGame(int gameID, String fileName) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void loadGame(String fileName) {
		// TODO Auto-generated method stub
		
	}
	
	//GAMES(POST-JOIN)
	/**
	 * Used by the model when joining a game or by the poller to check for
	 * updates.
	 * @param version current Version number of game(optional = null)
	 * @return Updated game model OR Null if there is no update
	 */
	public Map getModel(Integer version) {
		// TODO Auto-generated method stub
		return null;
	}
	
		//THIS METHOD IS JUST USED BY THE SERVER COMMUNICATOR
	/**
	 * Just used by the server communicator
	 * @param gameID
	 * @param color
	 */
	public void reset(int gameID, CatanColor color) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @param commands list of commands in the game
	 * @return Game Model
	 */
	public Map postCommands(Map commands) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @return list of commands in the game thus far
	 */
	public Map getCommands() {
		// TODO Auto-generated method stub
		return null;
	}
	
		//USED BY MODEL AS WELL
	public void addAI(String aiType) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @return list of AI Types
	 */
	public Map listAI() {
		// TODO Auto-generated method stub
		return null;
	}

	//MOVES
	public Map sendChat(int playerIndex, String message) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map rollNumber(int playerIndex, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map robPlayer(int playerIndex, int victimIndex, 
			HexLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map finishTurn(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map buyDevCard(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map yearOfPlenty(int playerIndex, ResourceType resource1, 
			ResourceType resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map roadBuilding(int playerIndex, EdgeLocation spot1, 
			EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map soldier(int playerIndex, int victimIndex, 
			HexLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map monopoly(ResourceType resource, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map monument(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map buildRoad(int playerIndex, EdgeLocation roadLocation, 
			boolean free) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map buildSettlement(int playerIndex, VertexLocation vertLoc, 
			boolean setupMode) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map buildCity(int playerIndex, VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map offerTrade(int playerIndex, Map<ResourceType, Integer> offer,
			int receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map acceptTrade(int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map maritimeTrade(int playerIndex, int ratio,
			ResourceType inputResource, ResourceType outputResource) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map discard(int playerIndex, List<ResourceType> discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//UTIL - ONLY IMPLEMENTED BY SERVER COMMUNICATOR
	public void changeLogLevel(String logLevel) {
		// TODO Auto-generated method stub
	}
}
