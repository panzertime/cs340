package tests.proxy;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.*;

import client.servercommunicator.*;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.VertexLocation;
import shared.models.definitions.CatanColor;
import shared.models.hand.ResourceType;

import org.json.simple.JSONObject;

public class GameTests {

	private ServerFacade serverFacade;
	private int gameID;
	
	@Before
	public void setup(){
		serverFacade = ServerFacade.get_instance();
		ServerProxy sp = new ServerProxy();
		sp.setURL("http://localhost:8081");
		serverFacade.setProxy(sp);
		try {
			gameID = serverFacade.getGames().size();
			gameID -= 1;
			CatanColor color = CatanColor.BLUE;
			System.out.println("Using 'new' game has ID: " + gameID);
			serverFacade.login("Sam","sam");
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
		} catch (ServerException e) {
			e.printStackTrace();
			fail("Failed listAI Proxy test");
		}
		
	}

}

