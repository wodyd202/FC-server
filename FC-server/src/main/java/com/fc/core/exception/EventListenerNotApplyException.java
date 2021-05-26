package com.fc.core.exception;

public class EventListenerNotApplyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EventListenerNotApplyException(String message, Exception e) {
		super(message, e);
	}
}
