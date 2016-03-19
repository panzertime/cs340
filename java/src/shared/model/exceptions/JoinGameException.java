package shared.model.exceptions;

import client.servercommunicator.ServerException;

public class JoinGameException extends Exception {

	public JoinGameException(ServerException e) {
		super(e);
	}

	public JoinGameException() {
		// TODO Auto-generated constructor stub
	}

}
