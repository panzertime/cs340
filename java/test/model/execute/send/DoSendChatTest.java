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
import org.junit.Test;

import shared.model.Model;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.ViolatedPreconditionException;

public class DoSendChatTest {

	
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
			model.doSendChat("message", 0);
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("Failed doSendChat with basic message and valid index");
		}
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
			model.doSendChat("message", 99);
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doSendChat with basic message and invalid index");
		}
		if(!model.equalsJSON(getJSONFrom("beforeMessage.json"))) {
			fail("Failed doSendChat with basic message and invalid index");
		}
	}
	
	@Test
	public void testDoSendChat3() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeMessage.json"));
			model.doSendChat("", 0);
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doSendChat with empty message and valid index");
		}
		if(!model.equalsJSON(getJSONFrom("beforeMessage.json"))) {
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
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doSendChat with null message and valid index");
		}
		if(!model.equalsJSON(getJSONFrom("beforeMessage.json"))) {
			fail("Failed doSendChat with null message and valid index");
		}
	}
	
	@Test
	public void testDoSendChat5() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeMessage.json"));
			model.doSendChat("", 99);
		} catch (BadJSONException e) {
			fail("Failed doSendChat while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doSendChat with empty message and invalid index");
		}
		if(!model.equalsJSON(getJSONFrom("beforeMessage.json"))) {
			fail("Failed doSendChat with empty message and invalid index");
		}
	}
}
