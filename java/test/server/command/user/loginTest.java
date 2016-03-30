package server.command.user;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;

public class loginTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ServerKernel.sole().reinitAll();		
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
	
	//valid
	@Test
	public void testExecute() {
		JSONObject args = new JSONObject();
		args.put("username", "Joshua");
		args.put("password", "joshua");
		String cookie = null;
		login l = new login();
		try {
			l.execute(args, cookie);
			System.out.println("Passed login test where "
					+ "user exists: Joshua");
		} catch (ServerAccessException e) {
			fail("Failed login test where "
					+ "user exists: Joshua");
		}
	}
	
	//valid
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("username", "Sam");
		args.put("password", "sam");
		String cookie = null;
		login l = new login();
		try {
			l.execute(args, cookie);
			System.out.println("Passed login test where "
					+ "user exists: Sam");
		} catch (ServerAccessException e) {
			fail("Failed login test where "
					+ "user exists: Sam");
		}
	}
	
	//valid
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("username", "Johnathon");
		args.put("password", "johnathon");
		String cookie = null;
		register r = new register();
		login l = new login();
		try {
			r.execute(args, cookie);
			l.execute(args, cookie);
			System.out.println("Passed login test where "
					+ "user registers then logs in");
		} catch (ServerAccessException e) {
			fail("Failed login test where "
					+ "user registers then logs in");
		}
	}
	
	//valid
	@Test
	public void testExecute3() {
		JSONObject args = new JSONObject();
		args.put("username", "sam");
		args.put("password", "sam");
		String cookie = null;
		login l = new login();
		try {
			l.execute(args, cookie);
			fail("Failed login test where "
					+ "username has wrong cases");
		} catch (ServerAccessException e) {
			System.out.println("Passed login test where "
					+ "username has wrong cases");
		}
	}
	
	//valid
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("username", "sam");
		args.put("password", "sAm");
		String cookie = null;
		login l = new login();
		try {
			l.execute(args, cookie);
			fail("Failed login test where "
					+ "password has wrong cases");
		} catch (ServerAccessException e) {
			System.out.println("Passed login test where "
					+ "password has wrong cases");
		}
	}
	
	//valid
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("username", "");
		args.put("password", "sam");
		String cookie = null;
		login l = new login();
		try {
			l.execute(args, cookie);
			fail("Failed login test where "
					+ "username is empty String");
		} catch (ServerAccessException e) {
			System.out.println("Passed login test where "
					+ "username is empty String");
		}
	}
	
	//valid
	@Test
	public void testExecute6() {
		JSONObject args = new JSONObject();
		args.put("username", "sam");
		String cookie = null;
		login l = new login();
		try {
			l.execute(args, cookie);
			fail("Failed login test where "
					+ "password dne");
		} catch (ServerAccessException e) {
			System.out.println("Passed login test where "
					+ "password dne");
		}
	}

}
