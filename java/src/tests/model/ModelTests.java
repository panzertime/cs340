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
import org.junit.Test;

import shared.models.ModelFacade;

public class ModelTests {

	//minimum model
	@Test
	public void initModel1() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/minjson.txt");
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
			
			ModelFacade modelFacade = new ModelFacade((JSONObject) jsonModel);
			
			if (!correctModel1(modelFacade)) {
				fail("Current model does not match Minimal JSON model");
			}
			System.out.println("Model passed minimal JSON init test");
		} catch (FileNotFoundException | ParseException e) {
			fail("Error with JSON input with minimul options\n");
		}
	}
	
	//maximum model
	@Test
	public void initModel2() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/fulljson.txt");
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
			
			ModelFacade modelFacade = new ModelFacade((JSONObject) jsonModel);
			if (!correctModel2(modelFacade)) {
				fail("Current model does not match full JSON model");
			}
			System.out.println("Model passed full JSON init test");
		} catch (FileNotFoundException | ParseException e) {
			fail("Error with JSON input with all options\n");
		}
	}
	
	//error model
	@Test
	public void initModel3() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/badjson.txt");
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
			
			ModelFacade modelFacade = new ModelFacade((JSONObject) jsonModel);
		} catch (FileNotFoundException | ParseException e) {
			fail("Error with bad JSON input\n");
		}
		//TODO Some catch here that says {
		// System.out.println("Model passed bad JSON init test");
		//}
		fail("Did not catch error with bad JSON input\n");
	}
	
	private boolean correctModel1(ModelFacade modelFacade) {
		//TODO: Individual element check
		return false;
	}

	private boolean correctModel2(ModelFacade modelFacade) {
		//TODO: Individual element check
		return false;
	}
}
