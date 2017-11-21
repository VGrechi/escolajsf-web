package com.javaee.escola.modelo.enums.tabelas;

public enum EnumTbTurma {

	COD_TURMA("TurmaCodTurma"),
	NOME("TurmaNome"),
	STATUS("TurmaStatus"),
	COD_USUARIO("TurmaCodUsuario"), 
	CAMPO_VIRTUAL_TOTAL_DE_ALUNOS("TurmaCampoVirtualTotalDeAlunos"),
	;
	
	private String alias;

	private EnumTbTurma(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
	
	
}
