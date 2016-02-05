package shared.models.exceptions;

import client.servercommunicator.ServerException;

public class PreGameException extends Exception {

	public PreGameException(ServerException e) {
		super(e);
	}

}
