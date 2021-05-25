package com.fc.command.member.exception;

public class MemberNotFoundException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public MemberNotFoundException(String msg) {
		super(msg);
	}
}
