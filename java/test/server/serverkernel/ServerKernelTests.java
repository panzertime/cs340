package server.serverkernel;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.exception.UserException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class ServerKernelTests {

	/*TODO
	 * Get these to run independently
	 */
	@Test
	public void testAddUser1() {
		User user = new User("Sam", "sam");
		try {
			ServerKernel.sole().addUser(user);
			if(ServerKernel.sole().userExists(user)){
				System.out.println("Passed adding a valid user test");
				return;
			} else {
				fail("User not added correctly");
			}
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (ServerAccessException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testAddUser2() {
		User user = new User("Joshua", "joshua");
		User user2 = new User("Joshua", "elephant");
		try {
			ServerKernel.sole().addUser(user);
			ServerKernel.sole().addUser(user2);
		} catch (UserException e) {
			e.printStackTrace();
		} catch (ServerAccessException e) {
			System.out.println("Passed adding the same user twice test");
			return;
		}
		fail("Did not catch error in adding already existing user");
	}
	
	@Test
	public void testAddUser3() {
		User user = new User("", "joshua");
		try {
			ServerKernel.sole().addUser(user);
		} catch (UserException e) {
			System.out.println("Passed adding an invalid user test");
			return;
		} catch (ServerAccessException e) {
			fail(e.getMessage());
		}
		fail("Did not catch error in adding invalid user");
	}
	
	@Test
	public void testAddUser4() {
		User user = new User("Daniel", "daniel");
		User user2 = new User("Jacob", "jacob");
		try {
			ServerKernel.sole().addUser(user);
			ServerKernel.sole().addUser(user2);
			if(ServerKernel.sole().userExists(user)
					&& ServerKernel.sole().userExists(user2)){
				System.out.println("Passed adding two valid users test");
				return;
			} else {
				fail("Multiple users not added correctly");
			}
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (ServerAccessException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUserExists0() {
		User userAdded = new User("Shadrack", "daniel");
		User userNotAdded = new User("Jacob", "jacob");
		try {
			ServerKernel.sole().addUser(userAdded);
			if(ServerKernel.sole().userExists(userAdded)){
				System.out.println("Passed checking one user exists test");
				return;
			} else {
				fail("did not check if user existed correctly");
			}
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (ServerAccessException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUserExists1() {
		User user = new User("Belthi", "daniel");
		User user2 = new User("Samantha", "jacob");
		try {
			ServerKernel.sole().addUser(user);
			ServerKernel.sole().addUser(user2);
			if(ServerKernel.sole().userExists(user)
					&& ServerKernel.sole().userExists(user2)){
				System.out.println("Passed checking two users exists test");
				return;
			} else {
				fail("did not check if users existed correctly");
			}
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (ServerAccessException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUserExists2() {
		User userToBeAdded = new User("Noe", "daniel");
		User userToBeCheckedFor = new User("Moises", "jacob");
		try {
			ServerKernel.sole().addUser(userToBeAdded);
			if(ServerKernel.sole().userExists(userToBeCheckedFor)){
				fail("Fail - says users exists when it shouldn't");
			} else {
				System.out.println("Passed checking for user who does not "
						+ "exist test");
				return;
			}
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (ServerAccessException e) {
			fail(e.getMessage());
		}
	}
	
	public JSONObject getDefaultJSONModel() {
		JSONObject model = null;
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/test/model/jsonMap.txt");
		FileInputStream fis;
		try {
			fis = new FileInputStream(jsonFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Scanner scanner = new Scanner(bis);
			String x = "";
			while(scanner.hasNextLine()) {
				x += scanner.nextLine();
				x += "\n";
			}
			scanner.close();
			
			model = (JSONObject) parser.parse(x);
			
		} catch (FileNotFoundException | ParseException e) {
			fail("Error init. json from file\n" +
					e.getMessage());
		}
		return model;
	}
	
	public Model getDefaultModel() {
		Model model = null;
		try {
			model = new Model(getDefaultJSONModel());
		} catch (BadJSONException e) {
			fail("Error model from passed json object\n" +
					e.getMessage());
		}
		return model;
	}

	@Test
	public void testPutGame0() {
		if(ServerKernel.sole().getNumOfGames() == 0) {
			System.out.println("Passed adding zero games test");
		} else {
			fail("Failed adding zero games test");
		}
	}
	
	@Test
	public void testGetGames0() {
		if(ServerKernel.sole().getGames().isEmpty()) {
			System.out.println("Passed get games test when no"
					+ " games on server");
		} else {
			fail("Failed get games test when no"
					+ " games on server");
		}
	}
	
	@Test
	public void testPutGame1() {
		Model model = getDefaultModel();
		ServerKernel.sole().putGame(model);
		if(ServerKernel.sole().getNumOfGames() == 1) {
			System.out.println("Passed adding one game test");
		} else {
			fail("Failed adding a game test");
		}
	}
	
	@Test
	public void testGameExists1() {
		if(ServerKernel.sole().gameExists(0)) {
			System.out.println("Passed making sure previously added"
					+ " game could be accessed test");
		} else {
			fail("Failed making sure previously added game could"
					+ " be accessed test");
		}
	}
	
	@Test
	public void testGetGames1() {
		if(ServerKernel.sole().getGames().equals(getProperGamesList(false))) {
			System.out.println("Passed getting games list test with one game");
		} else {
			fail("Failed getting games list test with one game "
					+ "(Check that file is correct)");
		}
	}
	
	@Test
	public void testPutGame2() {
		Model model = getDefaultModel();
		ServerKernel.sole().putGame(model);
		ServerKernel.sole().putGame(model);
		if(ServerKernel.sole().getNumOfGames() == 3) {
			System.out.println("Passed adding two games test");
		} else {
			fail("Failed adding two games test");
		}
	}
	
	@Test
	public void testPutGame3() {
		Model model = getDefaultModel();
		if(ServerKernel.sole().getNumOfGames() == 3) {
			System.out.println("Passed second adding zero games test");
		} else {
			fail("Failed adding zero games test second time around");
		}
	}
	
	@Test
	public void testGameExists2() {
		if(ServerKernel.sole().gameExists(0)
				&& ServerKernel.sole().gameExists(1)
				&& ServerKernel.sole().gameExists(2)) {
			System.out.println("Passed making sure all three previously added"
					+ " games could be accessed test");
		} else {
			fail("Failed making sure all three previously added games could"
					+ " be accessed test");
		}
	}
	@Test
	public void testGameExists3() {
		if(!ServerKernel.sole().gameExists(3)) {
			System.out.println("Passed making sure unadded game is does not "
					+ "return added test");
		} else {
			fail("Failed making sure unadded game did not exist test");
		}
	}
	
	@Test
	public void testUserIsInGame1() {
		User sam = new User("Sam", "sam");
		sam.setUserID(0);
		try {
			if(ServerKernel.sole().userIsInGame(0, sam)) {
				System.out.println("Passed user is in game test when user is in "
						+ "game");
			} else {
				fail("Failed user is in game test when user is in "
						+ "game");
			}
		} catch (ServerAccessException e) {
			fail("Failed user is in game test when user is in "
					+ "game. User Sam not set up correctly");
		}
	}
	
	@Test
	public void testUserIsInGame2() {
		User joshua = new User("Daniel", "daniel");
		joshua.setUserID(2);
		try {
			if(!ServerKernel.sole().userIsInGame(0, joshua)) {
				System.out.println("Passed user is in game test when user is "
						+ "not in game");
			} else {
				fail("Failed user is in game test when user is in "
						+ "game");
			}
		} catch (ServerAccessException e) {
			fail("Failed user is in game test when user is in "
					+ "game. User Sam not set up correctly");
		}
	}
	
	@Test
	public void testUserIsInGame3() {
 		User sam = new User("Sam", "asm");
		sam.setUserID(0);
		try {
			if(ServerKernel.sole().userIsInGame(3, sam)) {
				fail("Failed user is in game test when game is "
					+ "invalid");
			} else {
				fail("Failed user is in game test when game is "
					+ "invalid");
			}
		} catch (ServerAccessException e) {
			System.out.println("Passed user is in game test when game is "
					+ "invalid");
		}
	}

	public JSONArray getProperGamesList(boolean b) {
		JSONArray gamesList = null;
		JSONParser parser = new JSONParser();
		File jsonFile;
		if(b){
			jsonFile = new File("java/test/server/serverkernel/"
					+ "gamesList2.txt");
		} else {
			jsonFile = new File("java/test/server/serverkernel/"
					+ "gamesList.txt");
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(jsonFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Scanner scanner = new Scanner(bis);
			String x = "";
			while(scanner.hasNextLine()) {
				x += scanner.nextLine();
				x += "\n";
			}
			scanner.close();
			Object obj = parser.parse(x);
			
			gamesList = (JSONArray) obj;
			
		} catch (FileNotFoundException | ParseException e) {
			fail("Error init. games list from file\n" +
					e.getMessage());
		}
		return gamesList;
	}
	
	@Test
	public void testGetGames2() {
		if(ServerKernel.sole().getGames().equals(getProperGamesList(true))) {
			System.out.println("Passed getting full games list test");
		} else {
			fail("Failed getting full games list test. "
					+ "(Check that file is correct)");
		}
	}
}
