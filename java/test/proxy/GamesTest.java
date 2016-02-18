package proxy;
import static org.junit.Assert.fail;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import client.serverfacade.ServerException;
import client.serverfacade.ServerFacade;
import client.serverfacade.ServerProxy;
import shared.model.definitions.CatanColor;

public class GamesTest {
	
	private ServerFacade serverFacade;
	private int gameID;
	private void setID(int id){
		gameID = id;
	}
	
	@Before
	public void setup(){
		try {
			serverFacade = ServerFacade.get_instance();
			ServerProxy sp = new ServerProxy();
			sp.setURL("http://localhost:8081");
			serverFacade.setProxy(sp);
			serverFacade.login("Sam","sam");
		}
		catch (Exception e) {
			fail("Initialization failure: GamesTests");
		}
	}

	private int getSeed(){
		return (int) (Math.random()*10000000); 
	}



	@Test
	public void testgetGames200() {
		try {
			serverFacade.getGames();
			System.out.println("Passed get game list test");
		} catch (ServerException e) {
			e.printStackTrace();
			fail("Failed getGames Proxy test");
		}
		
	}


	@Test
	public void testcreateNewGame200() {
		boolean randomTiles = false;
		boolean randomNumbers = false;
		boolean randomPorts = false;
		String name = "Newgame " + getSeed();
		try {
			JSONObject newGame = (JSONObject) 
					serverFacade.createNewGame(randomTiles, randomNumbers, randomPorts, name);
			System.out.println("Passed create new game test");
			gameID = ((Long) newGame.get("id")).intValue();
			setID(gameID);
			System.out.println("New game has ID: " + gameID);
		} catch (ServerException e) {
			e.printStackTrace();
			fail("Failed createNewGame Proxy test");
		}
		
	}

	 @Test
	public void testjoinGame200() {
		try {
		gameID = serverFacade.getGames().size();
		gameID -= 2;
		CatanColor color = CatanColor.PUCE;
		System.out.println("Using 'new' game has ID: " + gameID);
		serverFacade.joinGame(gameID, color);
		System.out.println("Passed join game test");
		} catch (ServerException e) {
			e.printStackTrace();
			fail("Failed joinGame Proxy test");
		}
		
	}

}

