package model.execute.build.city;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import shared.model.Model;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.ViolatedPreconditionException;

public class DoBuildCityTest {
		
	public JSONObject getJSONFrom(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/execute/build/city/" + file);
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
			return (JSONObject) parser.parse(x);
			
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void testDoBuildCity1() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,0), VertexDirection.SouthWest);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildCity(vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildCity while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("Failed doBuildCity with valid location");
		}
		if(model.equalsJSON(getJSONFrom("placedCity.json"))) {
			System.out.println("passed doBuildCity with valid location");
		} else {
			fail("Failed doBuildCity with valid location");
		}
	}
	
	@Test
	public void testDoBuildCity2() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,-1), VertexDirection.SouthWest);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildCity(vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildCity while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildCity with taken location");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("Failed doBuildCity with taken location");
		}
	}
	
	@Test
	public void testDoBuildCity3() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildCity(vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildCity while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildCity with no settlment at location");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("Failed doBuildCity with no settlment at location");
		}
	}
	
	@Test
	public void testDoBuildCity4() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("notEnoughResources.json"));
			model.doBuildCity(vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildCity while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildCity with not enough resources");
		}
		if(!model.equalsJSON(getJSONFrom("notEnoughResources.json"))) {
			fail("Failed doBuildCity with not enough resources");
		}
	}
	
	@Test
	public void testDoBuildCity5() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("setup.json"));
			model.doBuildCity(vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildCity while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildCity while in setup");
		}
		if(!model.equalsJSON(getJSONFrom("setup.json"))) {
			fail("Failed doBuildCity while in setup");
		}
	}
	
	@Test
	public void testDoBuildCity6() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("robbing.json"));
			model.doBuildCity(vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildCity while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildCity while robbing");
		}
		if(!model.equalsJSON(getJSONFrom("robbing.json"))) {
			fail("Failed doBuildCity while robbing");
		}
	}
	
	@Test
	public void testDoBuildCity7() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("robbing.json"));
			model.doBuildCity(vertLoc, 99);
		} catch (BadJSONException e) {
			fail("Failed doBuildCity while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildCity with invalid index");
		}
		if(!model.equalsJSON(getJSONFrom("robbing.json"))) {
			fail("Failed doBuildCity with invalid index");
		}
	}
}
