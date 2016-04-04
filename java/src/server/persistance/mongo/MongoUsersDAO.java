package server.persistance.mongo;

import java.util.List;

import server.data.User;
import server.persistance.DatabaseException;
import server.persistance.IUsersDAO;

public class MongoUsersDAO implements IUsersDAO {

	@Override
	public void saveUser(User user) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getUsers() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
