package com.fc.command.product.exception;

public class ProductNotFoundException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public ProductNotFoundException(String msg) {
		super(msg);
	}
}
