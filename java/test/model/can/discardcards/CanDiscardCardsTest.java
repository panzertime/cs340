package model.can.discardcards;

import static org.junit.Assert.*;

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

import shared.models.ModelFacade;
import shared.models.exceptions.BadJSONException;
import shared.models.hand.exceptions.BadResourceTypeException;

public class CanDiscardCardsTest {
	
	ModelFacade modelFacade;

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
			
			Map jsonModel = (Map) parser.parse(x);
			
			modelFacade = new ModelFacade((JSONObject) jsonModel, 0);
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
		Map<String, Object> resourceList = new HashMap<String, Object>();
		resourceList.put("wood", 0);
		resourceList.put("ore", 0);
		resourceList.put("sheep", 0);
		resourceList.put("brick", 1);
		resourceList.put("wheat", 0);
		initModel("good.txt");
		try {
			if(modelFacade.canDiscardCards(resourceList) == true) {
				System.out.println("passed testCanDiscardCards test when meets parameters");
			} else {
				fail("failed testCanDiscardCards test when meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanDiscardCards test when meets parameters - model not created");
		} catch (BadResourceTypeException e) {
			fail("failed testCanDiscardCards test when meets parameters - you passed me a bad resource map");
		}
	}
	
	//1 – no model
	@Test
	public void testCanDiscardCards2() {
		Map<String, Object> resourceList = new HashMap<String, Object>();
		resourceList.put("wood", 0);
		resourceList.put("ore", 0);
		resourceList.put("sheep", 0);
		resourceList.put("brick", 1);
		resourceList.put("wheat", 0);
		ModelFacade mf = new ModelFacade();
		try {
			mf.canDiscardCards(resourceList);
			fail("failed testCanDiscardCards test when there is no model init");
		} catch (NullPointerException e) {
			System.out.println("passed testCanDiscardCards test when there is no model init");
		} catch (BadResourceTypeException e) {
			fail("failed testCanDiscardCards test when there is no model init - you passed me a bad resource map");
		}
	}
	
	//2 – status is not Discarding
	@Test
	public void testCanDiscardCards3() {
		Map<String, Object> resourceList = new HashMap<String, Object>();
		resourceList.put("wood", 0);
		resourceList.put("ore", 0);
		resourceList.put("sheep", 0);
		resourceList.put("brick", 1);
		resourceList.put("wheat", 0);
		initModel("noDiscard.txt");
		try {
			if(modelFacade.canDiscardCards(resourceList) == false) {
				System.out.println("passed testCanDiscardCards test when not discarding");
			} else {
				fail("failed testCanDiscardCards test when not discarding");
			}
		} catch (NullPointerException e) {
			fail("failed testCanDiscardCards test when not discarding - model not created");
		} catch (BadResourceTypeException e) {
			fail("failed testCanDiscardCards test when not discarding - you passed me a bad resource map");
		}
	}
	
	//3 – you have less than 8 cards
	@Test
	public void testCanDiscardCards4() {
		Map<String, Object> resourceList = new HashMap<String, Object>();
		resourceList.put("wood", 0);
		resourceList.put("ore", 0);
		resourceList.put("sheep", 0);
		resourceList.put("brick", 1);
		resourceList.put("wheat", 0);
		initModel("noEnoughCards.txt");
		try {
			if(modelFacade.canDiscardCards(resourceList) == false) {
				System.out.println("passed testCanDiscardCards test when you have less than 8 cards");
			} else {
				fail("failed testCanDiscardCards test when you have less than 8 cards");
			}
		} catch (NullPointerException e) {
			fail("failed testCanDiscardCards test when you have less than 8 cards - model not created");
		} catch (BadResourceTypeException e) {
			fail("failed testCanDiscardCards test you have less than 8 cards - you passed me a bad resource map");
		}
	}
	
	//4 – You don’t have the cards you choosing to discard
	@Test
	public void testCanDiscardCards5() {
		Map<String, Object> resourceList = new HashMap<String, Object>();
		resourceList.put("wood", 1);
		resourceList.put("ore", 1);
		resourceList.put("sheep", 1);
		resourceList.put("brick", 1);
		resourceList.put("wheat", 1);
		initModel("good.txt");
		try {
			if(modelFacade.canDiscardCards(resourceList) == false) {
				System.out.println("passed testCanDiscardCards test when you don't have the cards to discard that you think you do");
			} else {
				fail("failed testCanDiscardCards test when you don't have the cards to discard that you think you do");
			}
		} catch (NullPointerException e) {
			fail("failed testCanDiscardCards test when you don't have the cards to discard that you think you do - model not created");
		} catch (BadResourceTypeException e) {
			fail("failed testCanDiscardCards test when you don't have the cards to discard that you think you do - you passed me a bad resource map");
		}
	}
}
