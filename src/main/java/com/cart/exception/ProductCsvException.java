package com.cart.exception;

public class ProductCsvException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
	public ProductCsvException(String message) {
		super(message);
	}

	public ProductCsvException(String message, Throwable cause) {
		super(message, cause);
	}	

}
