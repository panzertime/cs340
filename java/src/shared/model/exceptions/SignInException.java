package shared.model.exceptions;

import client.serverfacade.ServerException;

public class SignInException extends Exception {

	public SignInException(ServerException e) {
		super(e);
	}

}
