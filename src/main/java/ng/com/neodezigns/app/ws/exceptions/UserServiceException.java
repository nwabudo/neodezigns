package ng.com.neodezigns.app.ws.exceptions;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = 3550684939939471934L;

	public UserServiceException(String message) {
		super(message);
	}
}
