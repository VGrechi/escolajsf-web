package com.javaee.escola.modelo;

import java.io.Serializable;

public class Disciplina implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Aluno
	private Usuario usuario;
	private Turma turma;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	

}
