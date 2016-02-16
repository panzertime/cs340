package client.serverfacade;

public class ServerProxyException extends Exception {

	public ServerProxyException() {
		return;
	}

	public ServerProxyException(String message) {
		super(message);
	}

	public ServerProxyException(Throwable throwable) {
		super(throwable);
	}

	public ServerProxyException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
