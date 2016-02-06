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
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.ModelAccessException;

public class CanPlaceRobberTests {
	
	ModelFacade modelFacade;

	public void initRobModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/robjson.txt");
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
	
	//BAD input
	@Test
	public void testCanPlaceRobber1() {
		HexLocation hexLoc = null;
		int playerIndex = 0;
		ModelFacade mf = new ModelFacade();
		try {
			mf.canPlaceRobber(hexLoc, playerIndex);
			fail("failed canPlaceRobber test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("Passed canPlaceRobber test with uninit model");
		}
	}
	
	@Test
	public void testCanPlaceRobber2() {
		this.initRobModel();
		HexLocation hexLoc = null;
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test with uninit hex loc");
			} else {
				fail("failed canPlaceRobber test with uninit hex");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with uninit hex - unit model");
		}
		
	}
	
	@Test
	public void testCanPlaceRobber3() {
		this.initRobModel();
		HexLocation hexLoc = new HexLocation(0,-2);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test with unmoved robber");
			} else {
				fail("failed canPlaceRobber test with unmoved robber");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with unmoved robber - unit model");
		}
	}

	@Test
	public void testCanPlaceRobber4() {
		this.initRobModel();
		HexLocation hexLoc = new HexLocation(0,-3);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber on water test");
			} else {
				fail("failed canPlaceRobber on water test");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber on water test - uninit model");
		}
	}
	
	@Test
	public void testCanPlaceRobber5() {
		this.initRobModel();
		HexLocation hexLoc = new HexLocation(56,-30);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == false) {
				System.out.println("Passed canPlaceRobber test with bizzare location");
			} else {
				fail("failed canPlaceRobber test with bizzare location");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with bizzare location - uninit model");
		}
	}
	
	@Test
	public void testCanPlaceRobber6() {
		this.initRobModel();
		HexLocation hexLoc = new HexLocation(0,-1);
		int playerIndex = -1;
		try {
			if(modelFacade.canPlaceRobber(hexLoc, playerIndex) == true) {
				System.out.println("Passed canPlaceRobber test with good location");
			} else {
				fail("failed canPlaceRobber test with good location");
			}
		} catch (NullPointerException e) {
			fail("failed canPlaceRobber test with good location - uninit model");
		}
	}
}
