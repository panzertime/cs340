package server.serverkernel;

import static org.junit.Assert.*;

import org.junit.Test;

import server.data.ServerData;
import server.data.User;
import server.exception.ServerAccessException;
import server.exception.UserException;

public class ServerKernel {

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
	public void testUserExists() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetGames() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUserIsInGame() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGameExists() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetGame() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testPutGame() {
		fail("Not yet implemented"); // TODO
	}

}
