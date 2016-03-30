package server.command.moves;

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

import server.command.moves.finishTurn;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;


public class finishTurnTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/jsonMap.txt");
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

	//One players properly ends his turn
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "finishTurn");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		finishTurn ft = new finishTurn();
		try {
			String changedGame = ft.execute(args, cookie);
			Model game = ServerKernel.sole().getGame(1);
			JSONParser parser = new JSONParser();
			JSONObject retModel = (JSONObject) parser.parse(changedGame);
			Model returnedGame = new Model(retModel);
			if(game.isTurn(1) && returnedGame.isTurn(1) && 
					game.equalsJSON(retModel)) {
				System.out.println("Passed finishTurn test when cookies and "
						+ "arguments are valid - one player");
			} else {
				fail("Failed finishTurn test when cookies and arguments are "
						+ "valid - one player - turn not changed");
			}
		} catch (ServerAccessException | ParseException | BadJSONException e) {
			fail("Failed finishTurn test when cookies and arguments are "
					+ "valid - one player - bad data access or returned JSON");
		}
	}
	
	//arg type mismatch
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "sendChat");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		finishTurn ft = new finishTurn();
		try {
			ft.execute(args, cookie);
			fail("Failed finishTurn test where type is sendChat");
		} catch (ServerAccessException e) {
			System.out.println("Passed finishTurn test where type is "
					+ "sendChat");
		}
	}
	
	//invalid playerIndex
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "finishTurn");
		args.put("playerIndex", (long) 1);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		finishTurn ft = new finishTurn();
		try {
			ft.execute(args, cookie);
			//TODO Ask Bentz about this
			fail("Failed finishTurn test where index is incorrect");
		} catch (ServerAccessException e) {
			System.out.println("Passed finishTurn test where argument index "
					+ "is incorrect");
		}
	}
	
	//Bad Cookie - game
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "finishTurn");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=3";
		finishTurn ft = new finishTurn();
		try {
			ft.execute(args, cookie);
			fail("Failed finishTurn test where game Cookie does not exist");
		} catch (ServerAccessException e) {
			System.out.println("Passed finishTurn test where game Cookie "
					+ "references a game that does not exist");
		}
	}
	
	//Bad Cookie - user
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "finishTurn");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		finishTurn ft = new finishTurn();
		try {
			ft.execute(args, cookie);
			fail("Failed finishTurn test where user Cookie is bad");
		} catch (ServerAccessException e) {
			System.out.println("Passed finishTurn test where user Cookie "
					+ "is bad");
		}
	}
	
	//Bad Cookie - user
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("type", "finishTurn");
		args.put("playerIndex", (long) 0);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		finishTurn ft = new finishTurn();
		try {
			ft.execute(args, cookie);
			fail("Failed finishTurn test where user is not in game");
		} catch (ServerAccessException e) {
			System.out.println("Passed finishTurn test where user is not "
					+ "in game");
		}
	}
}
