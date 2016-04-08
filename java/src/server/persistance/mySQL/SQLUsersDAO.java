package server.persistance.mySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.data.User;
import server.persistance.DatabaseException;
import server.persistance.IUsersDAO;
import server.persistance.PersistanceManager;

public class SQLUsersDAO implements IUsersDAO {

	PersistanceManager pm;
	
	SQLUsersDAO(PersistanceManager pm) {
		this.pm = pm;
	}
	
	@Override
	public void saveUser(User user) throws DatabaseException {
		if (user == null) throw new DatabaseException();
		PreparedStatement stmt = null;
		//ResultSet rs = null;
		try {
			String query = "INSERT INTO user (userID, username, password) VALUES (?, ?, ?)";
			stmt = pm.getConnection().prepareStatement(query);
			if (stmt.executeUpdate() == 1) {
				stmt.setInt(1, user.getID());
				stmt.setString(2, user.getUsername());
				stmt.setString(3, user.getPassword());
			}
			else {
				throw new DatabaseException("Could not insert user");
			}}
			catch (SQLException e) {
				throw new DatabaseException("Could not insert user", e);
			}
			finally {
				PersistanceManager.safeClose(stmt);
			}
		

	}

	@Override
	public List<User> getUsers() throws DatabaseException {
		List<User> users = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select userID, username, password from user";
			stmt = pm.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int userID = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				
				users.add(new User(userID, username, password));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		}		
		finally {
			PersistanceManager.safeClose(rs);
			PersistanceManager.safeClose(stmt);
		}
		
		return users;
	}

}
