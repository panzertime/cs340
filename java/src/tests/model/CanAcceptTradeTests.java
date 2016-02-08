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
	 * "tests.model.CanAcceptTradeTests"

2 – you have not been offered a domestic trade
3 – you don’t have the required resources



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
	
	@Test
	public void testCanAcceptTrade3() {
		try {
			modelFacade.canAcceptTrade();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//Bad tests
	@Test
	public void testCanAcceptTrade4() {
		try {
			modelFacade.canAcceptTrade();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanAcceptTrade5() {
		try {
			modelFacade.canAcceptTrade();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanAcceptTrade6() {
		try {
			modelFacade.canAcceptTrade();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
}
