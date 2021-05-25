package com.fc.service.member.exception;

public class InvalidMemberException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	
	public InvalidMemberException(String msg) {
		super(msg);
	}
}
