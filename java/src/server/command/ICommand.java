package server.command;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;

/**
 * Class to be used to allow server endpoints to be executable.
 * Any object inheriting from it must reside in the appropriate subdirectory
 * of the command package.
 *
 */
public interface ICommand {

	/**
	 * This function will access the Server's database and it will either
	 * make additions to the games list or users list, or update a game.
	 * Any non-user command must validate the user cookie passed in
	 * and all game commands must additionally validate the game
	 * cookie in order to successfully change the game model.
	 * @pre none
	 * @post The given command will be executed on the server if the args
	 * and cookie are valid. If not then it will return an error.
	 * @param args the JSON Arguments required for each command
	 * @param cookie The cookies passed in by the user
	 * @return a string to be included in the successful response 
	 * @throws ServerAccessException indicates that the data was invalid and 
	 * includes a message explaining why to be passed to the client user.
	 */
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException;
}
