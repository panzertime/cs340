package server.command.moves.monopoly;

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
import server.command.moves.discardCards;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class MonopolyTest {
	
	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "monopoly/monopoly.txt");
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
		args.put("type", "Monopoly");
		args.put("playerIndex", (long) 0);
		args.put("resource", "wheat");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		Monopoly m = new Monopoly();
		try {
			m.execute(args, cookie);
			System.out.println("Passed Monopoly test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed Monopoly test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "Monopoloy");
		args.put("playerIndex", (long) 0);
		args.put("resource", "wheat");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Monopoly m = new Monopoly();
		try {
			m.execute(args, cookie);
			fail("Failed Monopoly test where type "
					+ "is invalid: Monopoloy");
		} catch (ServerAccessException e) {
			System.out.println("Passed Monopoly test where type "
					+ "is invalid: Monopoloy");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "Monopoly");
		args.put("playerIndex", null);
		args.put("resource", "wheat");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Monopoly m = new Monopoly();
		try {
			m.execute(args, cookie);
			fail("Failed Monopoly test where playerIndex "
					+ "is invalid: null");
		} catch (ServerAccessException e) {
			System.out.println("Passed Monopoly test where playerIndex "
					+ "is invalid: null");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "Monopoly");
		args.put("playerIndex", (long) 0);
		args.put("resource", new JSONObject());
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Monopoly m = new Monopoly();
		try {
			m.execute(args, cookie);
			fail("Failed Monopoly test where resource "
					+ "is invalid: {}");
		} catch (ServerAccessException e) {
			System.out.println("Passed Monopoly test where resource "
					+ "is invalid: {}");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "Monopoly");
		args.put("playerIndex", (long) 0);
		args.put("resource", "wheat");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0};";
		Monopoly m = new Monopoly();
		try {
			m.execute(args, cookie);
			fail("Failed Monopoly test where cookie "
					+ "is invalid: no game");
		} catch (ServerAccessException e) {
			System.out.println("Passed Monopoly test where cookie "
					+ "is invalid: no game");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "Monopoly");
		args.put("playerIndex", (long) 0);
		args.put("resource", "wheat");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=54";
		Monopoly m = new Monopoly();
		try {
			m.execute(args, cookie);
			fail("Failed Monopoly test where cookie "
					+ "is invalid: game dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed Monopoly test where cookie "
					+ "is invalid: game dne");
		}
	}

}
