package com.javaee.escola.modelo.enums;

public enum EnumStatus {
	
	ATIVO("A", "Ativo"),
	INATIVO("I", "Inativo"),
	;
	
	private String status;
	private String descricao;
	
	private EnumStatus(String status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}
	
	public String getStatus() {
		return status;
	}

	public String getDescricao() {
		return descricao;
	}

	
	
}
