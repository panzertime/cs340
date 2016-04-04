package server.persistance;

import java.util.List;

import server.data.User;

public interface UsersDAO {
	
	/**
	 * @param user user to be saved in the database
	 * @post user is saved in the database
	 */
	public void saveUser(User user);
	
	/**
	 * @return List all users in the database
	 */
	public List<User> getUsers();

}
