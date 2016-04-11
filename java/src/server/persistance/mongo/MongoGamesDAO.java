package server.persistance.mongo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IGamesDAO;
import shared.logger.Log;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class MongoGamesDAO implements IGamesDAO {

	@Override
	public void saveGame(IConnection connection, Model model) throws DatabaseException {
		if (model == null)
			throw new DatabaseException();
		if (connection == null)
			throw new DatabaseException();
		MongoConnection mongoConnection = (MongoConnection) connection;

		DBCursor cursor = mongoConnection.getGamesCollection().find((DBObject) JSON.parse("{id:" + model.getID() + "}"));
		if (cursor.hasNext())
			mongoConnection.getGamesCollection().remove(cursor.next());
		
		mongoConnection.getGamesCollection().insert((DBObject) JSON.parse(model.toJSON().toJSONString()));
	}

	@Override
	public List<Model> getGames(IConnection connection) throws DatabaseException {
		if (connection == null)
			throw new DatabaseException();
		List<Model> games = new ArrayList<Model>();

		MongoConnection mongoConnection = (MongoConnection) connection;

		DBCursor cursor = mongoConnection.getGamesCollection().find();
		while (cursor.hasNext()) {
			try {
				games.add(new Model((JSONObject) cursor.next()));
			} catch (BadJSONException e) {
				Log.error("Mongo DB is ill formated, unable to interpret a game");
				e.printStackTrace();
			}
		}
		return games;
	}
}
