package model.execute.roll;

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


public class DoRollNumberTest {

	public JSONObject getJSONFrom(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/execute/roll/" + file);
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
	public void testDoRollNumber1() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("rolling.json"));
			model.doRollNumber(6, 0);
		} catch (BadJSONException e) {
			fail("Failed doRollNumber while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("Failed doRollNumber with 6 and valid index");
		}
		if(model.equalsJSON(getJSONFrom("rolled6.json"))) {
			System.out.println("passed doRollNumber with 6 and valid index");
		} else {
			fail("Failed doRollNumberChat with 6 and valid index");
		}
	}
	
	@Test
	public void testDoRollNumber2() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("rolling.json"));
			model.doRollNumber(7, 0);
		} catch (BadJSONException e) {
			fail("Failed doRollNumber while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("Failed doRollNumber with 7 and valid index");
		}
		if(model.equalsJSON(getJSONFrom("rolled7.json"))) {
			System.out.println("passed doRollNumber with 7 and valid index");
		} else {
			fail("Failed doRollNumberChat with 7 and valid index");
		}
	}
	
	@Test
	public void testDoRollNumber3() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("rolling.json"));
			model.doRollNumber(13, 0);
		} catch (BadJSONException e) {
			fail("Failed doRollNumber while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doRollNumber with 13 and valid index");
		}
		if(!model.equalsJSON(getJSONFrom("rolling.json"))) {
			fail("Failed doRollNumberChat with 13 and valid index");
		}
	}
	
	@Test
	public void testDoRollNumber4() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("rolling.json"));
			model.doRollNumber(1, 0);
		} catch (BadJSONException e) {
			fail("Failed doRollNumber while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doRollNumber with 1 and valid index");
		}
		if(!model.equalsJSON(getJSONFrom("rolling.json"))) {
			fail("Failed doRollNumberChat with 1 and valid index");
		}
	}
	
	@Test
	public void testDoRollNumber5() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("rolling.json"));
			model.doRollNumber(6, 99);
		} catch (BadJSONException e) {
			fail("Failed doRollNumber while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doRollNumber with 6 and invalid index");
		}
		if(!model.equalsJSON(getJSONFrom("rolling.json"))) {
			fail("Failed doRollNumberChat with 6 and invalid index");
		}
	}
	
	@Test
	public void testDoRollNumber6() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("notYourTurn.json"));
			model.doRollNumber(6, 0);
		} catch (BadJSONException e) {
			fail("Failed doRollNumber while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doRollNumber while not your turn");
		}
		if(!model.equalsJSON(getJSONFrom("notYourTurn.json"))) {
			fail("Failed doRollNumberChat while not your turn");
		}
	}
	
	@Test
	public void testDoRollNumber7() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("notRolling.json"));
			model.doRollNumber(6, 0);
		} catch (BadJSONException e) {
			fail("Failed doRollNumber while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doRollNumber while not rolling");
		}
		if(!model.equalsJSON(getJSONFrom("notRolling.json"))) {
			fail("Failed doRollNumberChat while not rolling");
		}
	}
}
