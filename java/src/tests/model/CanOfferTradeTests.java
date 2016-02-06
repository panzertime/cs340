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
import shared.models.exceptions.ModelAccessException;

public class CanOfferTradeTests {
	
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
			
			modelFacade = new ModelFacade((JSONObject) jsonModel);
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCanOfferTrade1() {
		Map<String, Object> resource = null;
		int amount = 0;
		try {
			modelFacade.canOfferTrade(resource, amount);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanOfferTrade2() {
		Map<String, Object> resource = null;
		int amount = 0;
		try {
			modelFacade.canOfferTrade(resource, amount);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanOfferTrade3() {
		Map<String, Object> resource = null;
		int amount = 0;
		try {
			modelFacade.canOfferTrade(resource, amount);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//Bad tests
	@Test
	public void testCanOfferTrade4() {
		Map<String, Object> resource = null;
		int amount = 0;
		try {
			modelFacade.canOfferTrade(resource, amount);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanOfferTrade5() {
		Map<String, Object> resource = null;
		int amount = 0;
		try {
			modelFacade.canOfferTrade(resource, amount);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanOfferTrade6() {
		Map<String, Object> resource = null;
		int amount = 0;
		try {
			modelFacade.canOfferTrade(resource, amount);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
