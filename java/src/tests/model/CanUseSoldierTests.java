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
import shared.models.board.hex.HexLocation;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.ModelAccessException;

public class CanUseSoldierTests {
	
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
	
	public void initHasSoldierAlreadyPlayedModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/hassoldieralreadyplayed.txt");
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
	
	public void initHasSoldierGoodToGoModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/goodsoldier.txt");
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
	
	public void initGoodSoldierNoResourcesModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/goodsoldiernoresources.txt");
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

	//not your turn
	@Test
	public void testCanUseSoldier1() {
		this.initNotTurnModel();
		HexLocation newRobberLocation = new HexLocation(0,1);
		int playerIndex = -1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("pass testCanUseSoldier test when not your turn");
			} else {
				fail("fail testCanUseYearOfPlenty test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when not your turn - could not access model");
		}
	}
	
	//not playing
	@Test
	public void testCanUseSoldier2() {
		this.initRobModel();
		HexLocation newRobberLocation = new HexLocation(0,1);
		int playerIndex = -1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("pass testCanUseSoldier test when not playing mode");
			} else {
				fail("fail testCanUseYearOfPlenty test when not playing mode");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when not playing mode - could not access model");
		}
	}
	
	//don't have soldier card
	@Test
	public void testCanUseSoldier3() {
		this.initModel();
		HexLocation newRobberLocation = new HexLocation(0,1);
		int playerIndex = -1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("pass testCanUseSoldier test when user has no soldier card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user has no soldier card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user has no soldier card - could not access model");
		}
	}
	
	//already played a card
	@Test
	public void testCanUseSoldier4() {
		this.initHasSoldierAlreadyPlayedModel();
		HexLocation newRobberLocation = new HexLocation(0,1);
		int playerIndex = -1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("pass testCanUseSoldier test when user already played card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user already played card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user already played card - could not access model");
		}
	}
	
	//robber doesn't move
	@Test
	public void testCanUseSoldier5() {
		this.initHasSoldierGoodToGoModel();
		HexLocation newRobberLocation = new HexLocation(0,-2);
		int playerIndex = -1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("pass testCanUseSoldier test when user already played card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user already played card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user already played card - could not access model");
		}
	}
	
	//player doens't have resources
	@Test
	public void testCanUseSoldier6() {
		this.initGoodSoldierNoResourcesModel();
		HexLocation newRobberLocation = new HexLocation(-2,1);
		int playerIndex = 1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("pass testCanUseSoldier test when player to rob doesn't have resources");
			} else {
				fail("fail testCanUseYearOfPlenty test when player to rob doesn't have resources");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when player to rob doesn't have resources - could not access model");
		}
	}
	
	//work - and rob
	@Test
	public void testCanUseSoldier7() {
		this.initHasSoldierGoodToGoModel();
		HexLocation newRobberLocation = new HexLocation(1,-1);
		int playerIndex = -1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == true){
				System.out.println("pass testCanUseSoldier test when works");
			} else {
				fail("fail testCanUseSoldier test when works");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseSoldier test when works");
		}
	}
}