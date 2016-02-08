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
import shared.models.board.hex.HexLocation;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.ModelAccessException;

public class CanPlaceRobberTests {
	
	ModelFacade modelFacade;

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/placerobber/" + file);
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
	
	//Good
	@Test
	public void testCanPlaceRobber6() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == true) {
				System.out.println("Passed canPlaceRobber test with good location and no one to rob");
			} else {
				fail("failed canPlaceRobber test with good location and no one to rob");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with good location and no one to rob - uninit model");
		}
	}
	
	//player being robbed has resources
	@Test
	public void testCanPlaceRobber10() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = 1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == true) {
				System.out.println("Passed canPlaceRobber test with good location and person to rob has resources");
			} else {
				fail("failed canPlaceRobber test with good location and person to rob has resources");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with good location and person to rob has resources - uninit model");
		}
	}
	
	//uninit model
	@Test
	public void testCanPlaceRobber1() {
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		ModelFacade mf = new ModelFacade();
		try {
			mf.canPlaceRobber(hexLoc, playerIndex);
			fail("failed canPlaceRobber test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("Passed canPlaceRobber test with uninit model");
		}
	}
	
	//uninit hex loc
	@Test
	public void testCanPlaceRobber2() {
		this.initModel("good.txt");
		HexLocation hexLoc = null;
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test with uninit hex loc");
			} else {
				fail("failed canPlaceRobber test with uninit hex");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with uninit hex - unit model");
		}
		
	}
	
	//unmoved robber
	@Test
	public void testCanPlaceRobber3() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(0,-2);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test with unmoved robber");
			} else {
				fail("failed canPlaceRobber test with unmoved robber");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with unmoved robber - unit model");
		}
	}

	//Robber placed on water
	@Test
	public void testCanPlaceRobber4() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(0,-3);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber on water test");
			} else {
				fail("failed canPlaceRobber on water test");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber on water test - uninit model");
		}
	}
	
	//robber way out in left field
	@Test
	public void testCanPlaceRobber5() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(56,-30);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test with bizzare location");
			} else {
				fail("failed canPlaceRobber test with bizzare location");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with bizzare location - uninit model");
		}
	}
	
	//not your turn
	@Test
	public void testCanPlaceRobber7() {
		this.initModel("noTurn.txt");
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test when it is not your turn");
			} else {
				fail("failed canPlaceRobber test when it is not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test when it is not your turn - uninit model");
		}
	}
	
	//not playing?
	@Test
	public void testCanPlaceRobber8() {
		this.initModel("noPlaying.txt");
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test when status is not playing");
			} else {
				fail("failed canPlaceRobber test when status is not playing");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test when status is not playing - uninit model");
		}
	}
	
	//player being robbed has not resources
	@Test
	public void testCanPlaceRobber9() {
		this.initModel("noRes.txt");
		HexLocation hexLoc = new HexLocation(0,0);
		int playerIndex = 2;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test with good location but person to rob has no resources");
			} else {
				fail("failed canPlaceRobber test with good location but person to rob has no resources");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with good location but person to rob has no resources - uninit model");
		}
	}
}