package server.persistance.files;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;
import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;
import server.persistance.IConnection;

public class FileCommandsDAO implements ICommandsDAO {

	@Override
	public void saveCommmand(IConnection connection, Integer gameID, MovesCommand movesCommand)
			throws DatabaseException {
		try {
			PrintWriter writer = new PrintWriter("data/command/" + gameID + ".txt", "UTF-8");
			writer.println(movesCommand.getArguments().toJSONString());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new DatabaseException();
		}
		

	}

	@Override
	public List<JSONObject> getCommands(IConnection connection, Integer gameID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCommands(IConnection connection, Integer gameID) throws DatabaseException {
		// TODO Auto-generated method stub

	}

}
