package com.javaee.escola.controlador.aluno;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Entrega;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.fachada.EntregaFachada;
import com.javaee.escola.seguranca.SegurancaBean;
import com.javaee.escola.utils.FacesUtil;
import com.javaee.escola.utils.Out;
import com.javaee.escola.utils.PaginasBean;
import com.javaee.escola.utils.SistemaException;

@Named
@ViewScoped
public class PortalAlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Atividade> listaAtividades;
	private Integer codTurmaSelecionada;
	private boolean mostrarPanel;
	private UploadedFile arquivoAnexado;
	private List<Turma> listaTurmas;
	private Turma turmaSelecionada; 
	private Atividade atividadeSelecionada;
	
	private List<Entrega> listaEntregas;
	
	@Inject
	private SegurancaBean seguranca;


	
	public void init() throws SistemaException{
		mostrarPanel = false;
		
		Usuario usuario = seguranca.getUsuarioLogado();
		EntregaFachada fachada = new EntregaFachada();
		
		Out<List<Turma>> outTurmas = new Out<>();
		Out<List<Entrega>> outEntregas = new Out<>();
		
		fachada.buscarDadosPortalAluno(usuario, outTurmas, outEntregas);
		
		listaTurmas = outTurmas.get();
		listaEntregas = outEntregas.get();
		listaAtividades = new ArrayList<>();
	}
	
	public void onChangeDisciplina(){
		listaAtividades.clear();
		if(codTurmaSelecionada != null){
			
			for (Turma turma : listaTurmas) {
				if(turma.getCodTurma() == codTurmaSelecionada){
					listaAtividades.addAll(turma.getAtividades());						
					turmaSelecionada = turma;
				}
			}
			mostrarPanel = true;
		}else{
			mostrarPanel = false;
		}
	}
	
	public void selecionarAtividade(Integer codAtividade){
		atividadeSelecionada = new Atividade();
		atividadeSelecionada.setCodAtividade(codAtividade);
	}

	public void trataFileUpload(FileUploadEvent event) throws SistemaException{
		arquivoAnexado = event.getFile();
		
		Usuario usuario = seguranca.getUsuarioLogado();
	
		
		Entrega entrega = new Entrega();
		entrega.setTurma(turmaSelecionada);
		entrega.setAtividade(atividadeSelecionada);
		entrega.setUsuario(usuario);
		entrega.setNomeArquivo(arquivoAnexado.getFileName());
		entrega.setConteudoArquivo(arquivoAnexado.getContents());
		
		EntregaFachada fachada = new EntregaFachada();
		fachada.inserirOuAtualizarEntrega(entrega);
		
		FacesUtil.redirecionarParaPagina(PaginasBean.PORTAL_ALUNO);
	}
	
	public void tratarFileDownload(Entrega entrega){
		try {
			String nomeArquivo = entrega.getNomeArquivo();
			
		    FacesContext facesContext = FacesContext.getCurrentInstance();
		    ExternalContext externalContext = facesContext.getExternalContext();
		    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		    		
		    ServletOutputStream outputStream = response.getOutputStream();
		    response.setContentType("application/pdf"); 
		    response.setHeader("Content-Disposition", "attachment;filename=" + nomeArquivo);
		    response.setContentLength((int) entrega.getConteudoArquivo().length);
		    outputStream.write(entrega.getConteudoArquivo());
		
			outputStream.flush();
			outputStream.close();
		   
		    facesContext.responseComplete(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Atividade> getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(List<Atividade> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}



	public Integer getCodTurmaSelecionada() {
		return codTurmaSelecionada;
	}

	public void setCodTurmaSelecionada(Integer codTurmaSelecionada) {
		this.codTurmaSelecionada = codTurmaSelecionada;
	}

	public Turma getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(Turma turmaSelecionada) {
		this.turmaSelecionada = turmaSelecionada;
	}

	public boolean isMostrarPanel() {
		return mostrarPanel;
	}

	public void setMostrarPanel(boolean mostrarPanel) {
		this.mostrarPanel = mostrarPanel;
	}

	public UploadedFile getArquivoAnexado() {
		return arquivoAnexado;
	}

	public void setArquivoAnexado(UploadedFile arquivoAnexado) {
		this.arquivoAnexado = arquivoAnexado;
	}

	public List<Turma> getListaTurmas() {
		return listaTurmas;
	}

	public void setListaTurmas(List<Turma> listaTurmas) {
		this.listaTurmas = listaTurmas;
	}

	public List<Entrega> getListaEntregas() {
		return listaEntregas;
	}

	public void setListaEntregas(List<Entrega> listaEntregas) {
		this.listaEntregas = listaEntregas;
	}
	
	
	
	
	
	
}
