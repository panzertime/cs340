package server.command.moves.yearofplenty;

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
import server.command.moves.Year_of_Plenty;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class YearOfPlentyTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "yearofplenty/yearofplenty.txt");
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
		args.put("type", "Year_of_Plenty");
		args.put("playerIndex", (long) 0);
		args.put("resource1", "wheat");
		args.put("resource2", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		Year_of_Plenty yop = new Year_of_Plenty();
		try {
			yop.execute(args, cookie);
			System.out.println("Passed Year_of_Plenty test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed Year_of_Plenty test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "year_of_plenty");
		args.put("playerIndex", (long) 0);
		args.put("resource1", "wheat");
		args.put("resource2", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Year_of_Plenty yop = new Year_of_Plenty();
		try {
			yop.execute(args, cookie);
			fail("Failed Year_of_Plenty test where type "
					+ "is invalid: wrong case");
		} catch (ServerAccessException e) {
			System.out.println("Passed Year_of_Plenty test where type "
					+ "is invalid: wrong case");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("type", "Year_of_Plenty");
		args.put("layerIndex", (long) 0);
		args.put("resource1", "wheat");
		args.put("resource2", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Year_of_Plenty yop = new Year_of_Plenty();
		try {
			yop.execute(args, cookie);
			fail("Failed Year_of_Plenty test where playerIndex "
					+ "is invalid: (p)layerIndex");
		} catch (ServerAccessException e) {
			System.out.println("Passed Year_of_Plenty test where playerIndex "
					+ "is invalid: (p)layerIndex");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("type", "Year_of_Plenty");
		args.put("playerIndex", (long) 0);
		args.put("resource1", "wheet");
		args.put("resource2", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Year_of_Plenty yop = new Year_of_Plenty();
		try {
			yop.execute(args, cookie);
			fail("Failed Year_of_Plenty test where resource1 "
					+ "is invalid: wheet");
		} catch (ServerAccessException e) {
			System.out.println("Passed Year_of_Plenty test where resource1 "
					+ "is invalid: wheet");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("type", "Year_of_Plenty");
		args.put("playerIndex", (long) 0);
		args.put("resource1", "wheat");
		args.put("resource", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Year_of_Plenty yop = new Year_of_Plenty();
		try {
			yop.execute(args, cookie);
			fail("Failed Year_of_Plenty test where resource2 "
					+ "is invalid: resource");
		} catch (ServerAccessException e) {
			System.out.println("Passed Year_of_Plenty test where resource2 "
					+ "is invalid: resource");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("type", "Year_of_Plenty");
		args.put("playerIndex", (long) 0);
		args.put("resource1", "wheat");
		args.put("resource2", "sheep");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=3";
		Year_of_Plenty yop = new Year_of_Plenty();
		try {
			yop.execute(args, cookie);
			fail("Failed Year_of_Plenty test where cookie "
					+ "is invalid: game dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed Year_of_Plenty test where cookie "
					+ "is invalid: game dne");
		}
	}

}
