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
		JSONObject args = new JSONObject();
		args.put("username", "Joshua");
		args.put("password", "joshua");
		String cookie = null;
		register r = new register();
		try {
			r.execute(args, cookie);
			System.out.println("Passed register test where "
					+ "1 user added");
		} catch (ServerAccessException e) {
			fail("Failed register test where "
					+ "1 user added");
		}
	}
	
	//valid
	@Test
	public void testExecute1() {
		JSONObject args = new JSONObject();
		args.put("username", "Joshua");
		args.put("password", "joshua");
		
		JSONObject args2 = new JSONObject();
		args2.put("username", "Andrea");
		args2.put("password", "andrea");
		String cookie = "";
		register r = new register();
		try {
			r.execute(args, cookie);
			r.execute(args2, cookie);
			System.out.println("Passed register test where "
					+ "2 users added");
		} catch (ServerAccessException e) {
			fail("Failed register test where "
					+ "2 users added");
		}
	}
	
	//valid
	@Test
	public void testExecute5() {
		JSONObject args = new JSONObject();
		args.put("username", "Joshua");
		args.put("password", "joshua");
		
		JSONObject args2 = new JSONObject();
		args2.put("username", "joshua");
		args2.put("password", "joshua");
		String cookie = "";
		register r = new register();
		try {
			r.execute(args, cookie);
			r.execute(args2, cookie);
			System.out.println("Passed register test where "
					+ "2 users added with same name but different cases");
		} catch (ServerAccessException e) {
			fail("Failed register test where "
					+ "2 users added with same name but different cases");
		}
	}

	
	//invalid
	@Test
	public void testExecute2() {
		JSONObject args = new JSONObject();
		args.put("username", "Joshua");
		String cookie = null;
		register r = new register();
		try {
			r.execute(args, cookie);
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
		JSONObject args = new JSONObject();
		args.put("username", "");
		args.put("password", "joshua");
		String cookie = null;
		register r = new register();
		try {
			r.execute(args, cookie);
			fail("Failed register test where "
					+ "username is null");
		} catch (ServerAccessException e) {
			System.out.println("Passed register test where "
					+ "username is null");
		}
	}
	
	//invalid
	@Test
	public void testExecute4() {
		JSONObject args = new JSONObject();
		args.put("username", "Joshua");
		args.put("password", "joshua");
		
		JSONObject args2 = new JSONObject();
		args2.put("username", "Joshua");
		args2.put("password", "andrea");
		String cookie = "";
		register r = new register();
		try {
			r.execute(args, cookie);
			r.execute(args2, cookie);
			fail("Failed register test where "
					+ "Same username used twice with different password");
		} catch (ServerAccessException e) {
			System.out.println("Passed register test where "
					+ "Same username used twice with different password");		
		}
	}
}
