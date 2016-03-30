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

public class modelTest {

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
	
	//valid - get full game
	@Test
	public void testExecute() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		model m = new model();
		try {
			String result = m.execute(args, cookie);
			if(result.equals("\"true\"")) {
				fail("Failed model test where everything "
						+ "is valid: returns model - Sam");
			} else {
				System.out.println("Passed model test where everything "
						+ "is valid: returns model - Sam");
			}
		} catch (ServerAccessException e) {
			fail("Failed model test where everything "
						+ "is valid: returns model - Sam");
		}
	}
	
	//valid - don't get game
	@Test
	public void testExecute1() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}; catan.game=0";
		model m = new model();
		try {
			m.setVersion(0);
			String result = m.execute(args, cookie);
			if(result.equals("\"true\"")) {
				System.out.println("Passed model test where everything "
						+ "is valid: returns \"true\" - Sam");
			} else {
				fail("Failed model test where everything "
						+ "is valid: returns \"true\" - Sam");
			}
		} catch (ServerAccessException e) {
			fail("Failed model test where everything "
						+ "is valid: returns \"true\" - Sam");
		}
	}
	
	//valid - get full game
	@Test
	public void testExecute4() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4}; catan.game=0";
		model m = new model();
		try {
			String result = m.execute(args, cookie);
			if(result.equals("\"true\"")) {
				fail("Failed model test where everything "
						+ "is valid: returns model - Joshua");
			} else {
				System.out.println("Passed model test where everything "
						+ "is valid: returns model - Joshua");
			}
		} catch (ServerAccessException e) {
			fail("Failed model test where everything "
						+ "is valid: returns model - Joshua");
		}
	}
	
	//valid - don't get game
	@Test
	public void testExecute5() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4}; catan.game=0";
		model m = new model();
		try {
			m.setVersion(0);
			String result = m.execute(args, cookie);
			if(result.equals("\"true\"")) {
				System.out.println("Passed model test where everything "
						+ "is valid: returns \"true\" - Joshua");
			} else {
				fail("Failed model test where everything "
						+ "is valid: returns \"true\" - Joshua");
			}
		} catch (ServerAccessException e) {
			fail("Failed model test where everything "
						+ "is valid: returns \"true\" - Joshua");
		}
	}
	
	//invalid cookie - get full game
	@Test
	public void testExecute2() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0}";
		model m = new model();
		try {
			m.execute(args, cookie);
			fail("Failed model test where cookie "
						+ "is invalid: returns model");
		} catch (ServerAccessException e) {
			System.out.println("Passed model test where cookie "
					+ "is invalid: returns model");
		}
	}
	
	//invalid - don't get game - bad cookie
	@Test
	public void testExecute3() {
		JSONObject args = null;
		String cookie = "; catan.game=0";
		model m = new model();
		try {
			m.setVersion(0);
			m.execute(args, cookie);
			fail("Failed model test where cookie "
						+ "is invalid: returns \"true\"");
		} catch (ServerAccessException e) {
			System.out.println("Passed model test where cookie "
					+ "is invalid: returns \"true\"");
		}
	}

}
