package com.javaee.escola.modelo.enums;

public enum EnumUsuarioAutenticadoRoles {

	ADMINISTRADOR("AD", "ADMINISTRADOR"),
	PROFESSOR("PR", "PROFESSOR"),
	ALUNO("AL", "ALUNO"),
	;
	
	private String tipo;
	private String role;
	
	private EnumUsuarioAutenticadoRoles(String tipo, String role) {
		this.tipo = tipo;
		this.role = role;
	}
	public String getTipo() {
		return tipo;
	}
	public String getRole() {
		return role;
	}
	
	

	
}
