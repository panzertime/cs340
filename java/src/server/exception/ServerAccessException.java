package server.exception;

/**
 * This exception is thrown whenever a command is unable to modify
 * or access the server database. It should include a message for the
 * end user client to know why the server request failed.
 *
 */
public class ServerAccessException extends Exception {

	/**
	 * returns an error message when thrown
	 * @pre message contains a valid message
	 * @post end user will know why their request failed.
	 * @param message Message to show the end-user why their request
	 * failed.
	 */
	public ServerAccessException(String message) {
		super(message);
	}
}
