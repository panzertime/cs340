package model.can.use.monument;

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

import client.modelfacade.ModelFacade;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.ModelAccessException;

public class CanUseMonumentTest {
	
	ModelFacade modelFacade;

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/use/monument/" + file);
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

	//game not created
	@Test
	public void testCanUseMonument1() {
		ModelFacade mf = new ModelFacade();
		try {
			mf.canUseMonument();
			fail("failed testCanUseMonument test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanUseMonument test with uninit model");
		}
	}
	
	//not your turn
	@Test
	public void testCanUseMonument2() {
		this.initModel("notTurn.txt");
		try {
			if(modelFacade.canUseMonument() == false) {
				System.out.println("passed testCanUseMonument test when not your turn");
			} else {
				fail("failed testCanUseMonument test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonument test when not your turn - model not created");
		}
	}
	
	//not playing state
	@Test
	public void testCanUseMonument3() {
		this.initModel("notPlaying.txt");
		try {
			if(modelFacade.canUseMonument() == false) {
				System.out.println("passed testCanUseMonument test when not playing state");
			} else {
				fail("failed testCanUseMonument test when not playing state");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonument test when not playing state - model not created");
		}
	}
	
	//Dont have monument
	@Test
	public void testCanUseMonument4() {
		this.initModel("noHave.txt");
		try {
			if(modelFacade.canUseMonument() == false) {
				System.out.println("passed testCanUseMonument test when user does not have card");
			} else {
				fail("failed testCanUseMonument test when user does not have card");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonument test when user does not have card - model not created");
		}
	}
	
	//already played a dev card
	@Test
	public void testCanUseMonument5() {
		this.initModel("alreadyDeved.txt");
		try {
			if(modelFacade.canUseMonument() == true) {
				System.out.println("passed testCanUseMonument test when user already played dev card");
			} else {
				fail("failed testCanUseMonument test when user already played dev card");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonument test when user already played dev card - model not created");
		}
	}
	
	// dont have enough points to win
	@Test
	public void testCanUseMonument6() {
		this.initModel("notEnoughPoints.txt");
		try {
			if(modelFacade.canUseMonument() == false) {
				System.out.println("passed testCanUseMonument test when user does not have enough points");
			} else {
				fail("failed testCanUseMonument test when user does not have enough points");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonument test when user does not have enough points - model not created");
		}
	}
	
	// good
	@Test
	public void testCanUseMonument7() {
		this.initModel("goodMonument.txt");
		try {
			if(modelFacade.canUseMonument() == true) {
				System.out.println("passed testCanUseMonument test when meets parameters");
			} else {
				fail("failed testCanUseMonument test when meets paramaters");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonument test when meets parameters - model not created");
		}
	}
}
