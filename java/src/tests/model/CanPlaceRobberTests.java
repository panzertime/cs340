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

public class CanPlaceRobberTests {
	
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
	
	//Good tests
	@Test
	public void testCanPlaceRobber1() {
		HexLocation hexLoc = null;
		int playerIndex = 0;
		try {
			modelFacade.canPlaceRobber(hexLoc, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanPlaceRobber2() {
		HexLocation hexLoc = null;
		int playerIndex = 0;
		try {
			modelFacade.canPlaceRobber(hexLoc, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanPlaceRobber3() {
		HexLocation hexLoc = null;
		int playerIndex = 0;
		try {
			modelFacade.canPlaceRobber(hexLoc, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//Bad tests
	@Test
	public void testCanPlaceRobber4() {
		HexLocation hexLoc = null;
		int playerIndex = 0;
		try {
			modelFacade.canPlaceRobber(hexLoc, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanPlaceRobber5() {
		HexLocation hexLoc = null;
		int playerIndex = 0;
		try {
			modelFacade.canPlaceRobber(hexLoc, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanPlaceRobber6() {
		HexLocation hexLoc = null;
		int playerIndex = 0;
		try {
			modelFacade.canPlaceRobber(hexLoc, playerIndex);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
}
