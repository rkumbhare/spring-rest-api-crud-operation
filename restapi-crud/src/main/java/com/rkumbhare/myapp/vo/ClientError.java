/**
 * @auther Rakesh
 * @time Feb 12, 2017
 */

package com.rkumbhare.myapp.vo;

import org.springframework.http.HttpStatus;

public class ClientError {
	private HttpStatus status;
	private String message;
	private String error;

	public ClientError(HttpStatus status, String message, String error) {
		this.status = status;
		this.message = message;
		this.error = error;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
