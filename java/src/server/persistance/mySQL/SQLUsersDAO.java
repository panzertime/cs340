package server.persistance.mySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import server.data.User;
import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IUsersDAO;

public class SQLUsersDAO implements IUsersDAO {

	@Override
	public void saveUser(IConnection connection, User user) throws DatabaseException {
		if (user == null)
			throw new DatabaseException();
		if (connection == null)
			throw new DatabaseException();
		SQLConnection sqlconnection = (SQLConnection) connection;
		
		PreparedStatement stmt = null;
		// ResultSet rs = null;
		try {
			String query = "INSERT INTO user (userID, username, password) VALUES (?, ?, ?)";
			stmt = sqlconnection.prepareStatement(query);
			stmt.setInt(1, user.getID());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, user.getPassword());
			if (stmt.executeUpdate() != Statement.EXECUTE_FAILED) {
			} else {
				throw new DatabaseException("Could not insert user");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not insert user", e);
		} finally {
			sqlconnection.safeClose(stmt);
		}

	}

	@Override
	public List<User> getUsers(IConnection connection) throws DatabaseException {
		if (connection == null)
			throw new DatabaseException();
		SQLConnection sqlconnection = (SQLConnection) connection;
		
		List<User> users = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select userID, username, password from user";
			stmt = sqlconnection.prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int userID = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);

				users.add(new User(userID, username, password));
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		} finally {
			sqlconnection.safeClose(rs);
			sqlconnection.safeClose(stmt);
		}

		return users;
	}

}
