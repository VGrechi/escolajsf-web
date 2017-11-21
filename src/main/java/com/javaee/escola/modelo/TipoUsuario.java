package com.javaee.escola.modelo;

import java.io.Serializable;

public class TipoUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String tipoUsuario;
	private String descricao;
	
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
