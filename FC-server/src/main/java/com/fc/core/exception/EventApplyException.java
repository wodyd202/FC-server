package com.fc.core.exception;

/**
 * Created by jaceshim on 2017. 3. 10..
 */
public class EventApplyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EventApplyException() {
	}

	public EventApplyException(String message) {
		super(message);
	}

	public EventApplyException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventApplyException(Throwable cause) {
		super(cause);
	}

	public EventApplyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
