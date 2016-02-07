package tests.model;

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
import shared.models.exceptions.ModelAccessException;

public class CanOfferTradeTests {
	
	ModelFacade modelFacade;

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/canOfferTrade/" + file);
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
	 * working: - changed fullJSON for good input file
	1.	initModel()
	2.	turnIndex = 0
	3.	status = “Playing”
	wood – 2
	ore  - 2
	brick : -2
	wheat : -2
	sheep : 0
	 */
	@Test
	public void testCanOfferTrade1() {
		Map<String, Object> resource = new HashMap<String, Object>();
		resource.put("wood", 2);
		resource.put("ore", 2);
		resource.put("sheep", 0);
		resource.put("brick", -2);
		resource.put("wheat", -2);
		initModel("good.txt");
		try {
			if(modelFacade.canOfferTrade(resource) == true) {
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
		Map<String, Object> resource = new HashMap<String, Object>();
		resource.put("wood", 2);
		resource.put("ore", 2);
		resource.put("sheep", 0);
		resource.put("brick", -2);
		resource.put("wheat", -2);
		ModelFacade mf = new ModelFacade();
		try {
			modelFacade.canOfferTrade(resource);
			fail("failed testCanOfferTrade test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanOfferTrade test with uninit model");
		}
	}
	
	//2 – not your turn
	@Test
	public void testCanOfferTrade3() {
		Map<String, Object> resource = new HashMap<String, Object>();
		resource.put("wood", 2);
		resource.put("ore", 2);
		resource.put("sheep", 0);
		resource.put("brick", -2);
		resource.put("wheat", -2);
		initModel("noTurn.txt");
		try {
			if(modelFacade.canOfferTrade(resource) == false) {
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
		Map<String, Object> resource = new HashMap<String, Object>();
		resource.put("wood", 2);
		resource.put("ore", 2);
		resource.put("sheep", 0);
		resource.put("brick", -2);
		resource.put("wheat", -2);
		initModel("noPlay.txt");
		try {
			if(modelFacade.canOfferTrade(resource) == false) {
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
		Map<String, Object> resource = new HashMap<String, Object>();
		resource.put("wood", 2);
		resource.put("ore", 2);
		resource.put("sheep", 0);
		resource.put("brick", -2);
		resource.put("wheat", -2);
		initModel("noRes.txt");
		try {
			if(modelFacade.canOfferTrade(resource) == false) {
				System.out.println("passed testCanOfferTrade test when you don't have said resources");
			} else {
				fail("failed testCanOfferTrade test when you don't have said resources");
			}
		} catch (NullPointerException e) {
			fail("failed testCanOfferTrade test when you don't have said resources - model not created");
		}
	}
}
