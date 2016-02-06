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
import shared.models.exceptions.ModelAccessException;

public class CanFinishTurnTests {
	
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
	
	@Test
	public void testCanFinishTurn1() {
		ModelFacade mf = new ModelFacade();
		try {
			mf.canFinishTurn();
			fail("failed canFinishTurn test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("Passed canRoll test with uninit model");
		}
	}
	
	@Test
	public void testCanFinishTurn2() {
		this.initNotTurnModel();
		try {
			if(modelFacade.canFinishTurn() == false) {
				System.out.println("Passed canFinishTurn test when not your turn");
			} else {
				fail("failed canFinishTurn test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed canFinishTurn test when not your turn - unit model");
		}
	}
	
	@Test
	public void testCanFinishTurn3() {
		this.initRobModel();
		try {
			if(modelFacade.canFinishTurn() == false) {
				System.out.println("Passed canFinishTurn test when robbing");
			} else {
				fail("failed canFinishTurn test when robbing");
			}
		} catch (NullPointerException e) {
			fail("failed canFinishTurn test when robbing - unit model");
		}
	}

	@Test
	public void testCanFinishTurn4() {
		this.initModel();
		try {
			if(modelFacade.canFinishTurn() == true) {
				System.out.println("Passed canFinishTurn test when playing and your turn");
			} else {
				fail("failed canFinishTurn test when playing and your turn");
			}
		} catch (NullPointerException e) {
			fail("failed canFinishTurn test when playing and your turn - unit model");
		}
	}
}
