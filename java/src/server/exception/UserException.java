package server.exception;

public class UserException extends Exception {
	
	/**
	 * returns an error message when thrown
	 * @pre message contains a message explaining the problem with the user 
	 * object
	 * @post programmer will know why user operation failed.
	 * @param message Message to show the programmer why the error was thrown
	 */
	public UserException(String message) {
		super(message);
	}
}
