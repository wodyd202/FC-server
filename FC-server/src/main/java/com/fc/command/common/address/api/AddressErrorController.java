package com.fc.command.common.address.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fc.command.common.address.exception.InvalidAddressException;
import com.fc.core.http.ErrorResponse;

@RestControllerAdvice
public class AddressErrorController {

	@ExceptionHandler(InvalidAddressException.class)
	public ResponseEntity<ErrorResponse> error(InvalidAddressException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
