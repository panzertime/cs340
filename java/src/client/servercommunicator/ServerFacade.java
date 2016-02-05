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

	public void joinGame(int gameID, CatanColor color) 
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
	public void saveGame(int gameID, String fileName) 
				throws ServerException{
		try {
			String saveGame = "{ id : " + gameID
				+ ", name : \"" + fileName + "\"}";
			JSONObject args = new JSONObject(saveGame);
			if(proxy.saveGame(args) == false){
				throw ServerException("Save game failed");
			}
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	
	public void loadGame(String fileName) 
				throws ServerException {
		try {
			String loadGame = "{ id : " + gameID
				+ ", name : \"" + fileName + "\"}";
			JSONObject args = new JSONObject(loadGame);
			if(proxy.loadGame(args) == false){
				throw ServerException("Load game failed");
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
			return proxy.executeCommands(commands);
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
			JSONObject args = new JSONObject("{ AIType : \"" + aiType + "\"}");
			if(!proxy.addAI(args)){
				throw new ServerException("Problem adding AI player");
			}
		}
		catch(Exception e){
			throw new ServerException(e);
		}
	}

	/**
	 * 
	 * @return list of AI Types
	 */
	public Map listAI() {
		throws ServerException {
		try {
			return proxy.listAI();
		}
		catch(Exception e){
			throw new ServerException(e);
		}
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
