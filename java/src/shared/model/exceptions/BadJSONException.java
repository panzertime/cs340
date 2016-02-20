package shared.model.exceptions;

public class BadJSONException extends Exception {

	public BadJSONException(String message) {
		super(message);
	}

	public BadJSONException() {
		super();
	}

}
