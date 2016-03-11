package server.data;

public class User {
	
	private static Integer idToBeAssigned;
	
	/**
	 * Used to generate a new user id for a user
	 * @pre the user has been 
	 * @post the next ID to be assigned has been incremented
	 * @return id to be assigned to a user
	 */
	public static int getNewID() {
		if(idToBeAssigned == null) {
			idToBeAssigned = 0;
		}
		return idToBeAssigned++;
	}

	private String username;
	private String password;
	private int id;
	
	/**
	 * Create a user object from a username and password
	 * @pre username and password are not empty
	 * @post User object is created
	 * @param username username
	 * @param password users password
	 */
	public User(String username, String password) {
		
	}
	
	/**
	 * Creates a user form a handed cookie
	 * @pre cookie is valid, nonempty string
	 * @post user object is created with the given parameters
	 * @param cookie cookie handed to the server by the client
	 */
	public User(String cookie) {
		
	}

	/**
	 * Used to assign a user an ID
	 * @pre id has come from getNewID function
	 * @post the userID is set
	 * @param id userID to be changed to
	 */
	public void setUserID(int id) {
		this.id = id;
	}

	/**
	 * Converts the given user to a cookie
	 * @pre all fields in user are non-null
	 * @post none
	 * @return Cookie String to be used by the server to return to the client
	 */
	public String toCookie() {
		return password;
	}

	/**
	 * Generates a hashcode for each user
	 * @pre user exists with all three fields filled out
	 * @post hashcode is generated
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
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
	
	
}
