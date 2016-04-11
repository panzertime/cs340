package server.data;

import org.json.simple.JSONObject;

import server.exception.UserException;
import shared.model.exceptions.BadJSONException;

public class User {
	
	private static Integer idToBeAssigned;
	
	public User(JSONObject jsonObject) throws BadJSONException {
		if (jsonObject == null)
			throw new BadJSONException();
		String username = (String) jsonObject.get("username");
		if (username == null)
			throw new BadJSONException();
		this.username = username;
		String password = (String) jsonObject.get("password");
		if (password == null)
			throw new BadJSONException();
		this.password = password;
		Long id = ((Long) jsonObject.get("userID"));
		if (id == null)
			throw new BadJSONException();
		this.userID = id.intValue();
	}
	
	public JSONObject toJSON() {
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("username", username);
		jsonUser.put("password", password);
		jsonUser.put("userID", userID);
		return jsonUser;
	}

	
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
	private Integer userID;
	
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
		userID = null;
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
		this.userID = id;
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
		if(userID == null) {
			this.userID = User.setID();
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
		return userID;
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
		if (userID != other.userID)
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
		return this.userID;
	}
	
	public String getPassword() {
		return this.password;
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
		this.userID = id;
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
