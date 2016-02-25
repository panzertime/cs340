package model.can.use.yearofplenty;

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
import shared.model.hand.ResourceType;

public class CanUseYearOfPlentyTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}


	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/use/yearofplenty/" + file);
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
	
	//works!
	/*
	 * your turn
	 * client is playing
	 * have the card in old dev hand
	 * have not yet played
	 * two resources are in the bank
	 */
	@Test
	public void testCanUseYearOfPlenty7() {
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		initModel("good.txt");
		try {
			if(CanModelFacade.sole().canUseYearOfPlenty(one, two) == true) {
				System.out.println("passed testCanUseYearOfPlenty test when user can play card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user can play card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user can play card - could not access model");
		}
	}
	
	//no game model
	@Test
	public void testCanUseYearOfPlenty1() {
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		try {
			CanModelFacade.sole().canUseYearOfPlenty(one, two);
			fail("failed testCanUseYearOfPlenty test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanUseYearOfPlenty test with uninit model");
		}
	}
	
	//not your turn
	@Test
	public void testCanBuyDevCard2() {
		initModel("noTurn.txt");
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		try {
			if(CanModelFacade.sole().canUseYearOfPlenty(one, two) == false) {
				System.out.println("passed testCanUseYearOfPlenty test when not playing");
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
		initModel("noPlay.txt");
		try {
			if(CanModelFacade.sole().canUseYearOfPlenty(one, two) == false) {
				System.out.println("passed testCanUseYearOfPlenty test when not playing");
			} else {
				fail("fail testCanUseYearOfPlenty test when not playing");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when not playing - could not access model");
		}
	}
	
	//just got the card
	@Test
	public void testCanUseYearOfPlenty4() {
		ResourceType one = ResourceType.BRICK;
		ResourceType two = ResourceType.ORE;
		initModel("newCard.txt");
		try {
			if(CanModelFacade.sole().canUseYearOfPlenty(one, two) == false) {
				System.out.println("passed testCanUseYearOfPlenty test when user doesn't have card");
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
		initModel("alreadyPlayed.txt");
		try {
			if(CanModelFacade.sole().canUseYearOfPlenty(one, two) == false) {
				System.out.println("passed testCanUseYearOfPlenty test when user already played card");
			} else {
				fail("fail testCanUseYearOfPlenty test when user already played card");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user already played card - could not access model");
		}
	}
	
	//1st resource not in bank
	@Test
	public void testCanUseYearOfPlenty8() {
		ResourceType one = ResourceType.ORE;
		ResourceType two = ResourceType.BRICK;
		initModel("noRes1.txt");
		try {
			if(CanModelFacade.sole().canUseYearOfPlenty(one, two) == false) {
				System.out.println("passed testCanUseYearOfPlenty test when user "
						+ "asks for ore(1st resource) and there aren't any");
			} else {
				fail("fail testCanUseYearOfPlenty test when user "
						+ "asks for ore(1st resource) and there aren't any");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user "
						+ "asks for ore(1st resource) and there aren't any - could not access model");
		}
	}
	
	//2nd resource not in bank
	@Test
	public void testCanUseYearOfPlenty6() {
		ResourceType one = ResourceType.ORE;
		ResourceType two = ResourceType.BRICK;
		initModel("noRes2.txt");
		try {
			if(CanModelFacade.sole().canUseYearOfPlenty(one, two) == false) {
				System.out.println("passed testCanUseYearOfPlenty test when user "
						+ "asks for bricks(2nd resource) and there aren't any");
			} else {
				fail("fail testCanUseYearOfPlenty test when user "
						+ "asks for bricks(2nd resource) and there aren't any");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseYearOfPlenty test when user "
						+ "asks for bricks(2nd resource) and there aren't any - could not access model");
		}
	}
}
