package com.javaee.escola.utils;

public class RNBancoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private int paramResult;
	
	public RNBancoException(int paramResult) {
		super("RNBancoException - paramResult: " + paramResult);
		this.paramResult = paramResult;
	}

	public int getParamResult() {
		return paramResult;
	}
	

}
