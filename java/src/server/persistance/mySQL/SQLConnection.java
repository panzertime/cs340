package server.persistance.mySQL;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.persistance.DatabaseException;
import server.persistance.IConnection;

public class SQLConnection implements IConnection {

	
	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "catan.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
											 File.separator +  DATABASE_FILE;
	
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

	@Override
	public void safeClose() {
		if (this.connection != null) {
			try {
				connection.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
		
	}

	@Override
	public void commit() throws DatabaseException {
		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException(e);
		}
	}

	@Override
	public void rollback() throws DatabaseException {

		try {
			connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException(e);		}
	}

	@Override
	public void startTransaction() throws DatabaseException {
		this.initialize();
		try {
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
		
	}
	
	public void initialize() throws DatabaseException {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			throw serverEx; 
		}
	}
}
