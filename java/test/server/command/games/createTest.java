package server.command.games;

import static org.junit.Assert.*;

import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;

public class createTest {
	
	//TODO uncomment or delete
	/*public expectedGamesList(String file) {
		
	}*/
	
	private JSONArray blankPlayerList() {
		JSONArray result = new JSONArray();
		result.add(new JSONObject());
		result.add(new JSONObject());
		result.add(new JSONObject());
		result.add(new JSONObject());
		return result;
	}
	
	private boolean isExpectedJSON(JSONObject jsonResponse, 
			String expectedName, long expectedID, JSONArray playersList) {
		boolean result = false;
		String name = (String) jsonResponse.get("title");
		Long id = (Long) jsonResponse.get("id");
		JSONArray players = (JSONArray) jsonResponse.get("players");
		
		if(expectedName.equals(name)
				&& id == expectedID
				&& playersList.equals(players)) {
			result = true;
		}
		
		return result;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		User user1 = new User("Sam", "sam");
		ServerKernel.sole().addUser(user1);
		User user2 = new User("Brooke", "brooke");
		ServerKernel.sole().addUser(user2);
		User user3 = new User("Pete", "pete");
		ServerKernel.sole().addUser(user3);
		User user4 = new User("Mark", "mark");
		ServerKernel.sole().addUser(user4);
	}

	@Before
	public void setUp() throws Exception {
		ServerKernel.sole().reinitGames();
	}

