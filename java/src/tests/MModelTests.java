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
import org.junit.Test;

import shared.models.ModelFacade;
import shared.models.exceptions.BadJSONException;

public class MModelTests {
	


	
	//maximum model
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/fulljson.txt");
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
			
			ModelFacade modelFacade = new ModelFacade((JSONObject) jsonModel, 0);
			if (!modelFacade.equalsJSON((JSONObject)jsonModel)) {
				fail("Current model does not match full JSON model");
			} else {
				System.out.println("Model passed full JSON init test");
			}
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			fail("Error with JSON input with all options\n" +
					e.getMessage());
		}
	}
}

