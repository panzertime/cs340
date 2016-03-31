package server.command.moves.maritimetrade;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import server.command.moves.discardCards;
import server.command.moves.maritimeTrade;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class maritimeTradeTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "maritimetrade/maritimeTrade.txt");
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
		args.put("type", "maritimeTrade");
		args.put("playerIndex", (long) 0);
		args.put("ratio", (long) 2);
		args.put("inputResource", "wheat");
		args.put("outputResource", "wheat");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		maritimeTrade mt = new maritimeTrade();
		try {
			mt.execute(args, cookie);
			System.out.println("Passed maritimeTrade test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed maritimeTrade test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		JSONArray mta = new JSONArray();
		mta.add("maritmeTrade");
		args.put("type", mta);
		args.put("playerIndex", (long) 0);
		args.put("inputResource", "sheep");
		args.put("outputResource", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		maritimeTrade mt = new maritimeTrade();
		try {
			mt.execute(args, cookie);
			fail("Failed discardCards test where type arg "
					+ "is invalid: [maritimeTrade]");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "type arg is invalid: [maritimeTrade]");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("playerIndex", (long) 0);
		args.put("inputResource", "sheep");
		args.put("outputResource", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		maritimeTrade mt = new maritimeTrade();
		try {
			mt.execute(args, cookie);
			fail("Failed discardCards test where type arg "
					+ "is invalid: dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "type arg is invalid: dne");
		}
	}
	
	
	//Invalid	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "maritimeTrade");
		args.put("playerIndex", (long) 0);
		args.put("inputResource", "sheep");
		args.put("outputResource", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		maritimeTrade mt = new maritimeTrade();
		try {
			mt.execute(args, cookie);
			fail("Failed discardCards test where index arg "
					+ "is invalid: wrong player");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "index arg is invalid: wrong player");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "maritimeTrade");
		args.put("playerIndex", (long) 0);
		args.put("inputResource", "sheeep");
		args.put("outputResource", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		maritimeTrade mt = new maritimeTrade();
		try {
			mt.execute(args, cookie);
			fail("Failed discardCards test where inputResource arg "
					+ "is invalid: sheeep");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "inputResource arg is invalid: sheeep");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "maritimeTrade");
		args.put("playerIndex", (long) 0);
		args.put("inputResource", "sheep");
		args.put("outputResource", null);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		maritimeTrade mt = new maritimeTrade();
		try {
			mt.execute(args, cookie);
			fail("Failed discardCards test where outputResource arg "
					+ "is invalid: null");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "outputResource arg is invalid: null");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("type", "maritimeTrade");
		args.put("playerIndex", (long) 0);
		args.put("inputResource", "sheep");
		args.put("outputResource", "sheep");
		String cookie = "catan.game=1";
		maritimeTrade mt = new maritimeTrade();
		try {
			mt.execute(args, cookie);
			fail("Failed discardCards test where cookie "
					+ "is invalid: no player");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "cookie is invalid: no player");
		}
	}

}
