package com.fc.command.store.exception;

public class InvalidStoreException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public InvalidStoreException(String msg) {
		super(msg);
	}
}
