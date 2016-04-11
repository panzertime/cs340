package server.persistance.mySQL;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IGamesDAO;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class SQLGamesDAO implements IGamesDAO {

	@Override
	public void saveGame(IConnection connection, Model model) throws DatabaseException {
		if (model == null)
			throw new DatabaseException();
		if (connection == null)
			throw new DatabaseException();
		SQLConnection sqlconnection = (SQLConnection) connection;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select gameBlog from game where gameID = ?";
			stmt = sqlconnection.prepareStatement(query);
			stmt.setInt(1, model.getID());
			rs = stmt.executeQuery();
			if (!rs.next()) {
				try {
					query = "INSERT INTO game (gameID, gameBlob) VALUES (?)";
					stmt = sqlconnection.prepareStatement(query);
					if (stmt.executeUpdate() == 1) {
						stmt.setInt(1, model.getID());
						stmt.setBlob(2, (Blob) model.toJSON());
					} else {
						throw new DatabaseException("Could not insert game");
					}
				} catch (SQLException e) {
					throw new DatabaseException("Could not insert game", e);
				} finally {
					sqlconnection.safeClose(stmt);
				}
			} else {
				try {
					query = "update game set gameBlog = ? where gameID = ?";
					stmt = sqlconnection.prepareStatement(query);
					stmt.setBlob(1, (Blob) model.toJSON());
					stmt.setInt(2, model.getID());
				} catch (SQLException e) {
					throw new DatabaseException("Could not update game", e);
				} finally {
					sqlconnection.safeClose(stmt);
				}
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		} finally {
			sqlconnection.safeClose(rs);
			sqlconnection.safeClose(stmt);
		}
	}

	@Override
	public List<Model> getGames(IConnection connection) throws DatabaseException {
		if (connection == null)
			throw new DatabaseException();
		SQLConnection sqlconnection = (SQLConnection) connection;
		
		List<Model> games = new ArrayList<Model>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select gameBlob from game";
			stmt = sqlconnection.prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject gameJSON = (JSONObject) rs.getBlob(1);

				try {
					games.add(new Model(gameJSON));
				} catch (BadJSONException e) {
					throw new DatabaseException(e);
				}
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		} finally {
			sqlconnection.safeClose(rs);
			sqlconnection.safeClose(stmt);
		}

		return games;
	}

}
