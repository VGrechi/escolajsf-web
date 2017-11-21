package com.javaee.escola.utils;

public enum RNBancoExceptionEnum {

	USUARIO_OU_SENHA_INVALIDOS(100),
	
	PROFESSOR_VINCULADO_A_TURMA(900),
	
	;
	
	private Integer paramResult;

	private RNBancoExceptionEnum(Integer paramResult) {
		this.paramResult = paramResult;
	}

	public Integer getParamResult() {
		return paramResult;
	}

	
	
}
