package server.persistance.mongo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import server.command.moves.MovesCommand;
import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;
import server.persistance.IConnection;

public class MongoCommandsDAO implements ICommandsDAO {

	@Override
	public void saveCommmand(IConnection connection, Integer gameID, MovesCommand movesCommand)
			throws DatabaseException {
		if (gameID == null)
			throw new DatabaseException();
		if (movesCommand == null)
			throw new DatabaseException();
		if (connection == null)
			throw new DatabaseException();
		MongoConnection mongoConnection = (MongoConnection) connection;

		DBObject command = (DBObject) JSON.parse(movesCommand.getArguments().toJSONString());
		command.put("gameID", gameID);

		mongoConnection.getCommandsCollection().insert(command);
	}

	@Override
	public List<JSONObject> getCommands(IConnection connection, Integer gameID) throws DatabaseException {
		if (gameID == null)
			throw new DatabaseException();
		if (connection == null)
			throw new DatabaseException();
		List<JSONObject> commands = new ArrayList<JSONObject>();

		MongoConnection mongoConnection = (MongoConnection) connection;

		DBCursor cursor = mongoConnection.getCommandsCollection().find();
		while (cursor.hasNext()) {
			commands.add((JSONObject) cursor.next());
		}
		return commands;
	}
	
	@Override
	public void deleteCommands(IConnection connection, Integer gameID) throws DatabaseException {
		if (gameID == null)
			throw new DatabaseException();
		if (connection == null)
			throw new DatabaseException();

		MongoConnection mongoConnection = (MongoConnection) connection;
		
		DBCursor cursor = mongoConnection.getCommandsCollection().find();
		while (cursor.hasNext()) {
			mongoConnection.getCommandsCollection().remove(cursor.next());
		}
	}
}
