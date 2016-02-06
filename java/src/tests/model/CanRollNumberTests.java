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


public class CanRollNumberTests {
	
	ModelFacade modelFacade;

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
	
	public void initChatModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/rollingJson.txt");
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

	@Test
	public void testCanRollNumber1() {
		ModelFacade mf = new ModelFacade();
		try {
			mf.canRollNumber();
			fail("failed canRoll test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("Passed canRoll test with uninit model");
		}
	}
	
	@Test
	public void testCanRollNumber2() {
		try {
			this.initModel();
			if(modelFacade.canRollNumber() == false) {
				System.out.println("Passed canRoll test when not your turn");
			} else {
				fail("failed canRoll test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("Could not access model in canRoll test when it's not your turn");
		}
	}
	
	@Test
	public void testCanRollNumber3() {
		try {
			this.initChatModel();
			if(modelFacade.canRollNumber() == true) {
				System.out.println("Passed canRoll test when your turn");
			} else {
				fail("failed canRoll test when your turn");
			}
		} catch (NullPointerException e) {
			fail("Could not access model in canRoll test when it's your turn");
		}
	}
}
