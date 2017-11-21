package com.javaee.escola.modelo;

import java.io.Serializable;

public class Tarefa implements Serializable{

	private static final long serialVersionUID = 1L;

	private Turma turma;
	private Atividade atividade;
	
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	
	
}
