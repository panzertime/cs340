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
import shared.models.hand.ResourceType;

public class CanUseYearOfPlentyTests {
	
	ModelFacade modelFacade;

	@Before
	public void initModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/jsonMap.txt");
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
	
	public void initRobModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/robjson.txt");
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
	
	public void initNotTurnModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/notyourturn.txt");
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
	
	public void initAlreadyPlayedModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/alreadyplayeddevcard.txt");
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
	
	public void initNoBricksInBankModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/nobricksinbank.txt");
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
	
	//no game model
	@Test
	public void testCanUseYearOfPlenty1() {
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		ModelFacade mf = new ModelFacade();
		try {
			mf.canUseYearOfPlenty(one, two);
			fail("failed testCanUseYearOfPlenty test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("Passed testCanUseYearOfPlenty test with uninit model");
		}
	}
	
	//not your turn
	@Test
	public void testCanBuyDevCard2() {
		this.initNotTurnModel();
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		try {
			if(modelFacade.canUseYearOfPlenty(one, two) == false) {
				System.out.println("pass testCanUseYearOfPlenty test when not playing");
			} else {
				fail("fail testCanUseYearOfPlenty test when not playing");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when not playing - could not access model");
		}
	}
	
	//not playing
	@Test
	public void testCanUseYearOfPlenty3() {
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		this.initRobModel();
		try {
			if(modelFacade.canUseYearOfPlenty(one, two) == false) {
				System.out.println("pass testCanUseYearOfPlenty test when not playing");
			} else {
				fail("fail testCanUseYearOfPlenty test when not playing");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when not playing - could not access model");
		}
	}
	
	//dont have specific card in old dev hand
	@Test
	public void testCanUseYearOfPlenty4() {
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		this.initModel();
		try {
			if(modelFacade.canUseYearOfPlenty(one, two) == false) {
				System.out.println("pass testCanUseYearOfPlenty test when user doesn't have card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user doesn't have card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user doesn't have card - could not access model");
		}
	}
	
	//already played a non-mon dev card
	@Test
	public void testCanUseYearOfPlenty5() {
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		this.initAlreadyPlayedModel();
		try {
			if(modelFacade.canUseYearOfPlenty(one, two) == false) {
				System.out.println("pass testCanUseYearOfPlenty test when user already played card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user already played card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user already played card - could not access model");
		}
	}
	
	//2nd resource not in bank
	@Test
	public void testCanUseYearOfPlenty6() {
		ResourceType one = ResourceType.ORE;
		ResourceType two = ResourceType.BRICK;
		this.initNoBricksInBankModel();
		try {
			if(modelFacade.canUseYearOfPlenty(one, two) == false) {
				System.out.println("pass testCanUseYearOfPlenty test when user "
						+ "asks for bricks and there aren't any");
			} else {
				fail("fail testCanUseYearOfPlenty test when user "
						+ "asks for bricks and there aren't any");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user "
						+ "asks for bricks and there aren't any - could not access model");
		}
	}
	
	//works!
	@Test
	public void testCanUseYearOfPlenty7() {
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		this.initModel();
		try {
			if(modelFacade.canUseYearOfPlenty(one, two) == true) {
				System.out.println("pass testCanUseYearOfPlenty test when user already played card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user already played card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user already played card - could not access model");
		}
	}

}
