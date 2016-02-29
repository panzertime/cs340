package model.can.placerobber;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import client.main.ClientPlayer;
import client.modelfacade.CanModelFacade;
import client.modelfacade.ModelFacade;
import client.modelfacade.testing.TestingModelFacade;
import shared.model.board.hex.HexLocation;
import shared.model.exceptions.BadJSONException;

public class CanPlaceRobberTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/placerobber/" + file);
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
			
			JSONObject jsonModel = (JSONObject) parser.parse(x);
			ModelFacade.setModel(jsonModel);
			
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			e.printStackTrace();
		}
	}
	
	//Good
	/*@Test
	public void testCanPlaceRobber6() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == true) {
				System.out.println("passed canPlaceRobber test with good location and no one to rob");
			} else {
				fail("failed canPlaceRobber test with good location and no one to rob");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with good location and no one to rob - uninit model");
		}
	}*/
	
	//player being robbed has resources
	@Test
	public void testCanPlaceRobber10() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = 1;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == true) {
				System.out.println("passed canPlaceRobber test with good location and person to rob has resources");
			} else {
				fail("failed canPlaceRobber test with good location and person to rob has resources");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with good location and person to rob has resources - uninit model");
		}*/
	}
	
	//uninit model
	@Test
	public void testCanPlaceRobber1() {
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		/*try {
			//CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex);
			fail("failed canPlaceRobber test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed canPlaceRobber test with uninit model");
		}*/
	}
	
	//uninit hex loc
	@Test
	public void testCanPlaceRobber2() {
		this.initModel("good.txt");
		HexLocation hexLoc = null;
		int playerIndex = -1;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("passed canPlaceRobber test with uninit hex loc");
			} else {
				fail("failed canPlaceRobber test with uninit hex");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with uninit hex - unit model");
		}*/
		
	}
	
	//unmoved robber
	@Test
	public void testCanPlaceRobber3() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(0,-2);
		int playerIndex = -1;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("passed canPlaceRobber test with unmoved robber");
			} else {
				fail("failed canPlaceRobber test with unmoved robber");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with unmoved robber - unit model");
		}*/
	}

	//Robber placed on water
	@Test
	public void testCanPlaceRobber4() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(0,-3);
		int playerIndex = -1;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("passed canPlaceRobber on water test");
			} else {
				fail("failed canPlaceRobber on water test");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber on water test - uninit model");
		}*/
	}
	
	//robber way out in left field
	@Test
	public void testCanPlaceRobber5() {
		this.initModel("good.txt");
		HexLocation hexLoc = new HexLocation(56,-30);
		int playerIndex = -1;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("passed canPlaceRobber test with bizzare location");
			} else {
				fail("failed canPlaceRobber test with bizzare location");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with bizzare location - uninit model");
		}*/
	}
	
	//not your turn
	@Test
	public void testCanPlaceRobber7() {
		this.initModel("noTurn.txt");
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("passed canPlaceRobber test when it is not your turn");
			} else {
				fail("failed canPlaceRobber test when it is not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test when it is not your turn - uninit model");
		}*/
	}
	
	//not playing?
	@Test
	public void testCanPlaceRobber8() {
		this.initModel("noPlaying.txt");
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("passed canPlaceRobber test when status is not playing");
			} else {
				fail("failed canPlaceRobber test when status is not playing");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test when status is not playing - uninit model");
		}*/
	}
	
	//player being robbed has not resources
	@Test
	public void testCanPlaceRobber9() {
		this.initModel("noRes.txt");
		HexLocation hexLoc = new HexLocation(0,0);
		int playerIndex = 2;
		/*try {
			if(CanModelFacade.sole().canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("passed canPlaceRobber test with good location but person to rob has no resources");
			} else {
				fail("failed canPlaceRobber test with good location but person to rob has no resources");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with good location but person to rob has no resources - uninit model");
		}*/
	}
}