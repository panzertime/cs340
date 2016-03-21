package server.utils;

import static org.junit.Assert.*;

import org.junit.Test;

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
					+ ",\"playerID\":0};Path=/;";
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
			String cookie = "catan.user={\"password\":\"sam\",\"playerID\":0};"
					+ "Path=/;";
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
					+ "\"playerID\":0};Path=/;";
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
			String cookie = "catan.user={\"name\":\"Sam\",\"password\":\"sam\",\"playerID\":0}; catan.game";
			CatanCookie cc = new CatanCookie(cookie, false);
			fail("Failed CatanCookie test with bad post-join-game cookie - no "
					+ "=NN");
		} catch (CookieException e) {
			System.out.println("Passed CatanCookie test with bad "
					+ "post-join-game cookie - no =NN");
		}
	}
}
