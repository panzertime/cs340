package server.command.moves.build.road;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import server.command.moves.buildRoad;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class buildRoadTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "build/road/road.txt");
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
		args.put("type", "buildRoad");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject roadLoc = new JSONObject();
		roadLoc.put("x", (long) 0);
		roadLoc.put("y", (long) 1);
		roadLoc.put("direction", "SE");
		args.put("roadLocation", roadLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		buildRoad br = new buildRoad();
		try {
			br.execute(args, cookie);
			System.out.println("Passed buildRoad test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed buildRoad test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "buildRoad");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject roadLoc = new JSONObject();
		roadLoc.put("y", (long) 1);
		roadLoc.put("direction", "SE");
		args.put("roadLocation", roadLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildRoad br = new buildRoad();
		try {
			br.execute(args, cookie);
			fail("Failed buildRoad test where vertexLocation arg is invalid"
					+ ": no x");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildRoad test where vertexLocation "
					+ "arg is invalid: no x");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "buildRoad");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject roadLoc = new JSONObject();
		roadLoc.put("x", (long) 0);
		roadLoc.put("y", (long) 1);
		roadLoc.put("diretection", "");
		args.put("roadLocation", roadLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildRoad br = new buildRoad();
		try {
			br.execute(args, cookie);
			fail("Failed buildRoad test where vertexLocation arg is invalid"
					+ ": empty direction");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildRoad test where vertexLocation "
					+ "arg is invalid: empty direction");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "buildRoad");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject roadLoc = new JSONObject();
		roadLoc.put("x", (long) 0);
		roadLoc.put("direction", "SE");
		args.put("roadLocation", roadLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildRoad br = new buildRoad();
		try {
			br.execute(args, cookie);
			fail("Failed buildRoad test where vertexLocation arg is invalid"
					+ ": no y");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildRoad test where vertexLocation "
					+ "arg is invalid: no y");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "buildRoad");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject roadLoc = new JSONObject();
		roadLoc.put("x", "0");
		roadLoc.put("y", "1");
		roadLoc.put("direction", "SE");
		args.put("roadLocation", roadLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildRoad br = new buildRoad();
		try {
			br.execute(args, cookie);
			fail("Failed buildRoad test where vertexLocation arg is invalid"
					+ ": x and y of type String");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildRoad test where vertexLocation "
					+ "arg is invalid: x and y of type String");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject roadLoc = new JSONObject();
		roadLoc.put("x", (long) 0);
		roadLoc.put("y", (long) 1);
		roadLoc.put("direction", "SE");
		args.put("roadLocation", roadLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildRoad br = new buildRoad();
		try {
			br.execute(args, cookie);
			fail("Failed buildRoad test where move type arg is invalid"
					+ ": buildCity");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildRoad test where move type "
					+ "arg is invalid: buildCity");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("type", "buildRoad");
		args.put("free", false);
		JSONObject roadLoc = new JSONObject();
		roadLoc.put("x", (long) 0);
		roadLoc.put("y", (long) 1);
		roadLoc.put("direction", "SE");
		args.put("roadLocation", roadLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildRoad br = new buildRoad();
		try {
			br.execute(args, cookie);
			fail("Failed buildRoad test where playerIndex arg is invalid"
					+ ": dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildRoad test where playerIndex "
					+ "arg is invalid: dne");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute7() {
		JSONObject args = new JSONObject();
		args.put("type", "buildRoad");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject roadLoc = new JSONObject();
		roadLoc.put("x", (long) 0);
		roadLoc.put("y", (long) 1);
		roadLoc.put("direction", "SE");
		args.put("roadLocation", roadLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0};";
		buildRoad br = new buildRoad();
		try {
			br.execute(args, cookie);
			fail("Failed buildRoad test where cookie is invalid"
					+ ": missing game");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildRoad test where cookie "
					+ "is invalid: missing game");
		}
	}

}
