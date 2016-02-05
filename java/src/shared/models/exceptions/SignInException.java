package shared.models.exceptions;

import client.servercommunicator.ServerException;

public class SignInException extends Exception {

	public SignInException(ServerException e) {
		super(e);
	}

}
