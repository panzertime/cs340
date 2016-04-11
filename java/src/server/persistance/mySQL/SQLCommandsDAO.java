package server.persistance.mySQL;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import server.command.moves.MovesCommand;
import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;
import server.persistance.IConnection;

public class SQLCommandsDAO implements ICommandsDAO {

	@Override
	public void saveCommmand(IConnection connection, Integer gameID, MovesCommand movesCommand)
			throws DatabaseException {
		if (movesCommand == null)
			throw new DatabaseException();
		if (connection == null)
			throw new DatabaseException();
		SQLConnection sqlconnection = (SQLConnection) connection;
		
		PreparedStatement stmt = null;
		// ResultSet rs = null;
		try {
			String query = "INSERT INTO command (gameID, movesCommand) VALUES (?, ?)";
			stmt = sqlconnection.prepareStatement(query);
			stmt.setInt(1, gameID);
			stmt.setBytes(2, movesCommand.getArguments().toJSONString().getBytes());
			if (stmt.executeUpdate() != Statement.EXECUTE_FAILED) {
			} else {
				throw new DatabaseException("Could not insert command");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not insert command", e);
		} finally {
			sqlconnection.safeClose(stmt);
		}

	}

	@Override
	public List<JSONObject> getCommands(IConnection connection, Integer gameID) throws DatabaseException {
		if (connection == null)
			throw new DatabaseException();
		if (gameID == null)
			throw new DatabaseException();
		SQLConnection sqlconnection = (SQLConnection) connection;
		
		List<JSONObject> commands = new ArrayList<JSONObject>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select movesCommand from command where gameID = ?";
			stmt = sqlconnection.prepareStatement(query);
			stmt.setInt(1, gameID);

			rs = stmt.executeQuery();
			while (rs.next()) {
				byte[] bytes = rs.getBytes(1);
				String JSONString = new String(bytes);

				JSONParser jp = new JSONParser();
				JSONObject commandJSON;
				try {
					commandJSON = (JSONObject) jp.parse(JSONString);
					commands.add(commandJSON);
				} catch (ParseException e) {
					throw new DatabaseException();
				}
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		} finally {
			sqlconnection.safeClose(rs);
			sqlconnection.safeClose(stmt);
		}
		return commands;
	}

	@Override
	public void deleteCommands(IConnection connection, Integer gameID) throws DatabaseException {
		if (gameID == null) throw new DatabaseException();
		SQLConnection sqlconnection = (SQLConnection) connection;
		
		PreparedStatement stmt = null;
		try {
			String query = "delete from command where gameID = ?";
			stmt = sqlconnection.prepareStatement(query);
			stmt.setInt(1, gameID);
			if (stmt.executeUpdate() != Statement.EXECUTE_FAILED) {
			} else {
				throw new DatabaseException("Could not delete commands");
			}
		}catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		} finally {
			sqlconnection.safeClose(stmt);
		}
	}

}
