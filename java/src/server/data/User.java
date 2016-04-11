package server.data;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

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
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

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
	 * This constructor should only be used to create a user from a valid 
	 * cookie
	 * @pre all attributes are valid
	 * @post a new user with the given params is created
	 * @param name username
	 * @param password password
	 * @param id ID
	 */
	public User(String name, String password, Integer id) {
		this.username = name;
		this.password = password;
		this.id = id;
	}
	
	public User()
	{
		
	}

	public User(int userID, String username, String password) {
		this.setUserID(userID);
		this.username = username;
		this.password = password;
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
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
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
	
	public String getPassword() {
		return this.password;
	}
	
	//PHASE 4
	public User(JSONObject jsonUser) throws UserException {
		try {
			this.id = ((Long) jsonUser.get("id")).intValue();
			this.username = (String) jsonUser.get("username");
			this.password = (String) jsonUser.get("password");
		} catch(Exception e) {
			throw new UserException("Invalid JSON");
		}
	}
	
	public JSONObject toJSON() {
		Map<Object,Object> jsonObject = new HashMap<Object,Object>();
		jsonObject.put("id", this.id);
		jsonObject.put("username", this.username);
		jsonObject.put("password", this.password);
		JSONObject result = new JSONObject(jsonObject);
		return result;
	}

	//DEBUG AND TESTING SECTION

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * TESTING AND DEBUGGING ONLY: This is to be used for tests to make sure 
	 * that a newly created user matches an already existing game
	 * @pre none
	 * @post id is set
	 * @param id ID
	 */
	public void setUserID(int id) {
		this.id = id;
	}
	
	/**
	 * TESING AND DEBUGGING ONLY:
	 * Used to reset static ID value to initial value of 0
	 * @pre none
	 * @post ids to be assigned will be reset to 0
	 */
	static public void resetIDs() {
		idToBeAssigned = 0;
	}
}
