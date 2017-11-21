package com.javaee.escola.modelo.enums.tabelas;

public enum EnumTbAtividade {

	COD_ATIVIDADE("AtividadeCodAtividade"),
	COD_USUARIO("AtividadeCodUsuario"),
	DATA_INICIAL("AtividadeDataInicial"),
	DATA_FINAL("AtividadeDataFinal"),
	DESCRICAO("AtividadeDescricao"),
	;
	
	private String alias;

	private EnumTbAtividade(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
	
	
}
