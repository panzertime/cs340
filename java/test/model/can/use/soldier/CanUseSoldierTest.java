package model.can.use.soldier;

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
import shared.model.board.hex.HexLocation;
import shared.model.exceptions.BadJSONException;

public class CanUseSoldierTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}


	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/use/soldier/" + file);
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
	
	//work
	/*
	 * your turn
	 * playing
	 * have card in old dev hand
	 * not played non monument
	 * robber moves
	 * player robbed has cards
	 */
	/*@Test
	public void testCanUseSoldier7() {
		initModel("good.txt");
		HexLocation newRobberLocation = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			if(CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex) == true){
				System.out.println("passed testCanUseSoldier test when robber moved, no one to rob");
			} else {
				fail("fail testCanUseSoldier test when  robber moved, no one to rob");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseSoldier test when  robber moved, no one to rob");
		}
	}*/
	
	//robber moves, player can rob
	@Test
	public void testCanUseSoldier8() {
		initModel("good.txt");
		HexLocation newRobberLocation = new HexLocation(1,-1);
		int playerIndex = 2;
		try {
			if(CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex) == true){
				System.out.println("passed testCanUseSoldier test when robber moved, someone to rob");
			} else {
				fail("fail testCanUseSoldier test when  robber moved, someone to rob");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseSoldier test when  robber moved, someone to rob");
		}
	}
	
	//robber moved, player cannot be robbed
	/*@Test
	public void testCanUseSoldier6() {
		initModel("noRobPete.txt");
		HexLocation newRobberLocation = new HexLocation(0,0);
		int playerIndex = 2;
		try {
			if(CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("passed testCanUseSoldier test when robber moved, person has no resource");
			} else {
				fail("fail testCanUseSoldier test when  robber moved, person has no resource");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseSoldier test when  robber moved, person has no resource");
		}
	}*/

	//null model
	@Test
	public void testCanUseSoldier9() {
		HexLocation newRobberLocation = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex);
			fail("fail testCanUseSoldier test when no model present");
		} catch (NullPointerException e) {
			System.out.println("passed testCanUseSoldier test when no model present");
		}
	}
	
	//not your turn
	@Test
	public void testCanUseSoldier1() {
		initModel("noTurn.txt");
		HexLocation newRobberLocation = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			if(CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("passed testCanUseSoldier test when not your turn");
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
			if(CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("passed testCanUseSoldier test when not playing mode");
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
			if(CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("passed testCanUseSoldier test when soldier card is new hand");
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
			if(CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("passed testCanUseSoldier test when user already played card");
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
			if(CanModelFacade.sole().canUseSoldier(newRobberLocation, playerIndex) == false){
				System.out.println("passed testCanUseSoldier test when user already played card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user already played card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user already played card - could not access model");
		}
	}
}