package com.javaee.escola.controlador.professor;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.fachada.AtividadeFachada;
import com.javaee.escola.seguranca.SegurancaBean;
import com.javaee.escola.utils.FacesUtil;
import com.javaee.escola.utils.PaginasBean;
import com.javaee.escola.utils.SistemaException;

@Named
@ViewScoped
public class AtividadesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Atividade> listaAtividades;
	private Atividade atividade;

	@Inject
	private SegurancaBean seguranca;
	
	public void init() throws SistemaException{
		Usuario usuarioLogado = seguranca.getUsuarioLogado();
		AtividadeFachada fachada = new AtividadeFachada();
		listaAtividades = fachada.buscarTodosPorUsuario(usuarioLogado);
	
		atividade = new Atividade();

	}
	
	public void abrirDialog(Atividade atividade, boolean modoEditar){
		if(modoEditar){
			this.atividade = atividade;
		}else{
			this.atividade = new Atividade();
		}
	}
	
	public void salvarAtividade() throws SistemaException{
		Usuario usuarioLogado = seguranca.getUsuarioLogado();
		AtividadeFachada fachada = new AtividadeFachada();
		fachada.inserirOuAtualizarAtividade(usuarioLogado, atividade);
		
		FacesUtil.redirecionarParaPagina(PaginasBean.ATIVIDADES);
	}

	public void deletarAtividade(Atividade atividade) throws SistemaException{
		AtividadeFachada fachada = new AtividadeFachada();
		fachada.deletarAtividade(atividade);
		FacesUtil.redirecionarParaPagina(PaginasBean.ATIVIDADES);
	}

	public List<Atividade> getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(List<Atividade> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	
	

	
	
}
