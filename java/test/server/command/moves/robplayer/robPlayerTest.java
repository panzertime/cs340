package server.command.moves.robplayer;

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
import server.command.moves.robPlayer;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class robPlayerTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "robplayer/robplayer.txt");
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
		args.put("type", "robPlayer");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			System.out.println("Passed robPlayer test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed robPlayer test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "discard");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			fail("Failed robPlayer test where type "
					+ "is invalid: discard");
		} catch (ServerAccessException e) {
			System.out.println("Passed robPlayer test where type "
					+ "is invalid: discard");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "robPlayer");
		args.put("index", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			fail("Failed robPlayer test where playerIndex "
					+ "is invalid: (playerI)index");
		} catch (ServerAccessException e) {
			System.out.println("Passed robPlayer test where playerIndex "
					+ "is invalid: (playerI)index");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "robPlayer");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) -1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			fail("Failed robPlayer test where victimIndex "
					+ "is invalid: out of bounds");
		} catch (ServerAccessException e) {
			System.out.println("Passed robPlayer test where victimIndex "
					+ "is invalid: out of bounds");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "robPlayer");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			fail("Failed robPlayer test where x "
					+ "is invalid: dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed robPlayer test where x "
					+ "is invalid: dne");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "robPlayer");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", "-1");
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			fail("Failed robPlayer test where y "
					+ "is invalid: type String");
		} catch (ServerAccessException e) {
			System.out.println("Passed robPlayer test where y "
					+ "is invalid: type String");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("type", "robPlayer");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("loctaion", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			fail("Failed robPlayer test where location "
					+ "is invalid: loctaion");
		} catch (ServerAccessException e) {
			System.out.println("Passed robPlayer test where location "
					+ "is invalid: loctaion");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute7() {
		JSONObject args = new JSONObject();
		args.put("type", "robPlayer");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Miguel\",\"password\":\"miguel\","
				+ "\"playerID\":6}; catan.game=1";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			fail("Failed robPlayer test where cookie "
					+ "is invalid: user not in system");
		} catch (ServerAccessException e) {
			System.out.println("Passed robPlayer test where cookie "
					+ "is invalid: user not in system");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute8() {
		JSONObject args = new JSONObject();
		args.put("type", "robPlayer");
		args.put("playerIndex", (long) 0);
		args.put("victimIndex", (long) 1);
		JSONObject loc = new JSONObject();
		loc.put("x", (long) -1);
		loc.put("y", (long) -1);
		args.put("location", loc);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=15";
		robPlayer rp = new robPlayer();
		try {
			rp.execute(args, cookie);
			fail("Failed robPlayer test where cookie "
					+ "is invalid: invalid game");
		} catch (ServerAccessException e) {
			System.out.println("Passed robPlayer test where cookie "
					+ "is invalid: invalid game");
		}
	}

}
