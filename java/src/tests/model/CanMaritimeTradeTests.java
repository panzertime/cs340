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

import shared.models.ModelFacade;
import shared.models.exceptions.BadJSONException;
import shared.models.hand.ResourceType;

public class CanMaritimeTradeTests {
	
	ModelFacade modelFacade;

	@Before
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
	
	//Good tests
	@Test
	public void testCanMaritimeTrade1() {
		int ratio = 0;
		ResourceType inputType = null;
		ResourceType outputType = null;
		try {
			modelFacade.canMaritimeTrade(ratio, inputType, outputType);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

	@Test
	public void testCanMaritimeTrade2() {
		int ratio = 0;
		ResourceType inputType = null;
		ResourceType outputType = null;
		try {
			modelFacade.canMaritimeTrade(ratio, inputType, outputType);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanMaritimeTrade3() {
		int ratio = 0;
		ResourceType inputType = null;
		ResourceType outputType = null;
		try {
			modelFacade.canMaritimeTrade(ratio, inputType, outputType);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//Bad tests
	@Test
	public void testCanMaritimeTrade4() {
		int ratio = 0;
		ResourceType inputType = null;
		ResourceType outputType = null;
		try {
			modelFacade.canMaritimeTrade(ratio, inputType, outputType);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanMaritimeTrade5() {
		int ratio = 0;
		ResourceType inputType = null;
		ResourceType outputType = null;
		try {
			modelFacade.canMaritimeTrade(ratio, inputType, outputType);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanMaritimeTrade6() {
		int ratio = 0;
		ResourceType inputType = null;
		ResourceType outputType = null;
		try {
			modelFacade.canMaritimeTrade(ratio, inputType, outputType);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
}
