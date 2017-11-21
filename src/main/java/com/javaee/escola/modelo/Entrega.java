package com.javaee.escola.modelo;

import java.io.Serializable;
import java.util.Date;

public class Entrega implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer codEntrega;
	private Date dataSubmetido;
	private String nomeArquivo;
	private byte[] conteudoArquivo;

	//Aluno
	private Usuario usuario;
	private Atividade atividade;
	private Turma turma;
	
	public Integer getCodEntrega() {
		return codEntrega;
	}
	public void setCodEntrega(Integer codEntrega) {
		this.codEntrega = codEntrega;
	}
	public Date getDataSubmetido() {
		return dataSubmetido;
	}
	public void setDataSubmetido(Date dataSubmetido) {
		this.dataSubmetido = dataSubmetido;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public byte[] getConteudoArquivo() {
		return conteudoArquivo;
	}
	public void setConteudoArquivo(byte[] conteudoArquivo) {
		this.conteudoArquivo = conteudoArquivo;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	
}
