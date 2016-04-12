package server.persistance.files;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import server.data.User;
import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IUsersDAO;

public class FileUsersDAO implements IUsersDAO {

	@Override
	public void saveUser(IConnection connection, User user) throws DatabaseException {
		try {
			PrintWriter writer = new PrintWriter(user.getID() + ".txt", "UTF-8");
			writer.println(user.toJSON().toJSONString());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new DatabaseException();
		}
		

	}

	@Override
	public List<User> getUsers(IConnection connection) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
