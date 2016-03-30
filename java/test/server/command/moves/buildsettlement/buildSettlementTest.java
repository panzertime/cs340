package server.command.moves.buildsettlement;

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
import server.command.moves.buildSettlement;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class buildSettlementTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "buildsettlement/settlement.txt");
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
		args.put("type", "buildSettlement");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 2);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		buildSettlement bs = new buildSettlement();
		try {
			bs.execute(args, cookie);
			System.out.println("Passed buildSettlement test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed buildSettlement test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "buildSettlement");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("y", (long) 2);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildSettlement bs = new buildSettlement();
		try {
			bs.execute(args, cookie);
			fail("Failed buildSettlement test where vertexLocation arg "
					+ "is invalid: no x");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildSettlement test where "
					+ "vertexLocation arg is invalid: no x");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "buildSettlement");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 2);
		vertLoc.put("diretection", "");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildSettlement bs = new buildSettlement();
		try {
			bs.execute(args, cookie);
			fail("Failed buildSettlement test where vertexLocation arg "
					+ "is invalid: empty direction");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildSettlement test where "
					+ "vertexLocation arg is invalid: empty direction");
		}
	}
	
	//Invalid - y	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "buildSettlement");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", "2");
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildSettlement bs = new buildSettlement();
		try {
			bs.execute(args, cookie);
			fail("Failed buildSettlement test where vertexLocation arg "
					+ "is invalid: y is a string");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildSettlement test where "
					+ "vertexLocation arg is invalid: y is a String");
		}
	}
	
	//Invalid	- free
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "buildSettlement");
		args.put("playerIndex", (long) 0);
		args.put("free", "false");
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 2);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildSettlement bs = new buildSettlement();
		try {
			bs.execute(args, cookie);
			fail("Failed buildSettlement test where free arg "
					+ "is invalid: type String");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildSettlement test where "
					+ "free arg is invalid: type String");
		}
	}
	
	//Invalid	- playerIndex
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "buildSettlement");
		args.put("free", false);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 2);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildSettlement bs = new buildSettlement();
		try {
			bs.execute(args, cookie);
			fail("Failed buildSettlement test where playerIndex arg "
					+ "is invalid: dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildSettlement test where "
					+ "playerIndex arg is invalid: dne");
		}
	}
	
	//Invalid	type
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("type", "buildCity");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 2);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		buildSettlement bs = new buildSettlement();
		try {
			bs.execute(args, cookie);
			fail("Failed buildSettlement test where type arg "
					+ "is invalid: buildCity");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildSettlement test where "
					+ "type arg is invalid: buildCity");
		}
	}
	
	//Invalid cookie	
	@Test
	public void testExecute7() {
		JSONObject args = new JSONObject();
		args.put("type", "buildSettlement");
		args.put("playerIndex", (long) 0);
		args.put("free", false);
		JSONObject vertLoc = new JSONObject();
		vertLoc.put("x", (long) 0);
		vertLoc.put("y", (long) 2);
		vertLoc.put("direction", "SE");
		args.put("vertexLocation", vertLoc);
		String cookie = "catan.game=1";
		buildSettlement bs = new buildSettlement();
		try {
			bs.execute(args, cookie);
			fail("Failed buildSettlement test where cookie "
					+ "is invalid: no user");
		} catch (ServerAccessException e) {
			System.out.println("Passed buildSettlement test where "
					+ "cookie is invalid: no user");
		}
	}
}
