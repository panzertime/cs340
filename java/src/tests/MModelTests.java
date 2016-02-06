package tests;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import shared.models.ModelFacade;
import shared.models.exceptions.BadJSONException;

public class MModelTests {
	
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/badjson.txt");
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
			
			ModelFacade modelFacade = new ModelFacade((JSONObject) jsonModel);
		} catch (FileNotFoundException | ParseException e) {
			fail("Error with bad JSON input\n" +
					e.getMessage());
		} catch (BadJSONException e) {
			System.out.println("Model passed bad JSON init test");
		}
		System.err.println("Did not catch error with bad JSON input\n");
	}
}

