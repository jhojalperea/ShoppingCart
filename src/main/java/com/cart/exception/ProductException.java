package com.cart.exception;

public class ProductException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
	public ProductException(String message) {
		super(message);
	}

	public ProductException(String message, Throwable cause) {
		super(message, cause);
	}
}
