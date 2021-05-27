package com.fc.command.store.exception;

public class StoreNotFoundException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public StoreNotFoundException(String msg) {
		super(msg);
	}
}
