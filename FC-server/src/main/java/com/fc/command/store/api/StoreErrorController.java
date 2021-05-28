package com.fc.command.store.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fc.command.store.exception.AlreadyExistStoreException;
import com.fc.command.store.exception.InvalidStoreException;
import com.fc.command.store.exception.StoreNotFoundException;
import com.fc.core.http.ErrorResponse;

@RestControllerAdvice
public class StoreErrorController {

	@ExceptionHandler(AlreadyExistStoreException.class)
	public ResponseEntity<ErrorResponse> error(AlreadyExistStoreException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidStoreException.class)
	public ResponseEntity<ErrorResponse> error(InvalidStoreException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(StoreNotFoundException.class)
	public ResponseEntity<ErrorResponse> error(StoreNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
