package com.fc.service.common.address.exception;

public class InvalidAddressException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public InvalidAddressException(String msg) {
		super(msg);
	}
}
