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
	
	//Good tests
	@Test
	public void testCanUseRoadBuilder1() {
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
	
	//Bad tests
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

}
