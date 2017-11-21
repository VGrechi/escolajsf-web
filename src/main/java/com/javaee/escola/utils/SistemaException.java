package com.javaee.escola.utils;

public class SistemaException extends Exception {

	private static final long serialVersionUID = 1L;

	private Throwable exception;
	
	private SistemaExceptionEnum enumException;
	
	public SistemaException(SistemaExceptionEnum enumException) {
		this.exception = new Exception();
		this.enumException = enumException;
	}
	
	public SistemaException(Throwable e, SistemaExceptionEnum enumException) {
		this.exception = e;
		this.enumException = enumException;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public SistemaExceptionEnum getEnum() {
		return enumException;
	}

	public void setEnumException(SistemaExceptionEnum enumException) {
		this.enumException = enumException;
	}
	
	@Override
	public String toString() {
		return "SistemaException: " + exception.getMessage();
	}
}
