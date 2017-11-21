package com.javaee.escola.controlador.professor;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.fachada.TarefaFachada;
import com.javaee.escola.modelo.fachada.TurmaFachada;
import com.javaee.escola.seguranca.SegurancaBean;
import com.javaee.escola.utils.FacesUtil;
import com.javaee.escola.utils.Out;
import com.javaee.escola.utils.SistemaException;

@Named
@ViewScoped
public class TurmasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Turma> listaTurmas;
	private List<Atividade> listaAtividades;
	private Turma turma;
	private Atividade atividade;
	private boolean turmaSelecionada;
	
	@Inject
	private SegurancaBean segurancaBean;

	public void init() throws SistemaException{
		Usuario usuarioLogado = segurancaBean.getUsuarioLogado();
		Out<List<Turma>> outTurmas = new Out<>();
		Out<List<Atividade>> outAtividades = new Out<>();
		
		TurmaFachada turmaFachada = new TurmaFachada();
		turmaFachada.buscarTodosPorProfessor(usuarioLogado,
					outTurmas,
					outAtividades);
		
		listaTurmas = outTurmas.get();
		listaAtividades = outAtividades.get();
		
		atividade = new Atividade();
	}
	
	public void selecionaTurma(Turma turma, boolean mostrarDetalhes){
		this.turma = turma;
		turmaSelecionada = mostrarDetalhes;
		if(!mostrarDetalhes){
			if(!listaAtividades.isEmpty()){
				RequestContext.getCurrentInstance().execute("PF('novaAtividadeDialogWidgetVar').show();");				
			}else{
				FacesUtil.addErrorMessage("Não há Atividades cadastradas.");
			}
		}
	}
	
	public void salvarAtividade() throws SistemaException{
		if(atividade != null){
			
			for (Atividade entidade : listaAtividades) {
				if(atividade.getCodAtividade() == entidade.getCodAtividade()){
					atividade = entidade;
				}
			}
			
			if(!turma.getAtividades().contains(atividade)){
				TarefaFachada fachada = new TarefaFachada();
				fachada.adicionarAtividade(turma, atividade);
				turma.getAtividades().add(atividade);
			}
		}
	}

	public List<Turma> getListaTurmas() {
		return listaTurmas;
	}

	public void setListaTurmas(List<Turma> listaTurmas) {
		this.listaTurmas = listaTurmas;
	}

	public List<Atividade> getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(List<Atividade> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public boolean isTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(boolean turmaSelecionada) {
		this.turmaSelecionada = turmaSelecionada;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	
	
	
	
	
	
	
	
}
