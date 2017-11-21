package com.javaee.escola.modelo.enums.tabelas;

public enum EnumTbTipoUsuario {
	
	TIPO("TipoUsuarioTipo"),
	DESCRICAO("TipoUsuarioDescricao"),
	
	;
	
	private String alias;

	private EnumTbTipoUsuario(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
}
