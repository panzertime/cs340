package tests;
import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import shared.models.ModelFacade;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.VertexLocation;
import shared.models.exceptions.ModelAccessException;
import shared.models.hand.ResourceType;

public class ModelTests {
	
	ModelFacade modelFacade;

	@Before
	private void initModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/jsonMap.txt");
		FileInputStream fis;
		try {
			fis = new FileInputStream(jsonFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Scanner scanner = new Scanner(bis);
			String x = "";
			while(scanner.hasNextLine()) {
				x += scanner.nextLine();
				x += "\n";
			}
			scanner.close();
			
			Map jsonModel = (Map) parser.parse(x);
			
			modelFacade = new ModelFacade(jsonModel);
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private boolean correctModel() {
		//TODO: Individual element check
		return false;
	}
	
	@Test
	public void testCanSendChat() {
		try {
			modelFacade.canSendChat();
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanRollNumber() {
		try {
			modelFacade.canRollNumber();
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanPlaceRobber() {
		HexLocation hexLoc = null;
		int playerIndex = 0;
		try {
			modelFacade.canPlaceRobber(hexLoc, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanFinishTurn() {
		try {
			modelFacade.canFinishTurn();
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuyDevCard() {
		try {
			modelFacade.canBuyDevCard();
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseYearOfPlenty() {
		ResourceType one = null;
		ResourceType two = null;
		try {
			modelFacade.canUseYearOfPlenty(one, two);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseRoadBuilder() {
		EdgeLocation one = null;
		EdgeLocation two = null;
		try {
			modelFacade.canUseRoadBuilder(one, two);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseSoldier() {
		HexLocation newRobberLocation = null;
		int playerIndex = 0;
		try {
			modelFacade.canUseSoldier(newRobberLocation, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseMonopoly() {
		ResourceType type = null;
		try {
			modelFacade.canUseMonopoly(type);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseMonument() {
		try {
			modelFacade.canUseMonument();
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildRoad() {
		EdgeLocation edgeLoc = null;
		try {
			modelFacade.canBuildRoad(edgeLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildSettlement() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildSettlement(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildCity() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildCity(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanOfferTrade() {
		Map<String, Object> resource = null;
		int amount = 0;
		try {
			modelFacade.canOfferTrade(resource, amount);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanAcceptTrade() {
		try {
			modelFacade.canAcceptTrade();
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanMaritimeTrade() {
		int ratio = 0;
		ResourceType type = null;
		try {
			modelFacade.canMaritimeTrade(ratio, type);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanDiscardCards() {
		Map<String, Object> resourceList = null;
		try {
			modelFacade.canDiscardCards(resourceList);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
}
