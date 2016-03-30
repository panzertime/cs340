package model.can.trade.accept;

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
import shared.model.exceptions.BadJSONException;

public class CanAcceptTradeTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/trade/accept/" + file);
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
	
	//1 – no model
	@Test
	public void testCanAcceptTrade2() {
		try {
			CanModelFacade.sole().canAcceptTrade();
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
			if(CanModelFacade.sole().canAcceptTrade() == false) {
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
			if(CanModelFacade.sole().canAcceptTrade() == false) {
				System.out.println("passed testCanAcceptTrade test when you don't have the resources");
			} else {
				fail("failed testCanAcceptTrade test when you don't have the resources");
			}
		} catch (NullPointerException e) {
			fail("failed testCanAcceptTrade test when you don't have the resources - model not created");
		}
	}

	/*
	1.	initModel()
	2.	tradeOffer – to player 0 
	3.	wheat = 2
	4.  wood = 2
	 */
	//Good tests
	/*@Test
	public void testCanAcceptTrade() {
		initModel("good.txt");
		try {
			if(CanModelFacade.sole().canAcceptTrade() == true) {
				System.out.println("passed testCanAcceptTrade test when meets parameters");
			} else {
				fail("failed testCanAcceptTrade test when when meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanAcceptTrade test when when meets parameters - model not created");
		}
	}
	
	// not your turn - still works
	@Test
	public void testCanAcceptTrade5() {
		initModel("noTurn.txt");
		try {
			if(CanModelFacade.sole().canAcceptTrade() == true) {
				System.out.println("passed testCanAcceptTrade test when it is not your turn");
			} else {
				fail("failed testCanAcceptTrade test when it is not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanAcceptTrade test when it is not your turn - model not created");
		}
	}
	
	//not playing - still works
	@Test
	public void testCanAcceptTrade6() {
		initModel("noPlay.txt");
		try {
			if(CanModelFacade.sole().canAcceptTrade() == true) {
				System.out.println("passed testCanAcceptTrade test when the model is not playing");
			} else {
				fail("failed testCanAcceptTrade test when the model is not playing");
			}
		} catch (NullPointerException e) {
			fail("failed testCanAcceptTrade test when the model is not playing - model not created");
		}
	}*/
}
