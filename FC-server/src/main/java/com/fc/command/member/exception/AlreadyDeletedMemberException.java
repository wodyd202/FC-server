package com.fc.service.member.exception;

public class AlreadyDeletedMemberException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public AlreadyDeletedMemberException(String msg) {
		super(msg);
	}
}
