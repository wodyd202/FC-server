package com.fc.command.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fc.command.member.exception.AlreadyDeletedMemberException;
import com.fc.command.member.exception.AlreadyExistMemberException;
import com.fc.command.member.exception.InvalidMemberException;
import com.fc.command.member.exception.MemberNotFoundException;
import com.fc.core.http.ErrorResponse;

@RestControllerAdvice
public class MemberErrorController {

	@ExceptionHandler(AlreadyDeletedMemberException.class)
	public ResponseEntity<ErrorResponse> error(AlreadyDeletedMemberException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AlreadyExistMemberException.class)
	public ResponseEntity<ErrorResponse> error(AlreadyExistMemberException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidMemberException.class)
	public ResponseEntity<ErrorResponse> error(InvalidMemberException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> error(MemberNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
