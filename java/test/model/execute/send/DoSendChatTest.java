package model.execute.send;

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
import client.modelfacade.testing.TestingModelFacade;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class DoSendChatTest {
	
	@Before
	public void initFacades() {
		ClientPlayer.sole().setUserIndex(0);
		TestingModelFacade.sole().emptyModel();
	}
	
	public JSONObject getJSONFrom(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/execute/send/" + file);
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
			
			return (JSONObject) parser.parse(x);
			
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void testDoSendChat1() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeMessage.json"));
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		}
		model.doSendChat("message", 0);
		if(model.equalsJSON(getJSONFrom("afterMessage.json"))) {
			System.out.println("passed doSendChat with basic message and valid index");
		} else {
			fail("Failed doSendChat with basic message and valid index");
		}
	}
	
	@Test
	public void testDoSendChat2() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeMessage.json"));
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		}
		model.doSendChat("message", 99);
		if(model.equalsJSON(getJSONFrom("beforeMessage.json"))) {
			System.out.println("passed doSendChat with basic message and invalid index");
		} else {
			fail("Failed doSendChat with basic message and invalid index");
		}
	}
	
	@Test
	public void testDoSendChat3() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeMessage.json"));
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		}
		model.doSendChat("", 0);
		if(model.equalsJSON(getJSONFrom("afterEmptyMessage.json"))) {
			System.out.println("passed doSendChat with empty message and valid index");
		} else {
			fail("Failed doSendChat with empty message and valid index");
		}
	}
	
	@Test
	public void testDoSendChat4() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeMessage.json"));
			model.doSendChat(null, 0);
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		} catch (NullPointerException e) {
			System.out.println("passed doSendChat with null message and valid index");
			return;
		}
		fail("Failed doSendChat with null message and valid index");
	}
	
	@Test
	public void testDoSendChat5() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeMessage.json"));
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		}
		model.doSendChat("", 99);
		if(model.equalsJSON(getJSONFrom("beforeMessage.json"))) {
			System.out.println("passed doSendChat with empty message and invalid index");
		} else {
			fail("Failed doSendChat with empty message and invalid index");
		}
	}
}
