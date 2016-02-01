package client.servercommunicator;

import shared.definitions.CatanColor;
import shared.models.Model;
import shared.models.NewGame;
import shared.models.board.locations.EdgeLocation;
import shared.models.board.locations.HexLocation;
import shared.models.hand.ResourceType;

public class ServerFacade {
	
	private static ServerFacade _instance;
	
	private ServerFacade() {
		// TODO Auto-generated method stub
	}
	
	public static ServerFacade get_Instance() {
		if(_instance == null) {
			_instance = new ServerFacade();
		}
		
		return _instance;
	}

	public Model sendMessage(int playerIndex, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	public Model buyDevCard(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Model playMonopolyCard(ResourceType resource, int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Model playMonumentCard(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Model playRoadBuildCard(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Model yearOfPlentyCard(int playerIndex, ResourceType resource1, 
			ResourceType resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Model discard(ResourceType[] discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

	public void login(String username, String password) {
		// TODO Auto-generated method stub
	}

	public void register(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	public void getGames() {
		// TODO Auto-generated method stub
		
	}

	public NewGame createNewGame(boolean randomTiles, boolean randomNumbers, 
			boolean randomPorts, String name) {
		return null;
		// TODO Auto-generated method stub
		
	}

	public void joinGame(int gameID, CatanColor color) {
		// TODO Auto-generated method stub
		
	}

	public void getModel(int versionNumber) {
		// TODO Auto-generated method stub
	}

	public void addAI(String aiType) {
		// TODO Auto-generated method stub
		
	}

	public void getAI() {
		// TODO Auto-generated method stub
		
	}

	public Model robPlayer(int playerIndex, int victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	public Model endTurn(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Model playSoldierCard(int playerIndex, int victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		return null;
	}
}
