package server.command.moves.discard;

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

import server.command.moves.buyDevCard;
import server.command.moves.discardCards;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class discardCardsTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "discard/discard.txt");
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
		args.put("type", "discardCards");
		args.put("playerIndex", (long) 0);
		JSONObject discard = new JSONObject();
		discard.put("brick", (long) 1);
		discard.put("ore", (long) 1);
		discard.put("sheep", (long) 1);
		discard.put("wheat", (long) 1);
		discard.put("wood", (long) 1);
		args.put("discardedCards", discard);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		discardCards dc = new discardCards();
		try {
			dc.execute(args, cookie);
			System.out.println("Passed discardCards test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed discardCards test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "discardCards");
		args.put("playerIndex", (long) 1);
		JSONObject discard = new JSONObject();
		discard.put("brick", (long) 1);
		discard.put("ore", (long) 1);
		discard.put("sheep", (long) 1);
		discard.put("wheat", (long) 1);
		discard.put("wood", (long) 1);
		args.put("discardedCards", discard);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		discardCards dc = new discardCards();
		try {
			dc.execute(args, cookie);
			fail("Failed discardCards test where index arg "
					+ "is invalid: wrong player");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "index arg is invalid: wrong player");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("playerIndex", (long) 0);
		JSONObject discard = new JSONObject();
		discard.put("brick", (long) 1);
		discard.put("ore", (long) 1);
		discard.put("sheep", (long) 1);
		discard.put("wheat", (long) 1);
		discard.put("wood", (long) 1);
		args.put("discardedCards", discard);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		discardCards dc = new discardCards();
		try {
			dc.execute(args, cookie);
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
		args.put("playerIndex", (long) 0);
		JSONObject discard = new JSONObject();
		discard.put("brick", (long) 1);
		discard.put("ore", (long) 1);
		discard.put("sheep", (long) 1);
		discard.put("wheat", (long) 1);
		args.put("discardedCards", discard);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		discardCards dc = new discardCards();
		try {
			dc.execute(args, cookie);
			fail("Failed discardCards test where wood arg "
					+ "is invalid: dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "wood arg is invalid: dne");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "discardCards");
		args.put("playerIndex", (long) 0);
		JSONObject discard = new JSONObject();
		discard.put("brick", (long) 1);
		discard.put("ore", (long) 1);
		discard.put("sheep", (long) 1);
		discard.put("wheat", (long) 1);
		discard.put("wood", (long) 1);
		args.put("discardedCards", discard);
		String cookie = "";
		discardCards dc = new discardCards();
		try {
			dc.execute(args, cookie);
			fail("Failed discardCards test where cookie "
					+ "is invalid: empty");
		} catch (ServerAccessException e) {
			System.out.println("Passed discardCards test where "
					+ "cookie is invalid: empty");
		}
	}
}
