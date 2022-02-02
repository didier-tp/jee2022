package tp.jeeApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistsException() {
	}

	public AlreadyExistsException(String message) {
		super(message);
	}

	public AlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
