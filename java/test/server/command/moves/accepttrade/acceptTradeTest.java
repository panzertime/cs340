package server.command.moves.accepttrade;

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

import server.command.moves.acceptTrade;
import server.command.moves.finishTurn;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class acceptTradeTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "accepttrade/trades.txt");
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
		args.put("type", "acceptTrade");
		args.put("playerIndex", (long) 0);
		args.put("willAccept", (Boolean) true);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		acceptTrade at = new acceptTrade();
		try {
			at.execute(args, cookie);
			System.out.println("Passed acceptTurn test where everything "
					+ "is valid: true");
		} catch (ServerAccessException e) {
			fail("Failed acceptTurn test where everything is valid"
					+ ": true");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "acceptTrade");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		acceptTrade at = new acceptTrade();
		try {
			at.execute(args, cookie);
			fail("Failed acceptTurn test where willAccept arg is invalid"
					+ ": dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed acceptTurn test where willAccept arg "
					+ "is invalid: dne");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "acceptTrade");
		args.put("playerIndex", (long) 0);
		args.put("willAccept", "true");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		acceptTrade at = new acceptTrade();
		try {
			at.execute(args, cookie);
			fail("Failed acceptTurn test where willAccept arg is invalid"
					+ ": type string");
		} catch (ServerAccessException e) {
			System.out.println("Passed acceptTurn test where willAccept arg "
					+ "is invalid: type string");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("playerIndex", (long) 0);
		args.put("willAccept", "true");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		acceptTrade at = new acceptTrade();
		try {
			at.execute(args, cookie);
			fail("Failed acceptTurn test where type arg is invalid"
					+ ": dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed acceptTurn test where type arg "
					+ "is invalid: dne");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "acceptTrade");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		acceptTrade at = new acceptTrade();
		try {
			at.execute(args, cookie);
			fail("Failed acceptTurn test where willAccept arg is invalid"
					+ ": dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed acceptTurn test where willAccept arg "
					+ "is invalid: dne");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "acceptTrade");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		acceptTrade at = new acceptTrade();
		try {
			at.execute(args, cookie);
			fail("Failed acceptTurn test where cookie is invalid"
					+ ": game dne = 3");
		} catch (ServerAccessException e) {
			System.out.println("Passed acceptTurn test where cookie "
					+ "is invalid: game dne = 3");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("type", "acceptTrade");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		acceptTrade at = new acceptTrade();
		try {
			at.execute(args, cookie);
			fail("Failed acceptTurn test where playerIndex arg is invalid"
					+ ": 4");
		} catch (ServerAccessException e) {
			System.out.println("Passed acceptTurn test where playerIndex arg "
					+ "is invalid: 4");
		}
	}
}