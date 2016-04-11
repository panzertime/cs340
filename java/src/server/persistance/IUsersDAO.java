package server.persistance;

import java.util.List;

import server.data.User;

public interface IUsersDAO {
	
	/**
	 * @param user user to be saved in the database
	 * @post user is saved in the database
	 * @throws DatabaseException
	 */
	public void saveUser(IConnection connection, User user) throws DatabaseException;
	
	/**
	 * @return List all users in the database
	 * @throws DatabaseException
	 */
	public List<User> getUsers(IConnection connection) throws DatabaseException;

}
