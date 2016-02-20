package proxy;
import static org.junit.Assert.fail;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import client.servercommunicator.ServerProxy;
import shared.model.definitions.CatanColor;

public class GameTest {

	private ServerFacade serverFacade;
	private int gameID;
	
	@Before
	public void setup(){
		serverFacade = ServerFacade.get_instance();
		ServerProxy sp = new ServerProxy();
		sp.setURL("http://localhost:8081");
		serverFacade.setProxy(sp);
		try {
			serverFacade.login("Sam","sam");
			String name = "Newgame " + getSeed();
			JSONObject newGame = (JSONObject) 
					serverFacade.createNewGame(false, false, false, name);
			System.out.println("Passed create new game test");
			gameID = ((Long) newGame.get("id")).intValue();
			CatanColor color = CatanColor.BLUE;
			System.out.println("Using 'new' game has ID: " + gameID);

			serverFacade.joinGame(gameID, color);
		} catch (ServerException e) {
			System.out.println("Failed initializing GameTests");
			e.printStackTrace();
		}
	}

	private int getSeed(){
		return (int) (Math.random()*10000000); 
	}

	@Test
	public void testgetModel200() {
		int version = 0;
		try {
			serverFacade.getModel(version);
		} catch (ServerException e) {
			e.printStackTrace();
			fail("Failed getModel Proxy test");
		}
		
	}

	@Test
	public void testaddAI200() {
		String aiType = "LARGEST_ARMY";
		try {
			serverFacade.addAI(aiType);
		} catch (ServerException e) {
			e.printStackTrace();
			fail("Failed addAI Proxy test");
		}
		
	}

	@Test
	public void testlistAI200() {
		try {
			serverFacade.listAI();
			System.out.println("Passed list AI types test");
		} catch (ServerException e) {
			e.printStackTrace();
			fail("Failed listAI Proxy test");
		}
		
	}

//	@Test
	public void doNothingTest() {
		return;
	}

}

