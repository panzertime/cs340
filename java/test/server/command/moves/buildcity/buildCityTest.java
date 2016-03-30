package server.command.moves.buildcity;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.command.moves.buildCity;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class buildCityTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "buildcity/city.txt");
		FileInputStream fis;
		try {
			fis = new FileInputStream(jsonFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Scanner scanner = new Scanner(bis);
			StringBuilder x = new StringBuilder();
			while(scanner.hasNextLine()) {
				x.append(scanner.nextLine());
			}
			scanner.close();
			
			JSONObject jsonModel = (JSONObject) parser.parse(x.toString());
			
			model = new Model(jsonModel);
			
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			e.printStackTrace();
		}
		
		return model;
	}

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ServerKernel.sole().reinitAll();
		Model game = modelFromFile();
		Model game1 = modelFromFile();
		Model game2 = modelFromFile();
		ServerKernel.sole().putGame(game);
		ServerKernel.sole().putGame(game1);
		ServerKernel.sole().putGame(game2);
		
		User user1 = new User("Sam", "sam");
		ServerKernel.sole().addUser(user1);
		User user2 = new User("Brooke", "brooke");
		ServerKernel.sole().addUser(user2);
		User user3 = new User("Pete", "pete");
		ServerKernel.sole().addUser(user3);
		User user4 = new User("Mark", "mark");
		ServerKernel.sole().addUser(user4);
		User user5 = new User("Joshua", "joshua");
		ServerKernel.sole().addUser(user5);
	}

	//Good - true
	@Test
	public void testExecute() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 0);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 1);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			System.out.println("Passed buildCity test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed acceptTurn test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 0);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("y", (long) 1);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			fail("Failed buildCity test where vertexLocation arg is invalid"
					+ ": no x");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildCity test where vertexLocation "
					+ "arg is invalid: no x");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 0);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			fail("Failed buildCity test where vertexLocation arg is invalid"
					+ ": no y");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildCity test where vertexLocation "
					+ "arg is invalid: no y");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 0);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", "0");
		vertLoc.put("y", "1");
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			fail("Failed buildCity test where vertexLocation arg is invalid"
					+ ": x and y of type String");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildCity test where vertexLocation "
					+ "arg is invalid: x and y of type String");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 0);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 1);
		vertLoc.put("diretection", "");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			fail("Failed buildCity test where vertexLocation arg is invalid"
					+ ": empty direction");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildCity test where vertexLocation "
					+ "arg is invalid: empty direction");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			fail("Failed buildCity test where vertexLocation arg is invalid"
					+ ": no vertex location");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildCity test where vertexLocation "
					+ "arg is invalid: no vertex location");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 1);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 1);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			fail("Failed buildCity test where playerIndex arg is invalid"
					+ ": wrong number");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildCity test where playerIndex "
					+ "arg is invalid: wrong number");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute7() {
		JSONObject args = new JSONObject();
		args.put("type", "buildSettlement");
		args.put("playerIndex", (long) 0);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 1);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			fail("Failed buildCity test where moveType arg is invalid:"
					+ " buildSettlement");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildCity test where moveType "
					+ "arg is invalid: buildSettlement");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute8() {
		JSONObject args = new JSONObject();
		args.put("type", "buildSettlement");
		args.put("playerIndex", (long) 0);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 1);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":1}; catan.game=1";
		buildCity bt = new buildCity();
		try {
			bt.execute(args, cookie);
			fail("Failed buildCity test where cookie is invalid:"
					+ " no game");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildCity test where cookie "
					+ "is invalid: no game");
		}
	}

}
