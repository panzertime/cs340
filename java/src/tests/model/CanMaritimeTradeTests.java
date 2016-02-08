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
import shared.models.board.edge.EdgeLocation;
import shared.models.board.vertex.Vertex;
import shared.models.exceptions.BadJSONException;
import shared.models.hand.ResourceType;

public class CanMaritimeTradeTests {
	
	ModelFacade modelFacade;

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/canmaritime/" + file);
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
6 – Working: ratio – 3 (You have the port)
1.	initModel()
2.	turn = 0
3.	Status = playing
4.	3 Wheat for 1 Brick
5.	Settlement (2,0,SW)
	 */
	//Good tests
	@Test
	public void testCanMaritimeTrade1() {
		int ratio = 3;
		ResourceType inputType = ResourceType.WHEAT;
		ResourceType outputType = ResourceType.BRICK;
		initModel("good.txt");
		try {
			if(modelFacade.canMaritimeTrade(ratio, inputType, outputType) == true) {
				System.out.println("passed testCanMaritimeTrade test when meets parameters");
			} else {
				fail("failed testCanMaritimeTrade test when when meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanMaritimeTrade test when when meets parameters - model not created");
		}
	}

	//1 – no model
	@Test
	public void testCanMaritimeTrade2() {
		int ratio = 3;
		ResourceType inputType = ResourceType.WHEAT;
		ResourceType outputType = ResourceType.BRICK;
		ModelFacade mf = new ModelFacade();
		try {
			mf.canMaritimeTrade(ratio, inputType, outputType);
			fail("failed testCanMaritimeTrade test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanMaritimeTrade test with uninit model");
		}
	}
	
	//2 – it is not your turn
	@Test
	public void testCanMaritimeTrade3() {
		int ratio = 3;
		ResourceType inputType = ResourceType.WHEAT;
		ResourceType outputType = ResourceType.BRICK;
		initModel("noTurn.txt");
		try {
			if(modelFacade.canMaritimeTrade(ratio, inputType, outputType) == true) {
				System.out.println("passed testCanMaritimeTrade test when it's not your turn");
			} else {
				fail("failed testCanMaritimeTrade test when it's not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanMaritimeTrade test when it's not your turn - model not created");
		}
	}
	
	//3 – model is not playing
	@Test
	public void testCanMaritimeTrade4() {
		int ratio = 3;
		ResourceType inputType = ResourceType.WHEAT;
		ResourceType outputType = ResourceType.BRICK;
		initModel("noPlay.txt");
		try {
			if(modelFacade.canMaritimeTrade(ratio, inputType, outputType) == true) {
				System.out.println("passed testCanMaritimeTrade test when model is not playing");
			} else {
				fail("failed testCanMaritimeTrade test when model is not playing");
			}
		} catch (NullPointerException e) {
			fail("failed testCanMaritimeTrade test when model is not playing - model not created");
		}
	}
	
	//4 – you don’t have the resources you are giving (2 wheat)
	@Test
	public void testCanMaritimeTrade5() {
		int ratio = 3;
		ResourceType inputType = ResourceType.WHEAT;
		ResourceType outputType = ResourceType.BRICK;
		initModel("noRes.txt");
		try {
			if(modelFacade.canMaritimeTrade(ratio, inputType, outputType) == true) {
				System.out.println("passed testCanMaritimeTrade test when user doesn't have resources");
			} else {
				fail("failed testCanMaritimeTrade test when user doesn't have resources");
			}
		} catch (NullPointerException e) {
			fail("failed testCanMaritimeTrade test when user doesn't have resources - model not created");
		}
	}
	
	//5 – you don’t have port for a ratio < 4
	//(2,0,SE)->(2,0,W)
	@Test
	public void testCanMaritimeTrade6() {
		int ratio = 3;
		ResourceType inputType = ResourceType.WHEAT;
		ResourceType outputType = ResourceType.BRICK;
		initModel("noPort.txt");
		try {
			if(modelFacade.canMaritimeTrade(ratio, inputType, outputType) == true) {
				System.out.println("passed testCanMaritimeTrade test when user doesn't have port");
			} else {
				fail("failed testCanMaritimeTrade test when user doesn't have port");
			}
		} catch (NullPointerException e) {
			fail("failed testCanMaritimeTrade test when user doesn't have port - model not created");
		}
	}
}
