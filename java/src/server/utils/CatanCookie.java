package server.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import server.data.User;
import server.exception.UserException;
import shared.model.Model;

/**
 * Handles Cookie Conversion
 *
 */
public class CatanCookie {
	private String name;
	private String password;
	private Integer userID;
	private Integer gameID;
	
	/**
	 * Creates a Cookie from a handed cookie
	 * @pre cookie is valid, nonempty string, already decoded URL
	 * @post cookie object is created with the given parameters
	 * @param cookieString cookie handed to the server by the client in the
	 * form of a string
	 * @param isPreGame whether or not a user has entered a game yet
	 * @throws CookieException Cookie passed in is invalid
	 */
	public CatanCookie(String cookieString, boolean isPreGame)
			throws CookieException {
		if(isPreGame) {
			preGameConstructor(cookieString);
		} else {
			postGameConstructor(cookieString);
		}
	}
	
	private void postGameConstructor(String cookieString) throws CookieException {
		if(validStart(cookieString)
				&& cookieString.matches("(.*)catan.game=([0-9]*)(.*)")) {
			setUserAttributes(cookieString);
			setGameAttributes(cookieString);
		} else {
			throw new CookieException("Cookie passed in is not in the required"
					+ "format.");
		}
	}

	/**
	 * Sets the game params from given cookie
	 * @pre cookie has valid format
	 * @post game id is set
	 * @param cookieString cookie
	 */
	private void setGameAttributes(String cookieString) {
		int equalsIndex = cookieString.indexOf("=", 11);
		int numIndex = equalsIndex + 1;
		String number = cookieString.substring(numIndex);
		
		this.gameID = Integer.parseInt(number);
	}

	private boolean validStart(String cookieString) {
		return cookieString.startsWith("catan.user=")
				&& !cookieString.isEmpty();
	}

	private void preGameConstructor(String cookieString) throws CookieException {
		if(validStart(cookieString)
				&& cookieString.contains("Path=/")) {
			setUserAttributes(cookieString);
			
		} else {
			throw new CookieException("Cookie passed in is not in the required"
					+ "format.");
		}
	}

	/**
	 * Sets the user params from given cookie
	 * @pre cookie has valid format
	 * @post user values are set
	 * @param cookieString cookie
	 * @throws CookieException Malformed cookie
	 */
	private void setUserAttributes(String cookieString) throws CookieException {
		int startIndex = cookieString.indexOf("{");
		int endIndex = cookieString.indexOf("}") + 1;
		String toJSON = cookieString.substring(startIndex, endIndex);
		JSONParser parser = new JSONParser();
		try {
			JSONObject userJSONCredentials = 
					(JSONObject) parser.parse(toJSON);
			
			String name = (String) userJSONCredentials.get("name");
			String password = (String) userJSONCredentials.get("password");
			Long id = (Long) userJSONCredentials.get("playerID");
			
			if(name == null
					|| password == null
					|| id == null) {
				throw new CookieException("Cookie has null JSON params");
			} else {
				this.userID = id.intValue();
				this.name = name;
				this.password = password;
			}
		} catch (ParseException e) {
			throw new CookieException("Cookie passed in has malformed User "
					+ "JSON");
		}
	}
	
	/**
	 * Constructor for CatanCookie when only a user exists
	 * @pre User has username, password and id assigned
	 * @post Creates a CatanCookie with all the user attributes filled out
	 * @param user
	 */
	public CatanCookie(User user) {
		this.name = user.getUsername();
		this.password = user.getPassword();
		this.userID = user.getID();
	}
	
	
	private CatanCookie(Model game) {
		this.gameID = game.getID();
	}
	
	/**
	 * Creates a cookie using the parameters in the constructors (User) or 
	 * (Model)
	 * @pre all the necessary CatanCookie attributes are assigned and valid
	 * (Either the three user fields or the one game ID - both done through
	 * proper constructor and passing in valid parameters)
	 * @post none
	 * @return String representing the unicode value to be parsed and sent to
	 * the end user (Not URL encoded)
	 * @throws CookieException Not all user fields were set
	 */
	public String toCookie() throws CookieException {
		StringBuilder cookie = new StringBuilder("catan.");
		if(this.gameID != null) {
			cookie.append("game=");
			cookie.append(this.gameID);
		} else if(validUserParams()) {
			JSONObject user = userToJSON();
			cookie.append("user=");
			cookie.append(user.toJSONString());
		} else {
			throw new CookieException("Not all attributes were "
					+ "initialized correctly");
		}
		
		cookie.append(";Path=/;");
		
		return cookie.toString();
	}

	/**
	 * @pre all params are valid
	 * @post none
	 * @return JSONObject representing the necessary attributes in a cookie
	 */
	private JSONObject userToJSON() {
		JSONObject user = new JSONObject();
		user.put("name", this.name);
		user.put("password", this.password);
		user.put("playerID", this.userID);
		return user;
	}

	/**
	 * Checks for valid user params
	 * @pre none
	 * @post none
	 * @return User params are non-null and actual values
	 */
	private boolean validUserParams() {
		return this.name != null && !this.name.isEmpty() &&
				this.password != null && !this.password.isEmpty() &&
				this.userID != null;
	}
	
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public Integer getUserID() {
		return userID;
	}

	public int getGameID() {
		return gameID;
	}		
}
