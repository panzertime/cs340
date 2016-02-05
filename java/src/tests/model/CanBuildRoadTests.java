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
import shared.models.board.edge.EdgeLocation;
import shared.models.exceptions.ModelAccessException;

public class CanBuildRoadTests {
	
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

	//Good tests
	@Test
	public void testCanBuildRoad1() {
		EdgeLocation edgeLoc = null;
		try {
			modelFacade.canBuildRoad(edgeLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildRoad2() {
		EdgeLocation edgeLoc = null;
		try {
			modelFacade.canBuildRoad(edgeLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildRoad3() {
		EdgeLocation edgeLoc = null;
		try {
			modelFacade.canBuildRoad(edgeLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//Bad tests
	@Test
	public void testCanBuildRoad4() {
		EdgeLocation edgeLoc = null;
		try {
			modelFacade.canBuildRoad(edgeLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildRoad5() {
		EdgeLocation edgeLoc = null;
		try {
			modelFacade.canBuildRoad(edgeLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildRoad6() {
		EdgeLocation edgeLoc = null;
		try {
			modelFacade.canBuildRoad(edgeLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
}