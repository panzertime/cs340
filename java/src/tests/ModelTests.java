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

import shared.models.GameModel;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.VertexLocation;
import shared.models.exceptions.BadPlayerIndexException;
import shared.models.exceptions.BadTurnStatusException;
import shared.models.hand.ResourceType;

public class ModelTests {
	
	GameModel gameModel;

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
			
			gameModel = new GameModel(jsonModel);
		} catch (FileNotFoundException | ParseException | 
				BadPlayerIndexException | BadTurnStatusException e) {
			e.printStackTrace();
		}
	}
	
	private boolean correctModel() {
		return false;
	}
	
	@Test
	public void testcanSendChat() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanRollNumber() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanPlaceRobber() {
		HexLocation hexLoc;
		int playerIndex;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanFinishTurn() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanBuyDevCard() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanUseYearOfPlenty() {
		ResourceType one;
		ResourceType two;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanUseRoadBuilder() {
		EdgeLocation one;
		EdgeLocation two;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanUseSoldier() {
		HexLocation newRobberLocation;
		int playerIndex;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanUseMonopoly() {
		ResourceType type;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanUseMonument() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanBuildRoad() {
		EdgeLocation edgeLoc;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanBuildSettlement() {
		VertexLocation vertLoc;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanBuildCity() {
		VertexLocation vertLoc;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanOfferTrade() {
		Map<String, Object> resource;
		int amount;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanAcceptTrade() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanMaritimeTrade() {
		int ratio;
		ResourceType type;
		fail("Not yet implemented");
	}
	
	@Test
	public void testcanDiscardCards() {
		Map<String, Object> resourceList;
		fail("Not yet implemented");
	}
}
