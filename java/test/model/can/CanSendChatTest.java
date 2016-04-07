package model.can;

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

public class CanSendChatTest {
	
	@Before
	public void initFacades() {
		//CanModelFacade.sole().setUserIndex(0);
		//TestingModelFacade.sole().setUserIndex(0);
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}
	
	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/" + file);
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
	
	//Test tests
	
	@Test
	public void testCanSendChatNoModel() {
		try {
			CanModelFacade.sole().canSendChat();
			fail("Fail - canSendChat while not logged in");
		} catch (NullPointerException e) {
			System.out.println("passed canSendChat while no game exists");
		}
	}
	
	
	//not turn
	@Test
	public void testCanSendChatLoggedIn1() {
		try {
			initModel("notyourturn.txt");
			if(CanModelFacade.sole().canSendChat() == true) {
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
			if(CanModelFacade.sole().canSendChat() == true) {
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
