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
import org.junit.Before;
import org.junit.Test;

import shared.models.ModelFacade;
import shared.models.exceptions.ModelAccessException;
import shared.models.hand.ResourceType;

public class CanUseYearOfPlentyTests {
	
	ModelFacade modelFacade;

	@Before
	private void initModel() {
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
			
			modelFacade = new ModelFacade(jsonModel);
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	//Good Tests
	@Test
	public void testCanUseYearOfPlenty1() {
		ResourceType one = null;
		ResourceType two = null;
		try {
			modelFacade.canUseYearOfPlenty(one, two);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseYearOfPlenty2() {
		ResourceType one = null;
		ResourceType two = null;
		try {
			modelFacade.canUseYearOfPlenty(one, two);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseYearOfPlenty3() {
		ResourceType one = null;
		ResourceType two = null;
		try {
			modelFacade.canUseYearOfPlenty(one, two);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//Bad Tests
	@Test
	public void testCanUseYearOfPlenty4() {
		ResourceType one = null;
		ResourceType two = null;
		try {
			modelFacade.canUseYearOfPlenty(one, two);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseYearOfPlenty5() {
		ResourceType one = null;
		ResourceType two = null;
		try {
			modelFacade.canUseYearOfPlenty(one, two);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseYearOfPlenty6() {
		ResourceType one = null;
		ResourceType two = null;
		try {
			modelFacade.canUseYearOfPlenty(one, two);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
