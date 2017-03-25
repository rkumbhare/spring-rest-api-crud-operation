/**
 * @auther Rakesh
 * @time Feb 12, 2017
 */

package com.rkumbhare.myapp.exception;

@SuppressWarnings("serial")
public class ControllerException extends Exception {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ControllerException() {
		super();
	}

	public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.message = message;
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public ControllerException(String message) {
		super(message);
		this.message = message;
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

}
