package server.data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import server.exception.UserException;

public class User {
	
	private static Integer idToBeAssigned;
	
	/**
	 * Used to generate a new user id for a user
	 * @pre the user has been 
	 * @post the next ID to be assigned has been incremented
	 * @return id to be assigned to a user
	 */
	private static int setID() {
		if(idToBeAssigned == null) {
			idToBeAssigned = 0;
		}
		return idToBeAssigned++;
	}

	private String username;
	private String password;
	private Integer id;
	
	/**
	 * Create a user object from a username and password
	 * @pre username and password are not empty
	 * @post User object is created
	 * @param username username
	 * @param password users password
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		id = null;
	}
	
	/**
	 * Creates a user form a handed cookie
	 * @pre cookie is valid, nonempty string, already decoded URL
	 * @post user object is created with the given parameters
	 * @param cookie cookie handed to the server by the client
	 * @throws UserException Cookie passed in is invalid
	 */
	public User(String cookie) throws UserException {
		if(cookie.startsWith("catan.user=")
				|| cookie.endsWith(";Path=/;") 
				|| !cookie.isEmpty()) {
			int startIndex = cookie.indexOf("{");
			int endIndex = cookie.indexOf("}");
			String toJSON = cookie.substring(startIndex, endIndex);
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
					throw new UserException("Cookie has null JSON params");
				} else {
					this.id = id.intValue();
					this.username = name;
					this.password = password;
				}
			} catch (ParseException e) {
				throw new UserException("Cookie passed in has malformed JSON");
			}
			
		} else {
			throw new UserException("Cookie passed in is not in the required"
					+ "format.");
		}
	}

	/**
	 * Used to assign a user an ID
	 * @pre user should not have an ID
	 * @post the userID is set
	 * @throws UserException User already had ID
	 */
	public void setUserID() throws UserException {
		if(id == null) {
			this.id = User.setID();
		}
		else {
			throw new UserException("User already assigned an ID");
		}
	}
	

	//TODO This may need to be refactored to be used with a UTILS class
	//in order to keep the HTML cookie encoding out of it.
	/**
	 * Converts the given user to a cookie
	 * @pre all fields in user are valid(non-empty string and has ID)
	 * @post none
	 * @return String to be used by the URL Encoder on the server to return to
	 * the client.
	 * @throws UserException User fields are incomplete or invalid
	 */
	public String toCookie() throws UserException {
		if(this.username.isEmpty()
				|| this.password.isEmpty()
				|| this.id == null) {
			throw new UserException("User is missing required data");
		}
		JSONObject cookie = new JSONObject();
		cookie.put("name", this.username);
		cookie.put("password", this.password);
		cookie.put("playerID", this.id);
		
		StringBuilder result = new StringBuilder("catan.user=");
		result.append(cookie.toJSONString());
		result.append(";Path=/;");
		
		return result.toString();
	}

	/**
	 * Generates a hashcode for each user
	 * @pre user exists with all three fields filled out
	 * @post hashcode is generated
	 */
	@Override
	public int hashCode() {
		return id;
	}

	/**
	 * Checks to see if a two users are equal
	 * @pre none
	 * @post returns true if two users are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		//We only care about user name
		/*if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;*/
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	/**
	 * Used to make sure the username and password field have been filled
	 * out and do not contain empty strings. Also
	 * @pre strings username and password have been created by the constructor
	 * @post nothing
	 * @return whether or not username and password were set up with non-empty
	 * strings
	 */
	public boolean hasValidCrendentials() {
		boolean result = true;
		if(this.username.isEmpty() || this.password.isEmpty()) {
			result = false;
		}
		return result;
	}

	public String getUsername() {
		return this.username;
	}

	/**
	 * get User ID
	 * @pre none
	 * @post id is returned(can be null)
	 * @return user ID
	 */
	public Integer getID() {
		return this.id;
	}	
}
