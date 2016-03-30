package server.command.moves.soldier;

import static org.junit.Assert.*;

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

import server.command.moves.Monopoly;
import server.command.moves.Soldier;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class SoldierTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "soldier/soldier.txt");
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
		args.put("type", "Soldier");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		Soldier s = new Soldier();
		try {
			s.execute(args, cookie);
			System.out.println("Passed Soldier test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed Soldier test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "robPlayer");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Soldier s = new Soldier();
		try {
			s.execute(args, cookie);
			fail("Failed Soldier test where type "
					+ "is invalid: robPlayer");
		} catch (ServerAccessException e) {
			System.out.println("Passed Soldier test where type "
					+ "is invalid: robPlayer");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "Soldier");
		args.put("index", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Soldier s = new Soldier();
		try {
			s.execute(args, cookie);
			fail("Failed Soldier test where playerIndex "
					+ "is invalid: (playerI)index");
		} catch (ServerAccessException e) {
			System.out.println("Passed Soldier test where playerIndex "
					+ "is invalid: (playerI)index");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "Soldier");
		args.put("playerIndex", (long) 0);
		args.put("victim", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Soldier s = new Soldier();
		try {
			s.execute(args, cookie);
			fail("Failed Soldier test where victimIndex "
					+ "is invalid: victim(Index)");
		} catch (ServerAccessException e) {
			System.out.println("Passed Soldier test where victimIndex "
					+ "is invalid: victim(Index)");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "Soldier");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", "-1");
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Soldier s = new Soldier();
		try {
			s.execute(args, cookie);
			fail("Failed Soldier test where x "
					+ "is invalid: type String");
		} catch (ServerAccessException e) {
			System.out.println("Passed Soldier test where x "
					+ "is invalid: type String");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "Soldier");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Soldier s = new Soldier();
		try {
			s.execute(args, cookie);
			fail("Failed Soldier test where y "
					+ "is invalid: dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed Soldier test where y "
					+ "is invalid: dne");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("type", "Soldier");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Soldier s = new Soldier();
		try {
			s.execute(args, cookie);
			fail("Failed Soldier test where loc "
					+ "is invalid: dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed Soldier test where loc "
					+ "is invalid: dne");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute7() {
		JSONObject args = new JSONObject();
		args.put("type", "Soldier");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4}; catan.game=1";
		Soldier s = new Soldier();
		try {
			s.execute(args, cookie);
			fail("Failed Soldier test where cookie "
					+ "is invalid: User not in game");
		} catch (ServerAccessException e) {
			System.out.println("Passed Soldier test where cookie "
					+ "is invalid: User not in game");
		}
	}

}
