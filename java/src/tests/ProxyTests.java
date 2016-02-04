package tests;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import client.servercommunicator.ServerFacade;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.VertexLocation;
import shared.models.definitions.CatanColor;
import shared.models.hand.ResourceType;

public class ProxyTests {
	
	ServerFacade serverFacade;

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testlogin() {
		String username = null;
		String password = null;
		serverFacade.login(username, password);
		fail("Not yet implemented");
	}

	@Test
	public void testregister() {
		String username = null;
		String password = null;
		serverFacade.register(username, password);
		fail("Not yet implemented");
	}

	@Test
	public void testgetGames() {
		serverFacade.getGames();
		fail("Not yet implemented");
	}

	@Test
	public void testcreateNewGame() {
		boolean randomTiles = false;
		boolean randomNumbers = false;
		boolean randomPorts = false;
		String name = null;
		serverFacade.createNewGame(randomTiles, randomNumbers, randomPorts, name);
		fail("Not yet implemented");
	}

	@Test
	public void testjoinGame() {
		CatanColor color = null;
		int gameID = 0;
		serverFacade.joinGame(gameID, color);
		fail("Not yet implemented");
	}

	@Test
	public void testsaveGame() {
		String fileName = null;
		int gameID = 0;
		serverFacade.saveGame(gameID, fileName);
		fail("Not yet implemented");
	}

	@Test
	public void testloadGame() {
		String fileName = null;
		serverFacade.loadGame(fileName);
		fail("Not yet implemented");
	}

	@Test
	public void testgetModel() {
		Integer version = null;
		serverFacade.getModel(version);
		fail("Not yet implemented");
	}

	@Test
	public void testreset() {
		int gameID = 0;
		CatanColor color = null;
		serverFacade.reset(gameID, color);
		fail("Not yet implemented");
	}

	@Test
	public void testpostCommands() {
		Map commands = null;
		serverFacade.postCommands(commands);
		fail("Not yet implemented");
	}

	@Test
	public void testgetCommands() {
		serverFacade.getCommands();
		fail("Not yet implemented");
	}

	@Test
	public void testaddAI() {
		String aiType = null;
		serverFacade.addAI(aiType);
		fail("Not yet implemented");
	}

	@Test
	public void testlistAI() {
		serverFacade.listAI();
		fail("Not yet implemented");
	}

	@Test
	public void testsendChat() {
		int playerIndex = 0;
		String message = null;
		serverFacade.sendChat(playerIndex, message);
		fail("Not yet implemented");
	}

	@Test
	public void testrollNumber() {
		int playerIndex = 0;
		int number = 0;
		serverFacade.rollNumber(playerIndex, number);
		fail("Not yet implemented");
	}

	@Test
	public void testrobPlayer() {
		int playerIndex = 0;
		int victimIndex = 0;
		HexLocation location = null;
		serverFacade.robPlayer(playerIndex, victimIndex, location);
		fail("Not yet implemented");
	}

	@Test
	public void testfinishTurn() {
		int playerIndex = 0;
		serverFacade.finishTurn(playerIndex);
		fail("Not yet implemented");
	}

	@Test
	public void testbuyDevCard() {
		int playerIndex = 0;
		serverFacade.buyDevCard(playerIndex);
		fail("Not yet implemented");
	}

	@Test
	public void testyearOfPlenty() {
		int playerIndex = 0;
		ResourceType resource1 = null;
		ResourceType resource2 = null;
		serverFacade.yearOfPlenty(playerIndex, resource1, resource2);
		fail("Not yet implemented");
	}

	@Test
	public void testroadBuilding() {
		int playerIndex = 0;
		EdgeLocation spot1 = null;
		EdgeLocation spot2 = null;
		serverFacade.roadBuilding(playerIndex, spot1, spot2);
		fail("Not yet implemented");
	}

	@Test
	public void testsoldier() {
		int playerIndex = 0;
		int victimIndex = 0;
		HexLocation location = null;
		serverFacade.soldier(playerIndex, victimIndex, location);
		fail("Not yet implemented");
	}

	@Test
	public void testmonopoly() {
		ResourceType resource = null;
		int playerIndex = 0;
		serverFacade.monopoly(resource, playerIndex);
		fail("Not yet implemented");
	}

	@Test
	public void testmonument() {
		int playerIndex = 0;
		serverFacade.monument(playerIndex);
		fail("Not yet implemented");
	}

	@Test
	public void testbuildRoad() {
		int playerIndex = 0;
		EdgeLocation roadLocation = null;
		boolean free = false;
		serverFacade.buildRoad(playerIndex, roadLocation, free);
		fail("Not yet implemented");
	}

	@Test
	public void testbuildSettlement() {
		int playerIndex = 0;
		VertexLocation vertLoc = null;
		boolean setupMode = false;
		serverFacade.buildSettlement(playerIndex, vertLoc, setupMode);
		fail("Not yet implemented");
	}

	@Test
	public void testbuildCity() {
		int playerIndex = 0;
		VertexLocation vertLoc = null;
		serverFacade.buildCity(playerIndex, vertLoc);
		fail("Not yet implemented");
	}

	@Test
	public void testofferTrade() {
		int playerIndex = 0;
		Map<ResourceType, Integer> offer = null;
		int receiver = 0;
		serverFacade.offerTrade(playerIndex, offer, receiver);
		fail("Not yet implemented");
	}

	@Test
	public void testacceptTrade() {
		int playerIndex = 0;
		boolean willAccept = false;
		serverFacade.acceptTrade(playerIndex, willAccept);
		fail("Not yet implemented");
	}

	@Test
	public void testmaritimeTrade() {
		int playerIndex = 0;
		int ratio = 0;
		ResourceType inputResource = null;
		ResourceType outputResource = null;
		serverFacade.maritimeTrade(playerIndex, ratio, inputResource, outputResource);
		fail("Not yet implemented");
	}

	@Test
	public void testdiscard() {
		int playerIndex = 0;
		List<ResourceType> discardedCards = null;
		serverFacade.discard(playerIndex, discardedCards);
		fail("Not yet implemented");
	}

	@Test
	public void testchangeLogLevel() {
		String logLevel = null;
		serverFacade.changeLogLevel(logLevel);
		fail("Not yet implemented");
	}

}
	
