package server.persistance.mySQL;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;
import server.data.User;
import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;
import server.persistance.PersistanceManager;

public class SQLCommandsDAO implements ICommandsDAO {

	
	PersistanceManager pm;
	
	
	public SQLCommandsDAO(PersistanceManager pm) {
		this.pm = pm;
	}

	@Override
	public void saveCommmand(Integer gameID, MovesCommand movesCommand) throws DatabaseException {
		if (movesCommand == null) throw new DatabaseException();
		PreparedStatement stmt = null;
		//ResultSet rs = null;
		try {
			String query = "INSERT INTO command (gameID, movesCommand) VALUES (?, ?)";
			stmt = pm.getConnection().prepareStatement(query);
			if (stmt.executeUpdate() == 1) {
				stmt.setInt(1, gameID);
				stmt.setBlog(2, movesCommand.getArguments());
			}
			else {
				throw new DatabaseException("Could not insert command");
			}}
			catch (SQLException e) {
				throw new DatabaseException("Could not insert command", e);
			}
			finally {
				PersistanceManager.safeClose(stmt);
			}

	}

	@Override
	public List<JSONObject> getCommands(Integer gameID) throws DatabaseException {
		List<JSONObject> commands = new ArrayList<JSONObject>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select commandBlog from command where gameID = ?";
			stmt = pm.getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);

			rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject commandJSON = (JSONObject) rs.getBlob(1);
				
				commands.add(commandJSON);
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
		return commands;
	}

}
