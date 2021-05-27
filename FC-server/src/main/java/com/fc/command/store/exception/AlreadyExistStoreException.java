package com.fc.command.store.exception;

public class AlreadyExistStoreException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public AlreadyExistStoreException(String msg) {
		super(msg);
	}
}
