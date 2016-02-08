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
	
	private static ModelFacade modelFacade;


	public static void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/monumenttests/" + file);
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

	
	//maximum model
	public static void main(String[] args) {
		initModel("noHave.txt");
		try {
			if(modelFacade.canUseMonument() == false) {
				System.out.println("passed testCanUseMonument test when user does not have card");
			} else {
				fail("failed testCanUseMonument test when user does not have card");
			}
		} catch (NullPointerException e) {
			fail("failed testCanUseMonument test when user does not have card - model not created");
		}
	}
}

