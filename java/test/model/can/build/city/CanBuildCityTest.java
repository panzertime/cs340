package model.can.build.city;

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

import client.modelfacade.CanModelFacade;
import client.modelfacade.ModelFacade;
import client.modelfacade.TestingModelFacade;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.BadJSONException;

public class CanBuildCityTest {
	
	@Before
	public void initFacades() {
		CanModelFacade.sole().setUserID(0);
		TestingModelFacade.sole().setUserID(0);
		TestingModelFacade.sole().emptyModel();
	}

	
	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/build/city/" + file);
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
	 * Working: 
		1.	initModel()
		2.	turnIndex = 0
		3.	status = Playing
		4.	new Loc(0,1,SE);
		5.	2 wheat 3 ore 1 city
	 */
	@Test
	public void testCanBuildCity1() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.SouthEast);
		initModel("buildCity.txt");
		try {
			if(CanModelFacade.sole().canBuildCity(vertLoc) == true) {
				System.out.println("passed testCanBuildCity test when given good input and should return true");
			} else {
				fail("failed testCanUseMonopoly test when given good input and should return true");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when given good input and should return true - model not created");
		}
	}

	//1 – no model
	@Test
	public void testCanBuildCity2() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.SouthEast);
		try {
			CanModelFacade.sole().canBuildCity(vertLoc);
			fail("failed testCanUseMonopoly test when no model is present");
		} catch (NullPointerException e) {
			System.out.println("passed testCanBuildCity test when no model is present");
		}
	}
	
	//2 – not your turn - 3
	@Test
	public void testCanBuildCity3() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.SouthEast);
		initModel("noTurn.txt");
		try {
			if(CanModelFacade.sole().canBuildCity(vertLoc) == false) {
				System.out.println("passed testCanBuildCity test when not your turn");
			} else {
				fail("failed testCanUseMonopoly test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when not your turn - model not created");
		}
	}
	
	//3 – client model is not playing - SecondRound
	@Test
	public void testCanBuildCity4() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.SouthEast);
		initModel("noPlay.txt");
		try {
			if(CanModelFacade.sole().canBuildCity(vertLoc) == false) {
				System.out.println("passed testCanBuildCity test when not playing");
			} else {
				fail("failed testCanUseMonopoly test when not playing");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when not playing - model not created");
		}
	}
	
	//4 – location is not currently a settlement – loc (0,0,SW)(Worse – petes settlement)
	@Test
	public void testCanBuildCity5() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,0), VertexDirection.SouthWest);
		initModel("buildCity.txt");
		try {
			if(CanModelFacade.sole().canBuildCity(vertLoc) == false) {
				System.out.println("passed testCanBuildCity test when building on anothers settlement");
			} else {
				fail("failed testCanUseMonopoly test when building on anothers settlement");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when building on anothers settlement - model not created");
		}
	}
	
	//5 – Doesn’t have 2 wheat, 3 ore and 1 city (missing city)
	@Test
	public void testCanBuildCity6() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.SouthEast);
		initModel("resources.txt");
		try {
			if(CanModelFacade.sole().canBuildCity(vertLoc) == false) {
				System.out.println("passed testCanBuildCity test when user doesn't have resources");
			} else {
				fail("failed testCanUseMonopoly test when user doesn't have resources");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when user doesn't have resources - model not created");
		}
	}
}
