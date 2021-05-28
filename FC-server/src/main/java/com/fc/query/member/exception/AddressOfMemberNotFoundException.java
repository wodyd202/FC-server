package com.fc.query.member.exception;

public class AddressOfMemberNotFoundException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public AddressOfMemberNotFoundException(String msg) {
		super(msg);
	}
}
