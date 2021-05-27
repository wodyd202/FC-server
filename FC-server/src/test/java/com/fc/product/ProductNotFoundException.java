package com.fc.product;

public class ProductNotFoundException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public ProductNotFoundException(String msg) {
		super(msg);
	}
}
