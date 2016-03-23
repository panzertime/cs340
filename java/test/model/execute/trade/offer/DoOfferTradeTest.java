package model.execute.trade.offer;

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

import client.main.ClientPlayer;
import client.modelfacade.CanModelFacade;
import client.modelfacade.ModelFacade;
import client.modelfacade.testing.TestingModelFacade;
import shared.model.exceptions.BadJSONException;
import shared.model.hand.ResourceType;

public class DoOfferTradeTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/trade/offer/" + file);
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
	 * working: - changed fullJSON for good input file
	1.	initModel()
	2.	turnIndex = 0
	3.	status = Playing
	wood : 2
	ore  : -2
	brick : -2
	wheat : -2
	sheep : 0
	 */
	@Test
	public void testCanOfferTrade1() {
		Integer recieverIndex = 2;
		Map<ResourceType, Integer> resource = new HashMap<ResourceType, Integer>();
		resource.put(ResourceType.WOOD, 2);
		resource.put(ResourceType.ORE, 2);
		resource.put(ResourceType.SHEEP, 0);
		resource.put(ResourceType.BRICK, -2);
		resource.put(ResourceType.WHEAT, -2);
		initModel("good.txt");
		try {
			if(CanModelFacade.sole().canOfferTrade(resource, recieverIndex) == true) {
				System.out.println("passed testCanOfferTrade test when meets parameters");
			} else {
				fail("failed testCanOfferTrade test when when meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanOfferTrade test when when meets parameters - model not created");
		}
	}
	
	//1 – no model
	@Test
	public void testCanOfferTrade2() {
		Integer recieverIndex = 2;
		Map<ResourceType, Integer> resource = new HashMap<ResourceType, Integer>();
		resource.put(ResourceType.WOOD, 2);
		resource.put(ResourceType.ORE, 2);
		resource.put(ResourceType.SHEEP, 0);
		resource.put(ResourceType.BRICK, -2);
		resource.put(ResourceType.WHEAT, -2);
		try {
			CanModelFacade.sole().canOfferTrade(resource, recieverIndex);
			fail("failed testCanOfferTrade test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanOfferTrade test with uninit model");
		}
	}
	
	//2 – not your turn
	@Test
	public void testCanOfferTrade3() {
		Integer recieverIndex = 2;
		Map<ResourceType, Integer> resource = new HashMap<ResourceType, Integer>();
		resource.put(ResourceType.WOOD, 2);
		resource.put(ResourceType.ORE, 2);
		resource.put(ResourceType.SHEEP, 0);
		resource.put(ResourceType.BRICK, -2);
		resource.put(ResourceType.WHEAT, -2);
		initModel("noTurn.txt");
		try {
			if(CanModelFacade.sole().canOfferTrade(resource, recieverIndex) == false) {
				System.out.println("passed testCanOfferTrade test when not your turn");
			} else {
				fail("failed testCanOfferTrade test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanOfferTrade test when not your turn - model not created");
		}
	}
	
	//3 – client model is not playing
	@Test
	public void testCanOfferTrade4() {
		Integer recieverIndex = 2;
		Map<ResourceType, Integer> resource = new HashMap<ResourceType, Integer>();
		resource.put(ResourceType.WOOD, 2);
		resource.put(ResourceType.ORE, 2);
		resource.put(ResourceType.SHEEP, 0);
		resource.put(ResourceType.BRICK, -2);
		resource.put(ResourceType.WHEAT, -2);
		initModel("noPlay.txt");
		try {
			if(CanModelFacade.sole().canOfferTrade(resource, recieverIndex) == false) {
				System.out.println("passed testCanOfferTrade test when not in playing state");
			} else {
				fail("failed testCanOfferTrade test when in playing state");
			}
		} catch (NullPointerException e) {
			fail("failed testCanOfferTrade test when not in playing state - model not created");
		}
	}
	
	//4 – you don’t have resources you are offering
	@Test
	public void testCanOfferTrade5() {
		Integer recieverIndex = 2;
		Map<ResourceType, Integer> resource = new HashMap<ResourceType, Integer>();
		resource.put(ResourceType.WOOD, 2);
		resource.put(ResourceType.ORE, 2);
		resource.put(ResourceType.SHEEP, 0);
		resource.put(ResourceType.BRICK, -2);
		resource.put(ResourceType.WHEAT, -2);
		initModel("noRes.txt");
		try {
			if(CanModelFacade.sole().canOfferTrade(resource, recieverIndex) == false) {
				System.out.println("passed testCanOfferTrade test when you don't have said resources");
			} else {
				fail("failed testCanOfferTrade test when you don't have said resources");
			}
		} catch (NullPointerException e) {
			fail("failed testCanOfferTrade test when you don't have said resources - model not created");
		}
	}
}
