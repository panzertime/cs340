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
	
	//Test tests
	
	@Test
	public void testCanSendChatNoModel() {
		ModelFacade mf = new ModelFacade();
		try {
			mf.canSendChat();
			fail("Fail - canSendChat while not logged in");
		} catch (NullPointerException e) {
			System.out.println("Passed canSendChat while no game exists");
		}
	}

	//Good test
	@Test
	public void testCanSendChatLoggedIn() {
		try {
			initModel();
			if(modelFacade.canSendChat() == true) {
				System.out.println("Passed canSendChat while player is in a game");
			} else
			{
				fail("Failed canSendChat test while player was in a game");
			}
		} catch (NullPointerException e) {
			fail("can send chat - Error when accessing model");
		}
	}
}
