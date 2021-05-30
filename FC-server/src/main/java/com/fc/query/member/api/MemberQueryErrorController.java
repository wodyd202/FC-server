package com.fc.query.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fc.core.http.ErrorResponse;
import com.fc.query.member.exception.AddressOfMemberNotFoundException;

@RestControllerAdvice
public class MemberQueryErrorController {
	@ExceptionHandler(AddressOfMemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> error(AddressOfMemberNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
