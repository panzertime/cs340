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
			String query = "select gameBlob from game where gameID = ?";
			stmt = sqlconnection.prepareStatement(query);
			stmt.setInt(1, model.getID());
			rs = stmt.executeQuery();
			if (!rs.next()) {
				try {
					query = "INSERT INTO game (gameID, gameBlob) VALUES (?, ?)";
					stmt = sqlconnection.prepareStatement(query);
					stmt.setInt(1, model.getID());
					stmt.setBytes(2, model.toJSON().toJSONString().getBytes());
					if (stmt.executeUpdate() != Statement.EXECUTE_FAILED) {
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
					query = "update game set gameBlob = ? where gameID = ?";
					stmt = sqlconnection.prepareStatement(query);
					stmt.setBytes(1, model.toJSON().toJSONString().getBytes());
					stmt.setInt(2, model.getID());
					if (stmt.executeUpdate() != Statement.EXECUTE_FAILED) {
					} else {
						throw new DatabaseException("Could not update game");
					}
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
				byte[] bytes = rs.getBytes(1);
				String JSONString = new String(bytes);
				
				try {
					JSONParser jp = new JSONParser();
					JSONObject gameJSON = (JSONObject) jp.parse(JSONString);

					games.add(new Model(gameJSON));
				} catch (ParseException | BadJSONException e) {
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
