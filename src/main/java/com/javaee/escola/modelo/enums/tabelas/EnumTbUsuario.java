package com.javaee.escola.modelo.enums.tabelas;

public enum EnumTbUsuario {

	COD_USUARIO("UsuarioCodUsuario"),
	NOME("UsuarioNome"),
	EMAIL("UsuarioEmail"),
	SENHA("UsuarioSenha"),
	TIPO_USUARIO("UsuarioTipoUsuario"),
	
	;
	
	private String alias;

	private EnumTbUsuario(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
}
