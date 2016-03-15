package server.model;

import static org.junit.Assert.*;

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

public class ToJSON {
	
	public JSONObject getJSONFromFile(String filename) {
		JSONObject result = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/model/" + filename + ".txt");
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
			
			result = (JSONObject) parser.parse(x);
			
		} catch (FileNotFoundException | ParseException e) {
			fail("Error with JSON input\n" +
					e.getMessage());
		}
		
		return result;
	}
	
	//min JSON
	@Test
	public void testToJSON1() {
		JSONObject minJSON = getJSONFromFile("minjson");
		Model model;
		try {
			model = new Model(minJSON);
			JSONObject modelToJSON = model.toJSON();
			System.out.println(modelToJSON);
			System.out.println(minJSON);
			if(minJSON.equals(modelToJSON)) {
				System.out.println("Passed test modelToJSON with minimal"
						+ "information.");
			} else {
				fail("Failed test modelToJSON with minimal "
						+ "information.");
			}
		} catch (BadJSONException e) {
			fail("Failed minimum toJSON test - bad input file");
		}
	}
	
	//maximum model
	@Test
	public void testToJSON2() {
		JSONObject maxJSON = getJSONFromFile("fulljson");
		Model model;
		try {
			model = new Model(maxJSON);
			JSONObject modelToJSON = model.toJSON();
			if(maxJSON.equals(modelToJSON)) {
				System.out.println("Passed test modelToJSON with maximum "
						+ "information.");
			} else {
				fail("Failed test modelToJSON with maximum model"
						+ "information.");
			}
		} catch (BadJSONException e) {
			fail("Failed maximum toJSON test - bad input file");
		}
	}
}
