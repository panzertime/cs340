package shared.models.exceptions;

/** This class is the exception when the messaging functions have errors
 */
@SuppressWarnings("serial")
public class MessageException extends Exception {

	public MessageException(Exception e) {
		super(e);
	}


}