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
import shared.models.board.edge.EdgeLocation;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.ModelAccessException;
import shared.models.hand.ResourceType;

public class CanUseRoadBuilderTests {
	
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
	
	public void initNotTurnModel() {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/src/tests/model/notyourturn.txt");
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
	
	//not turn
	@Test
	public void testCanUseRoadBuilder1() {
		EdgeLocation one = null;
		EdgeLocation two = null;
		this.initNotTurnModel();
		try {
			if(modelFacade.canUseRoadBuilder(one, two) == false) {
				System.out.println("pass testCanUseRoadBuilder test when not playing");
			} else {
				fail("fail testCanUseRoadBuilder test when not playing");
			}
		} catch (NullPointerException e) {
			fail("fail testCanUseRoadBuilder test when not playing - could not access model");
		}
	}
	
	//client is playing
	@Test
	public void testCanUseRoadBuilder2() {
		EdgeLocation one = null;
		EdgeLocation two = null;
		try {
			modelFacade.canUseRoadBuilder(one, two);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//first road location not on your own road
	@Test
	public void testCanUseRoadBuilder3() {
		EdgeLocation one = null;
		EdgeLocation two = null;
		try {
			modelFacade.canUseRoadBuilder(one, two);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//second road location not connected
	@Test
	public void testCanUseRoadBuilder4() {
		EdgeLocation one = null;
		EdgeLocation two = null;
		try {
			modelFacade.canUseRoadBuilder(one, two);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//road location on water
	@Test
	public void testCanUseRoadBuilder5() {
		EdgeLocation one = null;
		EdgeLocation two = null;
		try {
			modelFacade.canUseRoadBuilder(one, two);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//user only has 1 road
	@Test
	public void testCanUseRoadBuilder6() {
		EdgeLocation one = null;
		EdgeLocation two = null;
		try {
			modelFacade.canUseRoadBuilder(one, two);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//works!
	@Test
	public void testCanUseRoadBuilder7() {
		EdgeLocation one = null;
		EdgeLocation two = null;
		try {
			modelFacade.canUseRoadBuilder(one, two);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
}
