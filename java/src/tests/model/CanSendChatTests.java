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

import shared.models.GameModel;
import shared.models.ModelFacade;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.ModelAccessException;

public class CanSendChatTests {
	
	ModelFacade modelFacade;
	
	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/" + file);
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
	
	//Test tests
	
	@Test
	public void testCanSendChatNoModel() {
		ModelFacade mf = new ModelFacade();
		try {
			mf.canSendChat();
			fail("Fail - canSendChat while not logged in");
		} catch (NullPointerException e) {
			System.out.println("passed canSendChat while no game exists");
		}
	}

	//Good test
	@Test
	public void testCanSendChatLoggedIn() {
		try {
			initModel("jsonMap.txt");
			if(modelFacade.canSendChat() == true) {
				System.out.println("passed canSendChat while player is in a normal game");
			} else
			{
				fail("Failed canSendChat test while player was in a normal game");
			}
		} catch (NullPointerException e) {
			fail("can send chat - Error when accessing model");
		}
	}
	
	//not turn
	@Test
	public void testCanSendChatLoggedIn1() {
		try {
			initModel("notyourturn.txt");
			if(modelFacade.canSendChat() == true) {
				System.out.println("passed canSendChat while player is in a normal game and it's not their turn");
			} else
			{
				fail("Failed canSendChat test while player was in a normal game and it's not their turn");
			}
		} catch (NullPointerException e) {
			fail("can send chat - Error when accessing model");
		}
	}
	
	//not playing
	@Test
	public void testCanSendChatLoggedIn2() {
		try {
			initModel("robjson.txt");
			if(modelFacade.canSendChat() == true) {
				System.out.println("passed canSendChat while player is in a normal game and not in playing mode");
			} else
			{
				fail("Failed canSendChat test while player was in a normal game and not in playing mode");
			}
		} catch (NullPointerException e) {
			fail("can send chat - Error when accessing model");
		}
	}
}
