package model.can.use.monopoly;

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

import client.modelfacade.CanModelFacade;
import client.modelfacade.ModelFacade;
import client.modelfacade.TestingModelFacade;
import shared.model.exceptions.BadJSONException;
import shared.model.hand.ResourceType;

public class CanUseMonopolyTest {
	
	@Before
	public void initFacades() {
		CanModelFacade.sole().setUserID(0);
		TestingModelFacade.sole().setUserID(0);
		TestingModelFacade.sole().emptyModel();
	}


	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/use/monopoly/" + file);
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
	
	//no model
	@Test
	public void testCanUseMonopoly1() {
		ResourceType type = ResourceType.BRICK;
		try {
			CanModelFacade.sole().canUseMonopoly(type);
			fail("failed testCanUseMonopoly test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanUseMonopoly test with uninit model");
		}
	}
	
	//Not your turn
	@Test
	public void testCanUseMonopoly2() {
		this.initModel("notTurn.txt");
		ResourceType type = ResourceType.BRICK;
		try {
			if(CanModelFacade.sole().canUseMonopoly(type) == false) {
				System.out.println("passed testCanUseMonopoly test when not your turn");
			} else {
				fail("failed testCanUseMonopoly test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when not your turn - model not created");
		}
	}
	
	//Client model isn't playing
	@Test
	public void testCanUseMonopoly3() {
		this.initModel("notPlaying.txt");
		ResourceType type = ResourceType.BRICK;
		try {
			if(CanModelFacade.sole().canUseMonopoly(type) == false) {
				System.out.println("passed testCanUseMonopoly test when not in playing mode");
			} else {
				fail("failed testCanUseMonopoly test when not in playing mode");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when not in playing mode - model not created");
		}
	}
	
	//You don't have the specific card
	@Test
	public void testCanUseMonopoly4() {
		this.initModel("noCard.txt");
		ResourceType type = ResourceType.BRICK;
		try {
			if(CanModelFacade.sole().canUseMonopoly(type) == false) {
				System.out.println("passed testCanUseMonopoly test when you don't have the card");
			} else {
				fail("failed testCanUseMonopoly test when you don't have the card");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when you don't have the card - model not created");
		}
	}
	
	//you have already played a dev card
	@Test
	public void testCanUseMonopoly5() {
		this.initModel("alreadyDeved.txt");
		ResourceType type = ResourceType.BRICK;
		try {
			if(CanModelFacade.sole().canUseMonopoly(type) == false) {
				System.out.println("passed testCanUseMonopoly test when you already played dev card");
			} else {
				fail("failed testCanUseMonopoly test when you already played dev card");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when you already played dev card - model not created");
		}
	}
	
	//good output
	@Test
	public void testCanUseMonopoly6() {
		this.initModel("goodMonopoly.txt");
		ResourceType type = ResourceType.BRICK;
		try {
			if(CanModelFacade.sole().canUseMonopoly(type) == true) {
				System.out.println("passed testCanUseMonopoly test when meets parameters");
			} else {
				fail("failed testCanUseMonopoly test when when meets parameters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonopoly test when when meets parameters - model not created");
		}
	}
}
