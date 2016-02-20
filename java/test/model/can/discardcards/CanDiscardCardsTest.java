package model.can.discardcards;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import client.modelfacade.CanModelFacade;
import client.modelfacade.ModelFacade;
import client.modelfacade.testing.TestingModelFacade;
import shared.model.exceptions.BadJSONException;
import shared.model.hand.ResourceType;

public class CanDiscardCardsTest {
	
	@Before
	public void initFacades() {
		CanModelFacade.sole().setUserIndex(0);
		TestingModelFacade.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/discardcards/" + file);
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
	5 – working:
	1.	initModel
	2.	status = discarding
	3.	cards = 8
	4.	discard 1 - brick
	*/
	@Test
	public void testCanDiscardCards1() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 0);
		resourceList.put(ResourceType.ORE, 0);
		resourceList.put(ResourceType.SHEEP, 0);
		resourceList.put(ResourceType.BRICK, 1);
		resourceList.put(ResourceType.WHEAT, 0);
		initModel("good.txt");
		try {
			if(CanModelFacade.sole().canDiscardCards(resourceList) == true) {
				System.out.println("passed testCanDiscardCards test when meets parameters");
			} else {
				fail("failed testCanDiscardCards test when meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanDiscardCards test when meets parameters - model not created");
		}
	}
	
	//1 – no model
	@Test
	public void testCanDiscardCards2() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 0);
		resourceList.put(ResourceType.ORE, 0);
		resourceList.put(ResourceType.SHEEP, 0);
		resourceList.put(ResourceType.BRICK, 1);
		resourceList.put(ResourceType.WHEAT, 0);
		try {
			CanModelFacade.sole().canDiscardCards(resourceList);
			fail("failed testCanDiscardCards test when there is no model init");
		} catch (NullPointerException e) {
			System.out.println("passed testCanDiscardCards test when there is no model init");
		}
	}
	
	//2 – status is not Discarding
	@Test
	public void testCanDiscardCards3() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 0);
		resourceList.put(ResourceType.ORE, 0);
		resourceList.put(ResourceType.SHEEP, 0);
		resourceList.put(ResourceType.BRICK, 1);
		resourceList.put(ResourceType.WHEAT, 0);
		initModel("noDiscard.txt");
		try {
			if(CanModelFacade.sole().canDiscardCards(resourceList) == false) {
				System.out.println("passed testCanDiscardCards test when not discarding");
			} else {
				fail("failed testCanDiscardCards test when not discarding");
			}
		} catch (NullPointerException e) {
			fail("failed testCanDiscardCards test when not discarding - model not created");
		}
	}
	
	//3 – you have less than 8 cards
	@Test
	public void testCanDiscardCards4() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 0);
		resourceList.put(ResourceType.ORE, 0);
		resourceList.put(ResourceType.SHEEP, 0);
		resourceList.put(ResourceType.BRICK, 1);
		resourceList.put(ResourceType.WHEAT, 0);
		initModel("noEnoughCards.txt");
		try {
			if(CanModelFacade.sole().canDiscardCards(resourceList) == false) {
				System.out.println("passed testCanDiscardCards test when you have less than 8 cards");
			} else {
				fail("failed testCanDiscardCards test when you have less than 8 cards");
			}
		} catch (NullPointerException e) {
			fail("failed testCanDiscardCards test when you have less than 8 cards - model not created");
		}
	}
	
	//4 – You don’t have the cards you choosing to discard
	@Test
	public void testCanDiscardCards5() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 1);
		resourceList.put(ResourceType.ORE, 1);
		resourceList.put(ResourceType.SHEEP, 1);
		resourceList.put(ResourceType.BRICK, 1);
		resourceList.put(ResourceType.WHEAT, 1);
		initModel("good.txt");
		try {
			if(CanModelFacade.sole().canDiscardCards(resourceList) == false) {
				System.out.println("passed testCanDiscardCards test when you don't have the cards to discard that you think you do");
			} else {
				fail("failed testCanDiscardCards test when you don't have the cards to discard that you think you do");
			}
		} catch (NullPointerException e) {
			fail("failed testCanDiscardCards test when you don't have the cards to discard that you think you do - model not created");
		}
	}
}
