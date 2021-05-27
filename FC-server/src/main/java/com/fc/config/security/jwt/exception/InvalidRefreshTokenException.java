package com.fc.config.security.jwt.exception;

public class InvalidRefreshTokenException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenException(String msg) {
		super(msg);
	}
}
