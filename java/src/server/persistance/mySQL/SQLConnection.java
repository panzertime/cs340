package server.persistance.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.persistance.DatabaseException;
import server.persistance.IConnection;

public class SQLConnection implements IConnection {

	private Connection connection;

	public PreparedStatement prepareStatement(String query) throws DatabaseException {
		try {
			return connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Could not prepare statement");
		}
	}

	public void safeClose(PreparedStatement stmt) throws DatabaseException {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Could not close statement");
		}
	}

	public void safeClose(ResultSet rs) throws DatabaseException {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Could not close resultsSets");
		}
	}
}
