package com.reserve.util;

public class GuestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5942275254513048603L;

	public GuestException() {
	}

	public GuestException(String message) {
		super(message);
	}

	public GuestException(Throwable cause) {
		super(cause);
	}

	public GuestException(String message, Throwable cause) {
		super(message, cause);
	}

	public GuestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
