package shared.model.exceptions;

import client.serverfacade.ServerException;

public class JoinGameException extends Exception {

	public JoinGameException(ServerException e) {
		super(e);
	}

}
