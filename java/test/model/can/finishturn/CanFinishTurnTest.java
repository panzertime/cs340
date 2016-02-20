package model.can.finishturn;

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
import client.modelfacade.testing.TestingModelFacade;
import shared.model.exceptions.BadJSONException;

public class CanFinishTurnTest {
	
	@Before
	public void initFacades() {
		CanModelFacade.sole().setUserIndex(0);
		TestingModelFacade.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}

	public void initModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/jsonMap.txt");
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
	
	public void initNotTurnModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/notyourturn.txt");
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
	
	public void initRobModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/robjson.txt");
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
	
	//Your turn, playing
	@Test
	public void testCanFinishTurn4() {
		this.initModel();
		try {
			if(CanModelFacade.sole().canEndTurn() == true) {
				System.out.println("passed canFinishTurn test when playing and your turn");
			} else {
				fail("failed canFinishTurn test when playing and your turn");
			}
		} catch (NullPointerException e) {
			fail("failed canFinishTurn test when playing and your turn - unit model");
		}
	}
	
	//uninit model
	@Test
	public void testCanFinishTurn1() {
		try {
			CanModelFacade.sole().canEndTurn();
			fail("failed canFinishTurn test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed canFinishTurn test with uninit model");
		}
	}
	
	//not your turn
	@Test
	public void testCanFinishTurn2() {
		this.initNotTurnModel();
		try {
			if(CanModelFacade.sole().canEndTurn() == false) {
				System.out.println("passed canFinishTurn test when not your turn");
			} else {
				fail("failed canFinishTurn test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed canFinishTurn test when not your turn - unit model");
		}
	}
	
	//not in playing state
	@Test
	public void testCanFinishTurn3() {
		this.initRobModel();
		try {
			if(CanModelFacade.sole().canEndTurn() == false) {
				System.out.println("passed canFinishTurn test when robbing");
			} else {
				fail("failed canFinishTurn test when robbing");
			}
		} catch (NullPointerException e) {
			fail("failed canFinishTurn test when robbing - unit model");
		}
	}
}
