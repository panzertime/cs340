package proxy;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import client.serverfacade.ServerException;
import client.serverfacade.ServerFacade;
import client.serverfacade.ServerProxy;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;
import shared.model.definitions.CatanColor;
import shared.model.hand.ResourceType;

public class ProxyTest {
	
	ServerFacade serverFacade;
	
	@Before
	public void setup(){
		serverFacade = ServerFacade.get_instance();
		ServerProxy sp = new ServerProxy();
		sp.setURL("http://localhost:8081");
		serverFacade.setProxy(sp);
	}

	private int getSeed(){
		return (int) (Math.random()*10000000); 
	}


	//Good tests
	@Test
	public void testlogin200() {
		String username = "Sam";
		String password = "sam";
		try {
			if(serverFacade.login(username, password) > -1){
				System.out.println("Passed login test.");
			}
			
		} catch (ServerException e) {
			System.out.println("Failed login Proxy test: " + e.toString());
			e.printStackTrace();
			fail("Login failure.");
		}
		
	}

	@Test
	public void testregister200() {
		String username = "Newguy" + getSeed();
		System.out.println("Generated random test username: " + username);
		String password = "Newguypass";
		try {
			if(serverFacade.register(username, password) > -1){
				System.out.println("Passed register test.");
			}
		} catch (ServerException e) {
			System.out.println("Failed register Proxy test");
			e.printStackTrace();
			fail("Registration failure.  Note: this test may only work once per server startup.");

		}
		
	}

	@Test
	public void testgetGames200() {
		try {
			serverFacade.getGames();
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
			serverFacade.createNewGame(randomTiles, randomNumbers, randomPorts, name);
		} catch (ServerException e) {
			e.printStackTrace();
			fail("Failed createNewGame Proxy test");
		}
		
	}

	 @Test
	public void testjoinGame200() {
		CatanColor color = CatanColor.PUCE;
		int gameID = 1;
		try {
			serverFacade.joinGame(gameID, color);
		} catch (ServerException e) {
			fail("Failed joinGame Proxy test");
		}
		
	}

	// @Test
	public void testsaveGame200() {
		String fileName = null;
		int gameID = 0;
		try {
			serverFacade.saveGame(gameID, fileName);
		} catch (ServerException e) {
			fail("Failed saveGame Proxy test");
		}
		
	}

	// @Test
	public void testloadGame200() {
		String fileName = null;
		try {
			serverFacade.loadGame(fileName);
		} catch (ServerException e) {
			fail("Failed loadGame Proxy test");
		}
		
	}

	// @Test
	public void testgetModel200() {
		Integer version = null;
		try {
			serverFacade.getModel(version);
		} catch (ServerException e) {
			fail("Failed getModel Proxy test");
		}
		
	}

	// @Test
	public void testreset200() {
		try {
			serverFacade.reset();
		} catch (ServerException e) {
			fail("Failed reset Proxy test");
		}
		
	}

	// @Test
	public void testpostCommands200() {
		Map commands = null;
		try {
			serverFacade.postCommands(commands);
		} catch (ServerException e) {
			fail("Failed postCommandss Proxy test");
		}
		
	}

	// @Test
	public void testgetCommands200() {
		try {
			serverFacade.getCommands();
		} catch (ServerException e) {
			fail("Failed getCommands Proxy test");
		}
		
	}

	// @Test
	public void testaddAI200() {
		String aiType = null;
		try {
			serverFacade.addAI(aiType);
		} catch (ServerException e) {
			fail("Failed addAI Proxy test");
		}
		
	}

	// @Test
	public void testlistAI200() {
		try {
			serverFacade.listAI();
		} catch (ServerException e) {
			fail("Failed listAI Proxy test");
		}
		
	}

	// @Test
	public void testsendChat200() {
		int playerIndex = 0;
		String message = null;
		try {
			serverFacade.sendChat(playerIndex, message);
		} catch (ServerException e) {
			fail("Failed sendChat Proxy test");
		}
		
	}

	// @Test
	public void testrollNumber200() {
		int playerIndex = 0;
		int number = 0;
		try {
			serverFacade.rollNumber(playerIndex, number);
		} catch (ServerException e) {
			fail("Failed rollNumber Proxy test");
		}
		
	}

	// @Test
	public void testrobPlayer200() {
		int playerIndex = 0;
		int victimIndex = 0;
		HexLocation location = null;
		try {
			serverFacade.robPlayer(playerIndex, victimIndex, location);
		} catch (ServerException e) {
			fail("Failed robPlayer Proxy test");
		}
		
	}

