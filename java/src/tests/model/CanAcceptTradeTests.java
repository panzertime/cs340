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
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.ModelAccessException;

public class CanAcceptTradeTests {
	
	ModelFacade modelFacade;

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/canaccepttrade/" + file);
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
	1.	initModel()
	2.	tradeOffer – to player 0 
	3.	wheat = 2
	4.  wood = 2
	 */
	//Good tests
	@Test
	public void testCanAcceptTrade() {
		initModel("good.txt");
		try {
			if(modelFacade.canAcceptTrade() == true) {
				System.out.println("passed testCanAcceptTrade test when meets parameters");
			} else {
				fail("failed testCanAcceptTrade test when when meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanAcceptTrade test when when meets parameters - model not created");
		}
	}
	
	//1 – no model
	@Test
	public void testCanAcceptTrade2() {
		ModelFacade mf = new ModelFacade();
		try {
			mf.canAcceptTrade();
			fail("failed testCanAcceptTrade test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanAcceptTrade test with uninit model");
		}
	}
	
	//2 – you have not been offered a domestic trade
	@Test
	public void testCanAcceptTrade3() {
		initModel("noTrade.txt");
		try {
			if(modelFacade.canAcceptTrade() == false) {
				System.out.println("passed testCanAcceptTrade test when not offered a trade");
			} else {
				fail("failed testCanAcceptTrade test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanAcceptTrade test when not offered a trade - model not created");
		}
	}
	
	//3 – you don’t have the required resources (only one wood)
	@Test
	public void testCanAcceptTrade4() {
		initModel("noTrade.txt");
		try {
			if(modelFacade.canAcceptTrade() == false) {
				System.out.println("passed testCanAcceptTrade test when you don't have the resources");
			} else {
				fail("failed testCanAcceptTrade test when you don't have the resources");
			}
		} catch (NullPointerException e) {
			fail("failed testCanAcceptTrade test when you don't have the resources - model not created");
		}
	}
}
