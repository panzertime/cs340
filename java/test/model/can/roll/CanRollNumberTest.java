package model.can.roll;

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


public class CanRollNumberTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/roll/" + file);
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

	//unit model
	@Test
	public void testCanRollNumber1() {
		try {
			CanModelFacade.sole().canRollDice();
			fail("failed canRoll test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed canRoll test with uninit model");
		}
	}
	
	//not your turn
	@Test
	public void testCanRollNumber2() {
		try {
			this.initModel("noTurn.txt");
			if(CanModelFacade.sole().canRollDice() == false) {
				System.out.println("passed canRoll test when not your turn");
			} else {
				fail("failed canRoll test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("Could not access model in canRoll test when it's not your turn");
		}
	}
	
	//client model is rolling, amd your turn
	@Test
	public void testCanRollNumber3() {
		try {
			this.initModel("good.txt");
			if(CanModelFacade.sole().canRollDice() == true) {
				System.out.println("passed canRoll test when your turn");
			} else {
				fail("failed canRoll test when your turn");
			}
		} catch (NullPointerException e) {
			fail("Could not access model in canRoll test when it's your turn");
		}
	}
	
	//not rolling
	@Test
	public void testCanRollNumber4() {
		try {
			this.initModel("noRolling.txt");
			if(CanModelFacade.sole().canRollDice() == false) {
				System.out.println("passed canRoll test when not rolling");
			} else {
				fail("failed canRoll test when not rolling");
			}
		} catch (NullPointerException e) {
			fail("Could not access model in canRoll test when not rolling");
		}
	}
}
