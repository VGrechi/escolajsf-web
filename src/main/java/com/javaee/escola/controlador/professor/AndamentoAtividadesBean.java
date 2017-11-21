package com.javaee.escola.controlador.professor;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Entrega;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.fachada.EntregaFachada;
import com.javaee.escola.utils.FacesUtil;
import com.javaee.escola.utils.GlobalBean;
import com.javaee.escola.utils.Out;
import com.javaee.escola.utils.PaginasBean;
import com.javaee.escola.utils.SistemaException;

@Named
@ViewScoped
public class AndamentoAtividadesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Atividade atividade;
	private Turma turma;
	private List<Entrega> listaEntregas;

	private String codTurmaCifrado;
	private String codAtividadeCifrado;
	
	@Inject
	private GlobalBean globalBean;
	
	public void init() throws SistemaException{
		boolean temIdURL = (codTurmaCifrado != null && codAtividadeCifrado != null);
		
		if(temIdURL){
	
			Integer codTurma = globalBean.descifrarEConverterParaInteger(codTurmaCifrado);
			Integer codAtividade = globalBean.descifrarEConverterParaInteger(codAtividadeCifrado);
			
			Out<Turma> outTurma = new Out<>();
			Out<Atividade> outAtividade = new Out<>();
			Out<List<Entrega>> outEntregas = new Out<>();
			
			EntregaFachada fachada = new EntregaFachada();
			fachada.buscarTodosPorTurmaAtividade(codTurma, codAtividade, outTurma, outAtividade, outEntregas);
			
			turma = outTurma.get();
			atividade = outAtividade.get();
			listaEntregas = outEntregas.get();

		}else{
			FacesUtil.redirecionarParaPagina(PaginasBean.TURMAS);
		}
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

	public List<Entrega> getListaEntregas() {
		return listaEntregas;
	}

	public void setListaEntregas(List<Entrega> listaEntregas) {
		this.listaEntregas = listaEntregas;
	}

	public String getCodTurmaCifrado() {
		return codTurmaCifrado;
	}

	public void setCodTurmaCifrado(String codTurmaCifrado) {
		this.codTurmaCifrado = codTurmaCifrado;
	}

	public String getCodAtividadeCifrado() {
		return codAtividadeCifrado;
	}

	public void setCodAtividadeCifrado(String codAtividadeCifrado) {
		this.codAtividadeCifrado = codAtividadeCifrado;
	}

	
	
}
