package model;
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
import client.modelfacade.ModelFacade;
import client.modelfacade.testing.TestingModelFacade;
import shared.model.exceptions.BadJSONException;

public class ModelTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		TestingModelFacade.sole();
		ClientPlayer.sole().setUserIndex(0);
	}
    
	//minimum model
	@Test
	public void initModel1() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/model/minjson.txt");
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
			
			if (!TestingModelFacade.sole().equalsJSON((JSONObject)jsonModel)) {
				fail("Current model does not match Minimal JSON model");
			} else {
				System.out.println("passed minimal JSON init test");
			}
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			fail("Error with JSON input with minimul options\n" +
					e.getMessage());
		}
	}
	
	//maximum model
	@Test
	public void initModel2() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/model/fulljson.txt");
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
			
			if (!TestingModelFacade.sole().equalsJSON((JSONObject)jsonModel)) {
				fail("Current model does not match full JSON model");
			} else {
				System.out.println("passed full JSON init test");
			}
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			fail("Error with JSON input with all options\n" +
					e.getMessage());
		}
	}
	
	//error model
	@Test
	public void initModel3() {
		boolean pass = false;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/model/badjson.txt");
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
			
		} catch (FileNotFoundException | ParseException e) {
			fail("Error with bad JSON input\n" +
					e.getMessage());
		} catch (BadJSONException e) {
			pass = true;
		}
		if(pass) {
			System.out.println("passed bad JSON init test");
		} else {
			fail("Did not catch error with bad JSON input\n");
		}
	}
}
