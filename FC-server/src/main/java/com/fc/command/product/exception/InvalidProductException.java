package com.fc.command.product.exception;

public class InvalidProductException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public InvalidProductException(String msg) {
		super(msg);
	}
}
