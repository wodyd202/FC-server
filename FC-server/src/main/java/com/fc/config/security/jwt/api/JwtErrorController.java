package com.fc.config.security.jwt.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fc.config.security.jwt.exception.InvalidAccessTokenException;
import com.fc.config.security.jwt.exception.InvalidRefreshTokenException;
import com.fc.core.http.ErrorResponse;

@RestControllerAdvice
public class JwtErrorController {

	@ExceptionHandler(InvalidAccessTokenException.class)
	public ResponseEntity<ErrorResponse> InvalidAccessTokenException(InvalidAccessTokenException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidRefreshTokenException.class)
	public ResponseEntity<ErrorResponse> InvalidRefreshTokenException(InvalidRefreshTokenException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
