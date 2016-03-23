package server.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import server.data.User;
import shared.model.Model;

public class CatanCookieTest {
	
	public boolean checkfields(CatanCookie cc, String name, String password, 
			Integer userID, Integer gameID) {
		boolean result = false;
		if(cc.getName().equals(name) &&
				cc.getPassword().equals(password) &&
				cc.getUserID() == userID) {
			result = true;
		}
		
		return result;
	}

	@Test
	public void testCatanCookie0() {
		try {
			String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
					+ ",\"playerID\":0}";
			CatanCookie cc = new CatanCookie(cookie, true);
			if(checkfields(cc, "Sam", "sam", 0, null)) {
				System.out.println("Passed CatanCookie test with good pre-game"
						+ "cookie");
			} else {
				fail("Failed CatanCookie test with good pre-game cookie");
			}
		} catch (CookieException e) {
			fail("Failed CatanCookie test with good pre-game cookie");
		}
	}

	@Test
	public void testCatanCookie1() {
		try {
			String cookie = "catan.user={\"password\":\"sam\",\"playerID\":0};";
			new CatanCookie(cookie, true);
			fail("Failed CatanCookie test with bad pre-game cookie - missing "
					+ "name field");
		} catch (CookieException e) {
			System.out.println("Passed CatanCookie test with pre-game "
					+ "cookie - missing name field");
		}
	}
	
	@Test
	public void testCatanCookie2() {
		try {
			String cookie = "={\"name\":\"Sam\",\"password\":\"sam\","
					+ "\"playerID\":0}";
			new CatanCookie(cookie, true);
			fail("Failed CatanCookie test with bad pre-game cookie missing - "
					+ "catan.user");
		} catch (CookieException e) {
			System.out.println("Passed CatanCookie test with pre-game "
					+ "cookie missing - catan.user");
		}
	}
	
	@Test
	public void testCatanCookie3() {
		try {
			String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
					+ ",\"playerID\":0}; catan.game=09";
			CatanCookie cc = new CatanCookie(cookie, false);
			if(checkfields(cc, "Sam", "sam", 0, 9) &&
					cc.getGameID() == 9) {
				System.out.println("Passed CatanCookie test with good "
						+ "post-join-game cookie");
			}
		} catch (CookieException e) {
			fail("Failed CatanCookie test with good pre-game cookie");
		}
	}

	@Test
	public void testCatanCookie4() {
		try {
			String cookie = "catan.user={\"name\":\"Sam\",\"playerID\":0}; "
					+ "catan.game=09";
			CatanCookie cc = new CatanCookie(cookie, false);
			fail("Failed CatanCookie test with bad post-join-game cookie - no "
					+ "password field");
		} catch (CookieException e) {
			System.out.println("Passed CatanCookie test with bad "
					+ "post-join-game cookie - no password field");
		}
	}
	
	@Test
	public void testCatanCookie5() {
		try {
			String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\""
					+ ",\"playerID\":0}; catan.game";
			CatanCookie cc = new CatanCookie(cookie, false);
			fail("Failed CatanCookie test with bad post-join-game cookie - no "
					+ "=NN");
		} catch (CookieException e) {
			System.out.println("Passed CatanCookie test with bad "
					+ "post-join-game cookie - no =NN");
		}
	}
	
	//Good user attributes
	@Test public void toCookie1() {
		User user = new User("Bob", "bob", 1);
		try {
			CatanCookie cookie = new CatanCookie(user);
			String cookieString = cookie.toCookie();
			CatanCookie retCookie = new CatanCookie(cookieString, true);
			if(retCookie.getName().equals("Bob") &&
					retCookie.getPassword().equals("bob") &&
					retCookie.getUserID() == 1) {
				System.out.println("Passed cookie test when converting a "
						+ "valid user to a Set-Cookie statement");
			} else {
				fail("Failed cookie test when converting a valid user to a "
						+ "Set-Cookie statement");
			}
		} catch (CookieException e) {
			fail("Failed cookie test when converting a valid user to a "
					+ "Set-Cookie statement");
		}
	}
	
	//Good game attributes
	@Test public void toCookie2() {
		Model game = new Model(true, true, true, "test");
		game.setID(1);
		try {
			CatanCookie cookie = new CatanCookie(game);
			String cookieString = cookie.toCookie();
			if(cookieString.equals("catan.game=1;Path=/;")) {
				System.out.println("Passed cookie test when converting a "
						+ "valid game to a Set-Cookie statement");
			} else {
				fail("Failed cookie test when converting a valid game to a "
						+ "Set-Cookie statement");
			}
		} catch (CookieException e) {
			fail("Failed cookie test when converting a valid game to a "
					+ "Set-Cookie statement");
		}
	}
	
	
	//Missing one user attribute
	@Test public void toCookie3() {
		User user = new User("Bob", null, 1);		
		try {
			CatanCookie cookie = new CatanCookie(user);
			String cookieString = cookie.toCookie();
			fail("Passed cookie test when converting an "
					+ "invalid user (missing password) to a Set-Cookie "
					+ "statement");
		} catch (CookieException e) {
			System.out.println("Passed cookie test when converting an "
					+ "invalid user (missing password) to a Set-Cookie "
					+ "statement");
		}
	}
	
	//User passed in as null
	@Test public void toCookie4() {
		User user = null;
		try {
			CatanCookie cookie = new CatanCookie(user);
			String cookieString = cookie.toCookie();
			fail("Passed cookie test when converting an "
					+ "invalid user (null) to a Set-Cookie "
					+ "statement");
		} catch (CookieException e) {
			System.out.println("Passed cookie test when converting an "
					+ "invalid user (null) to a Set-Cookie "
					+ "statement");
		}
	}
	
	//Game passed in as null
	@Test public void toCookie5() {
		Model game = null;
		try {
			CatanCookie cookie = new CatanCookie(game);
			String cookieString = cookie.toCookie();
			fail("Passed cookie test when converting an "
					+ "invalid game (null) to a Set-Cookie "
					+ "statement");
		} catch (CookieException e) {
			System.out.println("Passed cookie test when converting an "
					+ "invalid game (null) to a Set-Cookie "
					+ "statement");
		}
	}
}
