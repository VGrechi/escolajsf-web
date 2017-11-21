package com.javaee.escola.modelo;

import java.io.Serializable;
import java.util.List;

public class Turma implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer codTurma;
	private String nome;
	private String status;
	private Usuario usuario; //Professor
	
	//Virtuais
	private Integer campoVirtualTotalDeAlunos;
	private List<Usuario> alunos;
	private List<Atividade> atividades;

	
	/*
	 * Getters e Setters
	 * */
	public Integer getCodTurma() {
		return codTurma;
	}

	public void setCodTurma(Integer codTurma) {
		this.codTurma = codTurma;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getCampoVirtualTotalDeAlunos() {
		return campoVirtualTotalDeAlunos;
	}

	public void setCampoVirtualTotalDeAlunos(Integer campoVirtualTotalDeAlunos) {
		this.campoVirtualTotalDeAlunos = campoVirtualTotalDeAlunos;
	}

	public List<Usuario> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Usuario> alunos) {
		this.alunos = alunos;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	
	
	
	
	
}
