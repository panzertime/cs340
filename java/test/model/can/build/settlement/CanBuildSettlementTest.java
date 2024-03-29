package model.can.build.settlement;

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
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.BadJSONException;

public class CanBuildSettlementTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}


	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/build/settlement/" + file);
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
	
	/*
	 * initModel()
	 * turnIndex = 0
	 * turn status = Playing
	 * Road added to (2,0,NW) in JSON
	 * User has 1 wood, 1 brick, 1 wheat, 1 sheep and 1 settlement
	 * Try to add (2,0, NW) here
	 */
	@Test
	public void testCanBuildSettlement1() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(2,0), VertexDirection.NorthWest);
		initModel("good.txt");
		try {
			if(CanModelFacade.sole().canBuildSettlement(vertLoc) == true) {
				System.out.println("passed testCanBuildSettlement test when in playing mode and test meets parameters");
			} else {
				fail("failed testCanBuildSettlement test when in playing mode and test meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildSettlement test when in playing mode and test meets parameters - model not created");
		}
	}
	
	/*
	 * initModel()
	 * turnIndex = 0
	 * turn status = SecondRound
	 * Road taken away from (0,1,SE) in JSON
	 * User has 0 res,but 4 settlements
	 * Try to add (0,1,SE) here
	 */
	/*@Test
	public void testCanBuildSettlement2() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.SouthEast);
		initModel("goodSetup.txt");
		try {
			if(CanModelFacade.sole().canSetupSettlement(vertLoc) == true) {
				System.out.println("passed testCanBuildSettlement test when in setup mode and test meets parameters");
			} else {
				fail("failed testCanBuildSettlement test when in setup mode and test meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildSettlement test when in setup mode and test meets parameters - model not created");
		}
	}*/
	
	//No model
	@Test
	public void testCanBuildSettlement3() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(2,0), VertexDirection.NorthWest);
		try {
			CanModelFacade.sole().canBuildSettlement(vertLoc);
			fail("failed testCanBuildSettlement test when no model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanBuildSettlement test when no model");
		}
	}
	
	//Not your turn
	@Test
	public void testCanBuildSettlement4() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(2,0), VertexDirection.NorthWest);
		initModel("noTurn.txt");
		try {
			if(CanModelFacade.sole().canBuildSettlement(vertLoc) == false) {
				System.out.println("passed testCanBuildSettlement test when not your turn");
			} else {
				fail("failed testCanBuildSettlement test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildSettlement test when not your turn - model not created");
		}
	}
	
	//not playing
	@Test
	public void testCanBuildSettlement5() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(2,0), VertexDirection.NorthWest);
		initModel("noPlaying.txt");
		try {
			if(CanModelFacade.sole().canBuildSettlement(vertLoc) == false) {
				System.out.println("passed testCanBuildSettlement test when not in playing mode");
			} else {
				fail("failed testCanBuildSettlement test when not in playing mode");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildSettlement test when not in playing mode - model not created");
		}
	}
	
	//not open
	@Test
	public void testCanBuildSettlement6() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,0), VertexDirection.SouthWest);
		initModel("good.txt");
		try {
			if(CanModelFacade.sole().canBuildSettlement(vertLoc) == false) {
				System.out.println("passed testCanBuildSettlement test when not an open location");
			} else {
				fail("failed testCanBuildSettlement test when not an open location");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildSettlement test when not an open location - model not created");
		}
	}

	//on water
	@Test
	public void testCanBuildSettlement7() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,3), VertexDirection.SouthWest);
		initModel("good.txt");
		try {
			if(CanModelFacade.sole().canBuildSettlement(vertLoc) == false) {
				System.out.println("passed testCanBuildSettlement test when placing on water");
			} else {
				fail("failed testCanBuildSettlement test when placing on water");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildSettlement test when placing on water - model not created");
		}
	}
	
	//adjacent hex
		@Test
		public void testCanBuildSettlement8() {
			VertexLocation vertLoc = new VertexLocation(new HexLocation(1,1), VertexDirection.NorthWest);
			initModel("good.txt");
			try {
				if(CanModelFacade.sole().canBuildSettlement(vertLoc) == false) {
					System.out.println("passed testCanBuildSettlement test when placing on an adjacent vertex");
				} else {
					fail("failed testCanBuildSettlement test when placing on an adjacent vertex");
				}
			} catch (NullPointerException e) {
				fail("failed testCanBuildSettlement test when placing on an adjacent vertex - model not created");
			}
		}
	
	//not enough resources
	@Test
	public void testCanBuildSettlement9() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(2,0), VertexDirection.NorthWest);
		initModel("noRes.txt");
		try {
			if(CanModelFacade.sole().canBuildSettlement(vertLoc) == false) {
				System.out.println("passed testCanBuildSettlement test when not enough resources");
			} else {
				fail("failed testCanBuildSettlement test when not enough resources");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildSettlement test when not enough resources - model not created");
		}
	}
}
