package shared.model.exceptions;

/** This class is the exception when a function that is called during the
 * move state.
 */
@SuppressWarnings("serial")
public class PlayingException extends Exception {

	public PlayingException(Exception e) {
		super(e);
	}

}
