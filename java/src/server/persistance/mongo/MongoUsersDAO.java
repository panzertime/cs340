package server.persistance.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

//import com.mongodb.util.JSON;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import server.data.User;
import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IUsersDAO;
import shared.logger.Log;
import shared.model.exceptions.BadJSONException;

public class MongoUsersDAO implements IUsersDAO {

	@Override
	public void saveUser(IConnection connection, User user) throws DatabaseException {
		if (user == null)
			throw new DatabaseException();
		if (connection == null)
			throw new DatabaseException();
		MongoConnection mongoConnection = (MongoConnection) connection;

		mongoConnection.getUsersCollection().insert((DBObject) JSON.parse(user.toJSON().toJSONString()));
	}

	@Override
	public List<User> getUsers(IConnection connection) throws DatabaseException {
		if (connection == null)
			throw new DatabaseException();
		List<User> users = new ArrayList<User>();

		MongoConnection mongoConnection = (MongoConnection) connection;

		DBCursor cursor = mongoConnection.getUsersCollection().find();
		while (cursor.hasNext()) {
			try {
				users.add(new User(new JSONObject((Map)cursor.next())));
			} catch (BadJSONException e) {
				Log.error("Mongo DB is ill formated, unable to interpret a user");
				e.printStackTrace();
			}
		}
		return users;
	}
}
