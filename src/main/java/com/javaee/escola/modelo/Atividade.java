package com.javaee.escola.modelo;

import java.io.Serializable;
import java.util.Date;

public class Atividade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer codAtividade;
	private Date dataInicial;
	private Date dataFinal;
	private String descricao;
	
	private Usuario usuario;
	
	public Integer getCodAtividade() {
		return codAtividade;
	}
	public void setCodAtividade(Integer codAtividade) {
		this.codAtividade = codAtividade;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
