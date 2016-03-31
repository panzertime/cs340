package server.command.game;

import static org.junit.Assert.*;


import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.definitions.CatanColor;

public class addAITest {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ServerKernel.sole().reinitAll();
		Model game = new Model(false, false, false, "One");
		Model game1 = new Model(false, false, false, "Two");
		Model game2 = new Model(false, false, false, "Three");
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
		
		game.joinGame(user1.getID(), user1.getUsername(), CatanColor.RED);
		game.joinGame(user5.getID(), user5.getUsername(), CatanColor.BLUE);
	}

	//invalid - good cookie
	@Test
	public void testExecute() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		addAI aAI = new addAI();
		try {
			aAI.execute(args, cookie);
			fail("Failed addAI test where everything is valid");
		} catch (ServerAccessException e) {
			System.out.println("Passed addAI test where everything "
					+ "is valid");
		}
	}
	
	//invalid - bad cookie
	@Test
	public void testExecute1() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0};";
		addAI aAI = new addAI();
		try {
			aAI.execute(args, cookie);
			fail("Failed addAI test where everything is valid");
		} catch (ServerAccessException e) {
			System.out.println("Passed addAI test where cookie "
					+ "is bad");
		}
	}
	
	//invalid - empty args
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		addAI aAI = new addAI();
		try {
			aAI.execute(args, cookie);
			fail("Failed addAI test where args are empty");
		} catch (ServerAccessException e) {
			System.out.println("Passed addAI test where args "
					+ "are empty");
		}
	}
}
