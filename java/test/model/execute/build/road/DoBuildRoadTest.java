package model.execute.build.road;

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
import shared.model.board.edge.EdgeDirection;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.ViolatedPreconditionException;

public class DoBuildRoadTest {
	
	public JSONObject getJSONFrom(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/execute/build/road/" + file);
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
	public void testDoBuildRoad1() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthWest);
		Model model = null;
		try {
			model = new Model(getJSONFrom("notYourTurn.json"));
			model.doBuildRoad(false, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed DoBuildRoad test when not your turn");
		}
		if(!model.equalsJSON(getJSONFrom("notYourTurn.json"))) {
			fail("failed DoBuildRoad test when not your turn");
		}
	}
	
	@Test
	public void testDoBuildRoad2() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthWest);
		Model model = null;
		try {
			model = new Model(getJSONFrom("robbing.json"));
			model.doBuildRoad(false, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed DoBuildRoad test when robbing");
		}
		if(!model.equalsJSON(getJSONFrom("robbing.json"))) {
			fail("failed DoBuildRoad test when robbing");
		}
	}
	
	@Test
	public void testDoBuildRoad3() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthEast);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildRoad(false, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed DoBuildRoad test when location is taken");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("failed DoBuildRoad test when location is taken");
		}
	}
	
	@Test
	public void testDoBuildRoad4() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,0), EdgeDirection.North);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildRoad(false, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed DoBuildRoad test when location is not connected");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("failed DoBuildRoad test when location is not connected");
		}
	}
	
	@Test
	public void testDoBuildRoad5() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,3), EdgeDirection.SouthEast);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildRoad(false, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed DoBuildRoad test when location is on water");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("failed DoBuildRoad test when location is on water");
		}
	}
	
	@Test
	public void testDoBuildRoad6() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthWest);
		Model model = null;
		try {
			model = new Model(getJSONFrom("notEnoughResources.json"));
			model.doBuildRoad(false, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed DoBuildRoad test when user doesn't have enough resources");
		}
		if(!model.equalsJSON(getJSONFrom("notEnoughResources.json"))) {
			fail("failed DoBuildRoad test when user doesn't have enough resources");
		}
	}
	
	@Test
	public void testDoBuildRoad7() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthEast);
		Model model = null;
		try {
			model = new Model(getJSONFrom("setup-secondRoad.json"));
			model.doBuildRoad(true, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed DoBuildRoad test when in setup and roads touch");
		}
		if(!model.equalsJSON(getJSONFrom("setup-secondRoad.json"))) {
			fail("failed DoBuildRoad test when when in setup and roads touch");
		}
	}
	
	@Test
	public void testDoBuildRoad8() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
		Model model = null;
		try {
			model = new Model(getJSONFrom("setup-firstRoad.json"));
			model.doBuildRoad(true, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed DoBuildRoad test when in first setup and location is valid");
		}
		if(model.equalsJSON(getJSONFrom("placed-firstRoad.json"))) {
			System.out.println("passed DoBuildRoad test when in first setup and location is valid");
		} else {
			fail("failed DoBuildRoad test when in first setup and location is valid");
		}
	}
	
	@Test
	public void testDoBuildRoad9() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1), EdgeDirection.South);
		Model model = null;
		try {
			model = new Model(getJSONFrom("setup-secondRoad.json"));
			model.doBuildRoad(true, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed DoBuildRoad test when in second setup and location is valid");
		}
		if(model.equalsJSON(getJSONFrom("placed-secondRoad.json"))) {
			System.out.println("passed DoBuildRoad test when in second setup and location is valid");
		} else {
			fail("failed DoBuildRoad test when in second setup and location is valid");
		}
	}
	
	@Test
	public void testDoBuildRoad10() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1), EdgeDirection.SouthEast);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildRoad(false, edgeLoc, 0);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed DoBuildRoad test when playing and location is valid");
		}
		if(model.equalsJSON(getJSONFrom("placedRoad.json"))) {
			System.out.println("passed DoBuildRoad test when playing and location is valid");
		} else {
			fail("failed DoBuildRoad test when playing and location is valid");
		}
	}
	
	@Test
	public void testDoBuildRoad11() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthEast);
		Model model = null;
		try {
			model = new Model(getJSONFrom("playing.json"));
			model.doBuildRoad(false, edgeLoc, 9);
		} catch (BadJSONException e) {
			fail("Failed DoBuildRoad test while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed DoBuildRoad test with bad index");
		}
		if(!model.equalsJSON(getJSONFrom("playing.json"))) {
			fail("failed DoBuildRoad test with bad index");
		}
	}
}
