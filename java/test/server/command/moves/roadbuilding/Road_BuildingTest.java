package server.command.moves.roadbuilding;

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
import server.command.moves.Road_Building;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class Road_BuildingTest {

	private static Model modelFromFile() {
		Model model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/server/command/moves/"
				+ "roadbuilding/roadbuilding.txt");
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
		args.put("type", "Road_Building");
		args.put("playerIndex", (long) 0);
		JSONObject spot1 = new JSONObject();
		spot1.put("x", (long) 0);
		spot1.put("y", (long) 2);
		spot1.put("direction", "NW");
		JSONObject spot2 = new JSONObject();
		spot2.put("x", (long) 0);
		spot2.put("y", (long) 2);
		spot2.put("direction", "SW");
		args.put("spot1", spot1);
		args.put("spot2", spot2);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		Road_Building r_b = new Road_Building();
		try {
			r_b.execute(args, cookie);
			System.out.println("Passed Road_Building test where everything "
					+ "is valid");
		} catch (ServerAccessException e) {
			fail("Failed Road_Building test where everything is valid");
		}
	}
	
	//Invalid	
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("type", "Road_Building");
		args.put("playerIndex", (long) 0);
		JSONObject spot1 = new JSONObject();
		spot1.put("x", (long) 0);
		spot1.put("y", (long) 2);
		spot1.put("direction", "NW");
		JSONObject spot2 = new JSONObject();
		spot2.put("x", (long) 0);
		spot2.put("y", (long) 2);
		spot2.put("direction", "SW");
		args.put("spot1", spot1);
		args.put("spot2", spot2);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=1";
		Road_Building r_b = new Road_Building();
		try {
			r_b.execute(args, cookie);
			fail("Failed Road_Building test where - "
					+ "is invalid: ");
		} catch (ServerAccessException e) {
			System.out.println("Passed Road_Building test where - "
					+ "is invalid: ");
		}
	}

}
