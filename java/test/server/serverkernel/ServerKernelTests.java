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

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.exception.UserException;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class ServerKernelTests {

	/*TODO
	 * Get these to run in order 
	 */
	@Test
	public void testAddUser1() {
		User user = new User("Joshua", "joshua");
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
		if(ServerKernel.sole().getNumOfGames() == 0) {
			System.out.println("Passed adding zero games test");
		} else {
			fail("Failed adding zero games test");
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
	public void testGetGames() {
		fail("Not yet implemented"); // TODO
	}
}
