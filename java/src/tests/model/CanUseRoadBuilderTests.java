package tests.model;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import shared.models.ModelFacade;
import shared.models.board.edge.EdgeDirection;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.ModelAccessException;
import shared.models.hand.ResourceType;

public class CanUseRoadBuilderTests {
	
	ModelFacade modelFacade;

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/canuseroadbuildcard/" + file);
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
			
			modelFacade = new ModelFacade((JSONObject) jsonModel, 0);
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * initModel()
	 * firstLocation = (2,0,NW)
	 * secondLocation = (0,1,SE)
	 * unused roads >= 2
	 * turnIndex = 0
	 * status = playing
	 * you have a road builder card
	 * you have not yet used a non monument card
	 */
	@Test
	public void testCanUseRoadBuilder1() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.NorthWest);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		initModel("good.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == true) {
				System.out.println("passed testCanUseRoadBuilder test when two roads are separate and valid");
			} else {
				fail("failed testCanUseRoadBuilder test when two roads are separate and valid");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when two roads are separate and valid - could not access model");
		}
	}
	
	/*
	 * initModel()
	 * firstLocation = (2,0,N)
	 * secondLocation = (2,0,NW)
	 * unused roads >= 2
	 */
	@Test
	public void testCanUseRoadBuilder2() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.NorthWest);
		EdgeLocation two = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		initModel("good.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == true) {
				System.out.println("passed testCanUseRoadBuilder test when two roads are connected valid");
			} else {
				fail("failed testCanUseRoadBuilder test when two roads are connected and valid");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when two roads are connected and valid - could not access model");
		}
	}
	
	//First road would only be valid for pete, (0,0,SE)
	@Test
	public void testCanUseRoadBuilder3() {
		EdgeLocation one = new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthEast);
		EdgeLocation two = new EdgeLocation(new HexLocation(2,0), EdgeDirection.NorthWest);
		initModel("good.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when placing a road valid only for another player (Pete)");
			} else {
				fail("failed testCanUseRoadBuilder test when placing a road valid only for another player (Pete)");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when placing a road valid only for another player (Pete) - could not access model");
		}
	}
	
	//second road is first road
	@Test
	public void testCanUseRoadBuilder4() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		EdgeLocation two = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		initModel("good.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when two roads are in the same spot");
			} else {
				fail("failed testCanUseRoadBuilder test when two roads are in the same spot");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when two roads are in the same spot - could not access model");
		}
	}
	
	//First road is on pete's road, (0,0,S)
	@Test
	public void testCanUseRoadBuilder5() {
		EdgeLocation one = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		initModel("good.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when first road is on another's road");
			} else {
				fail("failed testCanUseRoadBuilder test when first road is on another's road");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when first road is on another's road - could not access model");
		}
	}
	
	//first road is on water
	@Test
	public void testCanUseRoadBuilder6() {
		EdgeLocation one = new EdgeLocation(new HexLocation(3,0), EdgeDirection.South);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		initModel("good.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when first road is on water");
			} else {
				fail("failed testCanUseRoadBuilder test when first road is on water");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when first road is on water - could not access model");
		}
	}
	
	//second road is on water
	@Test
	public void testCanUseRoadBuilder7() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		EdgeLocation two = new EdgeLocation(new HexLocation(3,0), EdgeDirection.South);
		initModel("good.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when second road is on water");
			} else {
				fail("failed testCanUseRoadBuilder test when second road is on water");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when second road is on water - could not access model");
		}
	}
	
	//user doesn't have two unused roads
	@Test
	public void testCanUseRoadBuilder8() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		initModel("noRoads.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when user doesn't have enough roads");
			} else {
				fail("failed testCanUseRoadBuilder test when user doesn't have enough roads");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when user doesn't have enough roads - could not access model");
		}
	}
	
	//uninit model
	@Test
	public void testCanUseRoadBuilder9() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		ModelFacade mf = new ModelFacade();
		try {
			mf.canUseRoadBuilder(one, two);
			fail("failed testCanUseRoadBuilder test when there is currently no game");
		} catch (NullPointerException e) {
			System.out.println("passed testCanUseRoadBuilder test when there is currently no game");
		}
	}
	
	//not your turn
	@Test
	public void testCanUseRoadBuilder10() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		initModel("noTurn.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when not your turn");
			} else {
				fail("failed testCanUseRoadBuilder test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when not your turn - could not access model");
		}
	}
	
	//status is not playing
	@Test
	public void testCanUseRoadBuilder11() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		initModel("noPlay.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when status is not playing");
			} else {
				fail("failed testCanUseRoadBuilder test when status is not playing");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when status is not playing - could not access model");
		}
	}
	
	//you don't have this card
	@Test
	public void testCanUseRoadBuilder12() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		initModel("noCard.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when user does not have this card");
			} else {
				fail("failed testCanUseRoadBuilder test when user does not have this card");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when user does not have this card - could not access model");
		}
	}
	
	//you have already played a dev card
	@Test
	public void testCanUseRoadBuilder13() {
		EdgeLocation one = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		EdgeLocation two = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		initModel("alreadyPlayed.txt");
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("passed testCanUseRoadBuilder test when already played a dev card");
			} else {
				fail("failed testCanUseRoadBuilder test when already played a dev card");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseRoadBuilder test when already played a dev card - could not access model");
		}
	}
}
