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
import shared.models.board.hex.HexLocation;
import shared.models.exceptions.ModelAccessException;

public class CanUseSoldierTests {
	
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
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCanUseSoldier1() {
		HexLocation newRobberLocation = null;
		int playerIndex = 0;
		try {
			modelFacade.canUseSoldier(newRobberLocation, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseSoldier2() {
		HexLocation newRobberLocation = null;
		int playerIndex = 0;
		try {
			modelFacade.canUseSoldier(newRobberLocation, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseSoldier3() {
		HexLocation newRobberLocation = null;
		int playerIndex = 0;
		try {
			modelFacade.canUseSoldier(newRobberLocation, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseSoldier4() {
		HexLocation newRobberLocation = null;
		int playerIndex = 0;
		try {
			modelFacade.canUseSoldier(newRobberLocation, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseSoldier5() {
		HexLocation newRobberLocation = null;
		int playerIndex = 0;
		try {
			modelFacade.canUseSoldier(newRobberLocation, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanUseSoldier6() {
		HexLocation newRobberLocation = null;
		int playerIndex = 0;
		try {
			modelFacade.canUseSoldier(newRobberLocation, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
}
