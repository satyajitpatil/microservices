package com.cognizant.truyum.dao;

public class CartEmptyException extends Exception {

	private static final long serialVersionUID = 5455375227193462427L;

	public CartEmptyException() {
		super();
	}

	public CartEmptyException(String message) {
		super(message);
	}

}
