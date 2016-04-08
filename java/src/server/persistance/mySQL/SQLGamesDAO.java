package server.persistance.mySQL;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import server.data.User;
import server.persistance.DatabaseException;
import server.persistance.IGamesDAO;
import server.persistance.PersistanceManager;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class SQLGamesDAO implements IGamesDAO {

	
	PersistanceManager pm;
	
	public SQLGamesDAO(PersistanceManager pm) {
		this.pm = pm;
	}

	@Override
	public void saveGame(Model model) throws DatabaseException {
		if (model == null) throw new DatabaseException();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select gameBlog from game where gameID = ?";
			stmt = pm.getConnection().prepareStatement(query);
			stmt.setInt(1, model.getID());
			rs = stmt.executeQuery();
			if (!rs.next())
			{
				try {
					query = "INSERT INTO game (gameID, gameBlob) VALUES (?)";
					stmt = pm.getConnection().prepareStatement(query);
					if (stmt.executeUpdate() == 1) {
						stmt.setInt(1, model.getID());
						stmt.setBlob(2, (Blob) model.toJSON());
					}
					else {
						throw new DatabaseException("Could not insert game");
					}}
					catch (SQLException e) {
						throw new DatabaseException("Could not insert game", e);
					}
					finally {
						PersistanceManager.safeClose(stmt);
					}
			}
			else
			{
				try {
					query = "update game set gameBlog = ? where gameID = ?";
					stmt = pm.getConnection().prepareStatement(query);
					stmt.setBlob(1, (Blob) model.toJSON());
					stmt.setInt(2, model.getID());
				}
				catch (SQLException e) {
					throw new DatabaseException("Could not update game", e);
				}
				finally {
					PersistanceManager.safeClose(stmt);

				}
			}} catch (SQLException e) {
				DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
				throw serverEx;
			}
			finally {
				PersistanceManager.safeClose(rs);
				PersistanceManager.safeClose(stmt);
			}		
	}

	@Override
	public List<Model> getGames() throws DatabaseException {
		List<Model> games = new ArrayList<Model>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select gameBlob from game";
			stmt = pm.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject gameJSON = (JSONObject) rs.getBlob(1);
				
				try {
					games.add(new Model(gameJSON));
				} catch (BadJSONException e) {
					throw new DatabaseException(e);
				}
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
		
		return games;
	}

}
