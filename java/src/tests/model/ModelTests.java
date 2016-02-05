package tests.model;
import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import shared.models.ModelFacade;

public class ModelTests {
	
	ModelFacade modelFacade;

	//minimum model
	@Test
	private void initModel1() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/minimumjson.txt");
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
			
			modelFacade = new ModelFacade(jsonModel);
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	//maximum model
	@Test
	private void initModel2() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/maxjson.txt");
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
			
			modelFacade = new ModelFacade(jsonModel);
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	//error model
	@Test
	private void initModel3() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/badjson.txt");
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
			
			modelFacade = new ModelFacade(jsonModel);
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private boolean correctModel1() {
		//TODO: Individual element check
		return false;
	}

	private boolean correctModel2() {
		//TODO: Individual element check
		return false;
	}
}
