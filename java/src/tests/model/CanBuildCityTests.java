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
import shared.models.board.vertex.VertexLocation;
import shared.models.exceptions.ModelAccessException;

public class CanBuildCityTests {
	
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
	public void testCanBuildCity1() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildCity(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

	@Test
	public void testCanBuildCity2() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildCity(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildCity3() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildCity(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//Bad tests
	@Test
	public void testCanBuildCity4() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildCity(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildCity5() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildCity(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildCity6() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildCity(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
}