	//Test adding one valid game with valid cookie
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			String response = createCommand.execute(args, cookie);
			JSONParser parser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) parser.parse(response);
			JSONArray playersList = blankPlayerList();
			if(isExpectedJSON(jsonResponse, "Steve", 0, playersList)) {
				System.out.println("Passed create command test when passed "
						+ "valid arguments and cookie");
			} else {
				fail("Failed create command test when passed valid arguments "
						+ "and cookie - response returned was not what was "
						+ "expected");
			}
		} catch (ServerAccessException e) {
			fail("Failed create command test when passed valid arguments "
					+ "and cookie - Problem accessing server data");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			fail("Failed create command test when passed valid arguments "
					+ "and cookie - poorly formed JSON response");
			e.printStackTrace();
		}
	}
	
	//Create two valid games in a row 
	@Test
	public void testExecute2() {
		JSONObject args1 = new JSONObject();
		args1.put("name", "Steve");
		args1.put("randomTiles", false);
		args1.put("randomNumbers", false);
		args1.put("randomPorts", false);
		String cookie1 = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand1 = new create();
		
		JSONObject args2 = new JSONObject();
		args2.put("name", "Steve");
		args2.put("randomTiles", true);
		args2.put("randomNumbers", true);
		args2.put("randomPorts", true);
		String cookie2 = "catan.user={\"name\":\"Brooke\",\"password\":"
				+ "\"brooke\",\"playerID\":1};Path=/;";
		create createCommand2 = new create();
		try {
			String response = createCommand1.execute(args1, cookie1);
			String response2 = createCommand2.execute(args2, cookie2);
			
			JSONParser parser1 = new JSONParser();
			JSONParser parser2 = new JSONParser();
			
			JSONObject jsonResponse1 = (JSONObject) parser1.parse(response);
			JSONObject jsonResponse2 = (JSONObject) parser2.parse(response2);
			
			JSONArray playersList = blankPlayerList();
			if(isExpectedJSON(jsonResponse1, "Steve", 0, playersList) &&
					isExpectedJSON(jsonResponse2, "Steve", 1, playersList)) {
				System.out.println("Passed create command test when passed "
						+ "valid arguments and cookie for two games");
			} else {
				fail("Failed create command test when passed valid arguments "
						+ "and cookies for two games - responses "
						+ "returned were not what were expected");
			}
		} catch (ServerAccessException e) {
			fail("Failed create command test when passed valid arguments "
					+ "and cookies for two games - Problem accessing server "
					+ "data");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			fail("Failed create command test when passed valid arguments "
					+ "and cookies for two games - poorly formed JSON "
					+ "response");
			e.printStackTrace();
		}
	}
	
	//invalid game name - blank
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("name", "");
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid game name");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid game name");
		}
	}
	
	//invalid game name - int
	@Test
	public void testExecute13() {
		JSONObject args = new JSONObject();
		args.put("name", 5);
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid game name: the int 5");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid game name: the int 5");
		}
	}
	
	//invalid game name - boolean
	@Test
	public void testExecute14() {
		JSONObject args = new JSONObject();
		args.put("name", true);
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid game name: boolean true");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid game name: boolean true");
		}
	}
	
	//invalid game name - null
	@Test
	public void testExecute15() {
		JSONObject args = new JSONObject();
		args.put("name", null);
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid game name: null");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid game name: null");
		}
	}
	
	//invalid game name - empty JSON object
	@Test
	public void testExecute16() {
		JSONObject args = new JSONObject();
		args.put("name", new JSONObject());
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid game name: {}");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid game name: {}");
		}
	}
	
	//invalid game name - JSON array with answer
	@Test
	public void testExecute18() {
		JSONObject args = new JSONObject();
		JSONArray ans = new JSONArray();
		ans.add("Steve");
		args.put("name", ans);
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid game name: [\"Steve\"]");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid game name:  [\"Steve\"]");
		}
	}
	
	//invalid boolean option - int
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", 3);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid randomTiles: int 3");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid randomTiles: int 3");
		}
	}
	
	//invalid boolean option - null
	@Test
	public void testExecute12() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", null);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid randomTiles: null");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid randomTiles: null");
		}
	}
	
	//invalid boolean option - blank json object
	@Test
	public void testExecute10() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", new JSONObject());
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid randomTiles: {}");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid randomTiles: {}");
		}
	}
	
	//invalid boolean option - json array with boolean answer
	@Test
	public void testExecute17() {
		JSONObject args = new JSONObject();
		JSONArray ans = new JSONArray();
		ans.add(true);
		args.put("name", "Steve");
		args.put("randomTiles", ans);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid randomTiles: [true]");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid randomTiles: [true]");
		}
	}
	
	//invalid boolean option - string
	@Test
	public void testExecute11() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", "false");
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Passed create command test when passed "
					+ "invalid randomTiles: \"false\"");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "invalid randomTiles: \"false\"");
		}
	}
	
	//invalid cookie parameters
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.use={\"username\":\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Failed create command test when passed "
					+ "valid arguments but invalid cookie: username\":\"");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "valid arguments but invalid cookie: username\":\"");
		}
	}
	
	//invalid cookie parameters
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "catan.use={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Failed create command test when passed "
					+ "valid arguments but invalid cookie: catan.use(r)");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "valid arguments but invalid cookie: catan.use(r)");
		}
	}
	
	//No parameters
	@Test
	public void testExecute7() {
		JSONObject args = new JSONObject();
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Failed create command test when passed "
					+ "valid cookie but missing all arguments");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "valid cookie but missing all arguments");
		}
	}
	
	//Only missing one parameter
	@Test
	public void testExecute8() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
				+ ",\"playerID\":0};Path=/;";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Failed create command test when passed "
					+ "valid cookie but missing an argument");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "valid cookie but missing an argument");
		}
	}
	
	//No cookie
	@Test
	public void testExecute9() {
		JSONObject args = new JSONObject();
		args.put("name", "Steve");
		args.put("randomTiles", false);
		args.put("randomNumbers", false);
		args.put("randomPorts", false);
		String cookie = "";
		create createCommand = new create();
		try {
			createCommand.execute(args, cookie);
			fail("Failed create command test when passed "
					+ "valid arguments but no cookie");
		} catch (ServerAccessException e) {
			System.out.println("Passed create command test when passed "
					+ "valid arguments but no cookie");
		}
	}
}
