package model.execute.build.settlement;

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

public class DoBuildSettlementTest {
	
	public JSONObject getJSONFrom(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/execute/build/settlement/" + file);
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
	public void testDoBuildSettlement1() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,0), VertexDirection.SouthWest);
		Model model = null;
		try {
			model = new Model(getJSONFrom("setup-firstSettlement.json"));
			model.doBuildSettlement(true, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("Failed doBuildSettlement while in first setup");
		}
		if(model.equalsJSON(getJSONFrom("placed-firstSettlement.json"))) {
			System.out.println("passed doBuildSettlement while in first setup");
		} else {
			fail("Failed doBuildSettlement while in first setup");
		}
	}
	
	@Test
	public void testDoBuildSettlement2() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.SouthWest);
		Model model = null;
		try {
			model = new Model(getJSONFrom("setup-secondSettlement.json"));
			model.doBuildSettlement(true, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("Failed doBuildSettlement while in second setup");
		}
		if(model.equalsJSON(getJSONFrom("placed-secondSettlement.json"))) {
			System.out.println("passed doBuildSettlement while in second setup");
		} else {
			fail("Failed doBuildSettlement while in second setup");
		}
	}
	
	@Test
	public void testDoBuildSettlement3() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.West);
		Model model = null;
		try {
			model = new Model(getJSONFrom("setup-secondSettlement.json"));
			model.doBuildSettlement(true, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement while in second setup and too close");
		}
		if(!model.equalsJSON(getJSONFrom("setup-secondSettlement.json"))) {
			fail("Failed doBuildSettlement while in second setup and too close");
		}
	}
	
	@Test
	public void testDoBuildSettlement4() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,3), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("setup-secondSettlement.json"));
			model.doBuildSettlement(true, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement while in second setup and on water");
		}
		if(!model.equalsJSON(getJSONFrom("setup-secondSettlement.json"))) {
			fail("Failed doBuildSettlement while in second setup and on water");
		}
	}
	
	@Test
	public void testDoBuildSettlement5() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.West);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildSettlement(false, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement while playing and too close");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("Failed doBuildSettlement while playing and too close");
		}
	}
	
	@Test
	public void testDoBuildSettlement6() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,3), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildSettlement(false, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement while playing and on water");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("Failed doBuildSettlement while playing and on water");
		}
	}
	
	@Test
	public void testDoBuildSettlement7() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("robbing.json"));
			model.doBuildSettlement(false, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement while robbing");
		}
		if(!model.equalsJSON(getJSONFrom("robbing.json"))) {
			fail("Failed doBuildSettlement while in second robbing");
		}
	}
	
	@Test
	public void testDoBuildSettlement8() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("notYourTurn.json"));
			model.doBuildSettlement(false, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement while not your turn");
		}
		if(!model.equalsJSON(getJSONFrom("notYourTurn.json"))) {
			fail("Failed doBuildSettlement while not your turn");
		}
	}
	
	@Test
	public void testDoBuildSettlement9() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("notEnoughResources.json"));
			model.doBuildSettlement(false, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement with not enough resources");
		}
		if(!model.equalsJSON(getJSONFrom("notEnoughResources.json"))) {
			fail("Failed doBuildSettlement with not enough resources");
		}
	}
	
	@Test
	public void testDoBuildSettlement10() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildSettlement(false, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("Failed doBuildSettlement while playing");
		}
		if(!model.equalsJSON(getJSONFrom("placedSettlement.json"))) {
			System.out.println("passed doBuildSettlement while playing");
		} else {
			fail("Failed doBuildSettlement while playing");
		}
	}
	
	@Test
	public void testDoBuildSettlement11() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,0), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildSettlement(false, vertLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement with taken location");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("Failed doBuildSettlement with taken location");
		}
	}
	
	@Test
	public void testDoBuildSettlement12() {
		VertexLocation vertLoc = new VertexLocation(new HexLocation(0,1), VertexDirection.East);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildSettlement(false, vertLoc, 99);
		} catch (BadJSONException e) {
			fail("Failed doBuildSettlement while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed doBuildSettlement with invalid index");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("Failed doBuildSettlement with invalid index");
		}
	}
	
}
