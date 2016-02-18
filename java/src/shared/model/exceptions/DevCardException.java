package shared.model.exceptions;

/** This class is the exception when a dev card function is called during the
 * Playing state.
 */
@SuppressWarnings("serial")
public class DevCardException extends Exception {

	public DevCardException(Exception e) {
		super(e);
	}

}