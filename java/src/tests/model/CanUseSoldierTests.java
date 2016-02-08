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

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/soldier/" + file);
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
	
	//work
	/*
	 * your turn
	 * playing
	 * have card in old dev hand
	 * not played non monument
	 * robber moves
	 * player robbed has cards
	 */
	@Test
	public void testCanUseSoldier7() {
		initModel("good.txt");
		HexLocation newRobberLocation = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == true){
				System.out.println("pass testCanUseSoldier test when robber moved, no one to rob");
			} else {
				fail("fail testCanUseSoldier test when  robber moved, no one to rob");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseSoldier test when  robber moved, no one to rob");
		}
	}
	
	//robber moves, player can rob
	@Test
	public void testCanUseSoldier8() {
		initModel("good.txt");
		HexLocation newRobberLocation = new HexLocation(1,-1);
		int playerIndex = 2;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == true){
				System.out.println("pass testCanUseSoldier test when robber moved, someone to rob");
			} else {
				fail("fail testCanUseSoldier test when  robber moved, someone to rob");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseSoldier test when  robber moved, someone to rob");
		}
	}
	
	//robber moved, player cannot be robbed
	@Test
	public void testCanUseSoldier6() {
		initModel("noRobPete.txt");
		HexLocation newRobberLocation = new HexLocation(0,0);
		int playerIndex = 2;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("pass testCanUseSoldier test when robber moved, person has no resource");
			} else {
				fail("fail testCanUseSoldier test when  robber moved, person has no resource");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseSoldier test when  robber moved, person has no resource");
		}
	}

	//null model
	@Test
	public void testCanUseSoldier9() {
		HexLocation newRobberLocation = new HexLocation(0,-1);
		int playerIndex = -1;
		ModelFacade mf = new ModelFacade();
		try {
			mf.canUseSoldier(newRobberLocation, playerIndex);
			fail("fail testCanUseSoldier test when no model present");
		} catch (NullPointerException e) {
			System.out.println("pass testCanUseSoldier test when no model present");
		}
	}
	
	//not your turn
	@Test
	public void testCanUseSoldier1() {
		initModel("noTurn.txt");
		HexLocation newRobberLocation = new HexLocation(0,-1);
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
		initModel("noPlay.txt");
		HexLocation newRobberLocation = new HexLocation(0,-1);
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
	
	//don't have soldier card, in new hand
	@Test
	public void testCanUseSoldier3() {
		initModel("noOld.txt");
		HexLocation newRobberLocation = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			if(modelFacade.canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("pass testCanUseSoldier test when soldier card is new hand");
			} else {
				fail("fail testCanUseYearOfPlenty test when soldier card is new hand");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when soldier card is new hand - could not access model");
		}
	}
	
	//already played a card
	@Test
	public void testCanUseSoldier4() {
		initModel("alreadyPlayed.txt");
		HexLocation newRobberLocation = new HexLocation(0,-1);
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
		initModel("good.txt");
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
}