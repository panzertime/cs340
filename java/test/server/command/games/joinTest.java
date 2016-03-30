package server.command.games;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import server.command.game.listAI;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.definitions.CatanColor;

public class joinTest {
	
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
	}

	//valid - good cookie
	@Test
	public void testExecute() {
		JSONObject args = new JSONObject();
		args.put("id", (long) 0);
		args.put("color", "red");
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0};";
		join j = new join();
		try {
			j.execute(args, cookie);
			System.out.println("Passed join test where everything "
					+ "is valid: Sam - game 0 - red");
		} catch (ServerAccessException e) {
			fail("Failed join test where everything is is valid: "
					+ "Sam - game 0 - red");
		}
	}
	
	//valid - good cookie
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("id", (long) 2);
		args.put("color", "PUCE");
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4};";
		join j = new join();
		try {
			j.execute(args, cookie);
			System.out.println("Passed join test where everything "
					+ "is valid: Joshua - game 2 - PUCE");
		} catch (ServerAccessException e) {
			fail("Failed join test where everything is is valid: "
					+ "Joshua - game 2 - PUCE");
		}
	}
	
	//invalid
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("color", "PUCE");
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4};";
		join j = new join();
		try {
			j.execute(args, cookie);
			fail("Failed join test where id "
					+ "is invalid: dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed join test where id "
					+ "is invalid: dne");
		}
	}
	
	//invalid
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("id", "1");
		args.put("color", "PUCE");
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4};";
		join j = new join();
		try {
			j.execute(args, cookie);
			fail("Failed join test where id "
					+ "is invalid: type String");
		} catch (ServerAccessException e) {
			System.out.println("Passed join test where id "
					+ "is invalid: type String");
		}
	}
	
	//invalid
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("id", (long) 1);
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4};";
		join j = new join();
		try {
			j.execute(args, cookie);
			fail("Failed join test where color "
					+ "is invalid: dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed join test where color "
					+ "is invalid: dne");
		}
	}
	
	//invalid
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("id", (long) 1);
		args.put("color", "PUkE");
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4};";
		join j = new join();
		try {
			j.execute(args, cookie);
			fail("Failed join test where color "
					+ "is invalid: mispelled");
		} catch (ServerAccessException e) {
			System.out.println("Passed join test where color "
					+ "is invalid: mispelled");
		}
	}
	
	//invalid
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("id", (long) 1);
		args.put("color", "PUCE");
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":16};";
		join j = new join();
		try {
			j.execute(args, cookie);
			fail("Failed join test where cookie "
					+ "is invalid: ID wrong");
		} catch (ServerAccessException e) {
			System.out.println("Passed join test where cookie "
					+ "is invalid: ID wrong");
		}
	}

}
