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
	
	public String getPassword() {
		return this.password;
	}
	
	//DEBUG AND TESTING SECTION

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
	public void resetIDs() {
		this.id = 0;
	}
}
