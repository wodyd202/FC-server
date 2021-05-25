package com.fc.core.exception;

/**
 * Created by jaceshim on 2017. 3. 28..
 */
public class EventListenerNotApplyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EventListenerNotApplyException(String message, Exception e) {
		super(message, e);
	}
}
