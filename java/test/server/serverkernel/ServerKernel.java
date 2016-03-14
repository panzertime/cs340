package server.serverkernel;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import server.data.ServerData;
import server.data.User;
import server.exception.ServerAccessException;
import server.exception.UserException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class ServerKernel {

	/*TODO
	 * Get these to run in order 
	 */
	@Test
	public void testAddUser1() {
		User user = new User("Joshua", "joshua");
		try {
			ServerData.sole().addUser(user);
			if(ServerData.sole().userExists(user)){
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
			ServerData.sole().addUser(user);
			ServerData.sole().addUser(user2);
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
			ServerData.sole().addUser(user);
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
			ServerData.sole().addUser(user);
			ServerData.sole().addUser(user2);
			if(ServerData.sole().userExists(user)
					&& ServerData.sole().userExists(user2)){
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
			ServerData.sole().addUser(userAdded);
			if(ServerData.sole().userExists(userAdded)){
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
			ServerData.sole().addUser(user);
			ServerData.sole().addUser(user2);
			if(ServerData.sole().userExists(user)
					&& ServerData.sole().userExists(user2)){
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
			ServerData.sole().addUser(userToBeAdded);
			if(ServerData.sole().userExists(userToBeCheckedFor)){
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

	@Test
	public void testUserIsInGame() {
		fail("Not yet implemented"); // TODO
	}
	
	public Model getDefaultModel() {
		Model model = null;
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
			
			JSONObject jsonModel = (JSONObject) parser.parse(x);
			model = new Model(jsonModel);
			
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			fail("Error init. json from file\n" +
					e.getMessage());
		}
		return model;
	}

	@Test
	public void testPutGame0() {
		if(ServerData.sole().getNumOfGames() == 0) {
			System.out.println("Passed adding zero games test");
		} else {
			fail("Failed adding zero games test");
		}
	}
	
	@Test
	public void testPutGame1() {
		Model model = getDefaultModel();
		ServerData.sole().putGame(model);
		if(ServerData.sole().getNumOfGames() == 1) {
			System.out.println("Passed adding one game test");
		} else {
			fail("Failed adding a game test");
		}
	}
	
	@Test
	public void testGameExists1() {
		if(ServerData.sole().gameExists(0)) {
			System.out.println("Passed making sure previously added"
					+ " game could be accessed test");
		} else {
			fail("Failed making sure previously added game could"
					+ " be accessed test");
		}
	}
	
	@Test
	public void testPutGame2() {
		Model model = getDefaultModel();
		ServerData.sole().putGame(model);
		ServerData.sole().putGame(model);
		if(ServerData.sole().getNumOfGames() == 3) {
			System.out.println("Passed adding two games test");
		} else {
			fail("Failed adding two games test");
		}
	}
	
	@Test
	public void testPutGame3() {
		Model model = getDefaultModel();
		if(ServerData.sole().getNumOfGames() == 3) {
			System.out.println("Passed second adding zero games test");
		} else {
			fail("Failed adding zero games test second time around");
		}
	}
	
	@Test
	public void testGameExists2() {
		if(ServerData.sole().gameExists(0)
				&& ServerData.sole().gameExists(1)
				&& ServerData.sole().gameExists(2)) {
			System.out.println("Passed making sure all three previously added"
					+ " games could be accessed test");
		} else {
			fail("Failed making sure all three previously added games could"
					+ " be accessed test");
		}
	}
	@Test
	public void testGameExists3() {
		if(!ServerData.sole().gameExists(3)) {
			System.out.println("Passed making sure unadded game is does not "
					+ "return added test");
		} else {
			fail("Failed making sure unadded game did not exist test");
		}
	}

	@Test
	public void testGetGames() {
		fail("Not yet implemented"); // TODO
	}
}
