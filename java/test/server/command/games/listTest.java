package server.command.games;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.definitions.CatanColor;

public class listTest {

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
		
		game.joinGame(user1.getID(), user1.getUsername(), CatanColor.YELLOW);
		game.joinGame(user2.getID(), user2.getUsername(), CatanColor.BLUE);
		game.joinGame(user3.getID(), user3.getUsername(), CatanColor.RED);
		game.joinGame(user4.getID(), user4.getUsername(), CatanColor.GREEN);
		
		game1.joinGame(user5.getID(), user5.getUsername(), CatanColor.BLUE);
	}
	
	private final String expected = "[{\"id\":0,\"title\":\"One\",\"players\""
			+ ":[{\"name\":\"Sam\",\"id\":0,\"color\":\"yellow\"},{\"name\":\""
			+ "Brooke\",\"id\":1,\"color\":\"blue\"},{\"name\":\"Pete\",\"id\":"
			+ "2,\"color\":\"red\"},{\"name\":\"Mark\",\"id\":3,\"color\":"
			+ "\"green\"}]},{\"id\":1,\"title\":\"Two\",\"players\":[{\"name\":"
			+ "\"Joshua\",\"id\":4,\"color\":\"blue\"},{},{},{}]},{\"id\":2,\"title\""
			+ ":\"Three\",\"players\":[{},{},{},{}]}]";
	
	//valid - good cookie
	@Test
	public void testExecute() {
		JSONObject args = new JSONObject();
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\","
				+ "\"playerID\":0};";
		list l = new list();
		try {
			String result = l.execute(args, cookie);
			if(expected.equals(result)) {
				System.out.println("Passed list test where everything "
						+ "is valid: Sam");
			} else {
				fail("Failed list test where everything is is valid: "
						+ "Sam");
			}
		} catch (ServerAccessException e) {
			fail("Failed list test where everything is is valid: "
					+ "Sam");
		}
	}
		
	//valid
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4};";
		list l = new list();
		try {
			String result = l.execute(args, cookie);
			if(expected.equals(result)) {
				System.out.println("Passed list test where everything "
						+ "is valid: Joshua - empty JSON args");
			} else {
				fail("Failed list test where everything "
						+ "is valid: Joshua - empty JSON args");
			}
		} catch (ServerAccessException e) {
			fail("Failed list test where everything "
					+ "is valid: Joshua - empty JSON args");
		}
	}
	
	//valid
	@Test
	public void testExecute3() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Joshua\",\"password\":\"joshua\","
				+ "\"playerID\":4};";
		list l = new list();
		try {
			String result = l.execute(args, cookie);
			if(expected.equals(result)) {
				System.out.println("Passed list test where everything "
						+ "is valid: Joshua - null JSON args");
			} else {
				fail("Failed list test where everything "
						+ "is valid: Joshua - null JSON args");
			}
		} catch (ServerAccessException e) {
			fail("Failed list test where everything "
					+ "is valid: Joshua - null JSON args");
		}
	}
	
	//invalid
	@Test
	public void testExecute4() {
		JSONObject args = null;
		String cookie = "";
		list l = new list();
		try {
			l.execute(args, cookie);
			fail("Passed list test where cookie "
					+ "is invalid: empty");
		} catch (ServerAccessException e) {
			System.out.println("Passed list test where cookie "
					+ "is invalid: empty");
		}
	}
	
	//invalid
	@Test
	public void testExecute5() {
		JSONObject args = null;
		String cookie = null;
		list l = new list();
		try {
			l.execute(args, cookie);
			fail("Passed list test where cookie "
					+ "is invalid: null");
		} catch (ServerAccessException e) {
			System.out.println("Passed list test where cookie "
					+ "is invalid: null");
		}
	}
	
	//invalid
	@Test
	public void testExecute6() {
		JSONObject args = null;
		String cookie = "catan.user={\"name\":\"Miguel\",\"password\":\"miguel\","
				+ "\"playerID\":5};";
		list l = new list();
		try {
			l.execute(args, cookie);
			fail("Passed list test where cookie "
					+ "is invalid: player dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed list test where cookie "
					+ "is invalid: player dne");
		}
	}
}
