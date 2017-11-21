package com.javaee.escola.controlador.administrador;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.fachada.TurmaFachada;
import com.javaee.escola.utils.SistemaException;

@Named
@ViewScoped
public class CadastroTurmasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Turma> listaTurmas;

	public void init() throws SistemaException{
		TurmaFachada fachada = new TurmaFachada();
		listaTurmas = fachada.buscarTodos();
	}
	
	public List<Turma> getListaTurmas() {
		return listaTurmas;
	}

	public void setListaTurmas(List<Turma> listaTurmas) {
		this.listaTurmas = listaTurmas;
	}

}
