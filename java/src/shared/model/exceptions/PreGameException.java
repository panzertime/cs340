package shared.model.exceptions;

import client.serverfacade.ServerException;

public class PreGameException extends Exception {

	public PreGameException(ServerException e) {
		super(e);
	}

}