	// @Test
	public void testfinishTurn200() {
		int playerIndex = 0;
		try {
			serverFacade.finishTurn(playerIndex);
		} catch (ServerException e) {
			fail("Failed finishTurn Proxy test");
		}
		
	}

	// @Test
	public void testbuyDevCard200() {
		int playerIndex = 0;
		try {
			serverFacade.buyDevCard(playerIndex);
		} catch (ServerException e) {
			fail("Failed buyDevCard Proxy test");
		}
		
	}

	// @Test
	public void testyearOfPlenty200() {
		int playerIndex = 0;
		ResourceType resource1 = null;
		ResourceType resource2 = null;
		try {
			serverFacade.yearOfPlenty(playerIndex, resource1, resource2);
		} catch (ServerException e) {
			fail("Failed yearOfPlenty Proxy test");
		}
		
	}

	// @Test
	public void testroadBuilding200() {
		int playerIndex = 0;
		EdgeLocation spot1 = null;
		EdgeLocation spot2 = null;
		try {
			serverFacade.roadBuilding(playerIndex, spot1, spot2);
		} catch (ServerException e) {
			fail("Failed roadBuilding Proxy test");
		}
		
	}

	// @Test
	public void testsoldier200() {
		int playerIndex = 0;
		int victimIndex = 0;
		HexLocation location = null;
		try {
			serverFacade.soldier(playerIndex, victimIndex, location);
		} catch (ServerException e) {
			fail("Failed soldier Proxy test");
		}
		
	}

	// @Test
	public void testmonopoly200() {
		ResourceType resource = null;
		int playerIndex = 0;
		try {
			serverFacade.monopoly(resource, playerIndex);
		} catch (ServerException e) {
			fail("Failed monopoly Proxy test");
		}
		
	}

	// @Test
	public void testmonument200() {
		int playerIndex = 0;
		try {
			serverFacade.monument(playerIndex);
		} catch (ServerException e) {
			fail("Failed monument Proxy test");
		}
		
	}

	// @Test
	public void testbuildRoad200() {
		int playerIndex = 0;
		EdgeLocation roadLocation = null;
		boolean free = false;
		try {
			serverFacade.buildRoad(playerIndex, roadLocation, free);
		} catch (ServerException e) {
			fail("Failed Proxy test");
		}
		
	}

	// @Test
	public void testbuildSettlement200() {
		int playerIndex = 0;
		VertexLocation vertLoc = null;
		boolean setupMode = false;
		try {
			serverFacade.buildSettlement(playerIndex, vertLoc, setupMode);
		} catch (ServerException e) {
			fail("Failed buildSettlement Proxy test");
		}
		
	}

	// @Test
	public void testbuildCity200() {
		int playerIndex = 0;
		VertexLocation vertLoc = null;
		try {
			serverFacade.buildCity(playerIndex, vertLoc);
		} catch (ServerException e) {
			fail("Failed buildCity Proxy test");
		}
		
	}

	// @Test
	public void testofferTrade200() {
		int playerIndex = 0;
		Map<ResourceType, Integer> offer = null;
		int receiver = 0;
		try {
			serverFacade.offerTrade(playerIndex, offer, receiver);
		} catch (ServerException e) {
			fail("Failed offerTrade Proxy test");
		}
		
	}

	// @Test
	public void testacceptTrade200() {
		int playerIndex = 0;
		boolean willAccept = false;
		try {
			serverFacade.acceptTrade(playerIndex, willAccept);
		} catch (ServerException e) {
			fail("Failed acceptTrade Proxy test");
		}
		
	}

	// @Test
	public void testmaritimeTrade200() {
		int playerIndex = 0;
		int ratio = 0;
		ResourceType inputResource = null;
		ResourceType outputResource = null;
		try {
			serverFacade.maritimeTrade(playerIndex, ratio, inputResource, outputResource);
		} catch (ServerException e) {
			fail("Failed maritimeTrade Proxy test");
		}
		
	}

	// @Test
	public void testdiscard200() {
		int playerIndex = 0;
		Map<ResourceType, Integer> discardedCards = null;
		try {
			serverFacade.discard(playerIndex, discardedCards);
		} catch (ServerException e) {
			fail("Failed discard Proxy test");
		}

	}

	// @Test
	public void testchangeLogLevel200() {
		String logLevel = null;
		try {
			serverFacade.changeLogLevel(logLevel);
		} catch (ServerException e) {
			fail("Failed change log level Proxy test");
		}
	}
}
