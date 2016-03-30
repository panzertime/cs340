package server.command.user;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.command.moves.sendChat;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.exception.UserException;

public class registerTest {
	
	@Before
	public void setUp() throws Exception {
		ServerKernel.sole().reinitAll();
	}
	
	//valid
	@Test
	public void testExecute() {
		User user = new User("Joshua","Joshua");
		try {
			ServerKernel.sole().addUser(user);
			if(ServerKernel.sole().userExists(user)) {
				System.out.println("Passed register test where "
						+ "1 user added");
			} else {
				fail("Failed register test where "
						+ "1 user added");
			}
		} catch (ServerAccessException | UserException e) {
			fail("Failed register test where "
					+ "1 user added");
		}
	}
	
	//valid
	@Test
	public void testExecute1() {
		User user = new User("Joshua","andrea");
		User user2 = new User("Andrea","andrea");
		try {
			ServerKernel.sole().addUser(user);
			ServerKernel.sole().addUser(user2);
			if(ServerKernel.sole().userExists(user) &&
					ServerKernel.sole().userExists(user2)) {
				System.out.println("Passed register test where "
						+ "2 users added");
			} else {
				fail("Failed register test where "
						+ "2 users added");
			}
		} catch (ServerAccessException | UserException e) {
			fail("Failed register test where "
					+ "2 users added");
		}
	}
	
	//valid
	@Test
	public void testExecute5() {
		User user = new User("Joshua","joshua");
		User user2 = new User("joshua","joshua");
		try {
			ServerKernel.sole().addUser(user);
			if(ServerKernel.sole().userExists(user)) {
				System.out.println("Passed register test where "
						+ "2 users added with same name but different cases");
			} else {
				fail("Failed register test where "
						+ "2 users added with same name but different cases");
			}
		} catch (ServerAccessException | UserException e) {
			fail("Failed register test where "
					+ "2 users added with same name but different cases");
		}
	}

	
	//invalid
	@Test
	public void testExecute2() {
		User user = new User("joshua","");
		try {
			ServerKernel.sole().addUser(user);
			fail("Failed register test where "
					+ "user has no password");
		} catch (ServerAccessException e) {
			System.out.println("Passed register test where "
					+ "user has no password");
		}
	}
	
	//invalid
	@Test
	public void testExecute3() {
		User user = new User("","joshua");
		try {
			ServerKernel.sole().addUser(user);
			fail("Failed register test where "
					+ "user has no username");
		} catch (ServerAccessException e) {
			System.out.println("Passed register test where "
					+ "user has no username");
		}
	}
	
	//invalid
	@Test
	public void testExecute4() {
		User user = new User("Joshua","Joshua");
		User user2 = new User("Joshua","Joshua");
		try {
			ServerKernel.sole().addUser(user);
			ServerKernel.sole().addUser(user2);
			fail("Failed register test where "
					+ "same user added twice");
		} catch (ServerAccessException e) {
			System.out.println("Passed register test where "
					+ "same user added twice");
		}
	}
}
