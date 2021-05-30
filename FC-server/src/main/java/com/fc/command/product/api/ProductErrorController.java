package com.fc.command.product.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fc.command.product.exception.InvalidProductException;
import com.fc.command.product.exception.ProductNotFoundException;
import com.fc.core.http.ErrorResponse;

@RestControllerAdvice
public class ProductErrorController {

	@ExceptionHandler(InvalidProductException.class)
	public ResponseEntity<ErrorResponse> error(InvalidProductException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> error(ProductNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

}
