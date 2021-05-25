package com.fc.command.member.exception;

public class AlreadyExistMemberException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	public AlreadyExistMemberException(String msg) {
		super(msg);
	}
}
