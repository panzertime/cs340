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

public class CanBuildSettlementTests {
	
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
	
	@Test
	public void testCanBuildSettlement1() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildSettlement(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildSettlement2() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildSettlement(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildSettlement3() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildSettlement(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	//Bad tests
	@Test
	public void testCanBuildSettlement4() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildSettlement(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	
	@Test
	public void testCanBuildSettlement5() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildSettlement(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
	@Test
	public void testCanBuildSettlement6() {
		VertexLocation vertLoc = null;
		try {
			modelFacade.canBuildSettlement(vertLoc);
		} catch (ModelAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
