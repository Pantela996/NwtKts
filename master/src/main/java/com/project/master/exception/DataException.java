package com.project.master.exception;

public class DataException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DataException(String message) {
		this.message = message;
	}

	public DataException() {
	}
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
