package model.execute;

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

public class DoBuyDevCardTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}

	public void initModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/jsonMap.txt");
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
	
	public void initNotTurnModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/notyourturn.txt");
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
	
	public void initRobModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/robjson.txt");
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
	
	public void initNoDevCardsModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/jsondevless.txt");
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
	
	public void initWorkingModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/jsonresourceyess.txt");
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
	
	//work!, 
	/*
	 * your turn, 
	 * client model is playing
	 * you have the resources
	 * there are dev cards left in the deck
	 */
	@Test
	public void testCanBuyDevCard6() {
		this.initWorkingModel();
		try {
			if(CanModelFacade.sole().canBuyDevCard() == true) {
				System.out.println("passed canBuydevCard test when can buy dev card");
			} else {
				fail("fail canBuydevCard test when can buy dev card");
			}
		} catch (NullPointerException e) {
			fail("fail canBuydevCard test when can buy dev card - could not access model");
		}
	}

	//no model
	@Test
	public void testCanBuyDevCard1() {
		try {
			CanModelFacade.sole().canBuyDevCard();
			fail("failed testCanBuyDevCard test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanBuyDevCard test with uninit model");
		}
	}
	
	//not your turn
	@Test
	public void testCanBuyDevCard2() {
		this.initNotTurnModel();
		try {
			if(CanModelFacade.sole().canBuyDevCard() == false) {
				System.out.println("passed canBuydevCard test when not your turn");
			} else {
				fail("fail canBuydevCard test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("fail canBuydevCard test when not your turn - could not access model");
		}
	}
	
	//not playing
	@Test
	public void testCanBuyDevCard3() {
		this.initRobModel();
		try {
			if(CanModelFacade.sole().canBuyDevCard() == false) {
				System.out.println("passed canBuydevCard test when not playing");
			} else {
				fail("fail canBuydevCard test when not playing");
			}
		} catch (NullPointerException e) {
			fail("fail canBuydevCard test when not playing - could not access model");
		}
	}
	
	//dont have resources
	@Test
	public void testCanBuyDevCard4() {
		this.initModel();
		try {
			if(CanModelFacade.sole().canBuyDevCard() == false) {
				System.out.println("passed canBuydevCard test when not enough resources");
			} else {
				fail("fail canBuydevCard test when not enough resources");
			}
		} catch (NullPointerException e) {
			fail("fail canBuydevCard test when not enough resources - could not access model");
		}
	}
	
	//no dev cards left
	@Test
	public void testCanBuyDevCard5() {
		this.initNoDevCardsModel();
		try {
			if(CanModelFacade.sole().canBuyDevCard() == false) {
				System.out.println("passed canBuydevCard test when no more dev cards left");
			} else {
				fail("fail canBuydevCard test when no more dev cards left");
			}
		} catch (NullPointerException e) {
			fail("fail canBuydevCard test when no more dev cards left - could not access model");
		}
	}
}
