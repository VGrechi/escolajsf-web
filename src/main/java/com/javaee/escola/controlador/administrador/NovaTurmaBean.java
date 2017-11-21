package com.javaee.escola.controlador.administrador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.fachada.TurmaFachada;
import com.javaee.escola.utils.FacesUtil;
import com.javaee.escola.utils.GlobalBean;
import com.javaee.escola.utils.Out;
import com.javaee.escola.utils.PaginasBean;
import com.javaee.escola.utils.SistemaException;

@Named
@ViewScoped
public class NovaTurmaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idCifrado;
	private Turma turma;
	private List<Usuario> listaAlunos;
	private List<Usuario> listaProfessores;
	private Integer codUsuarioSelecionado;
	private List<Usuario> alunosParaExclusao;
	
	@Inject
	private GlobalBean globalBean;


	public void init() throws SistemaException{
		boolean temIdURL = idCifrado != null;
		
		if(temIdURL){
			Integer codTurma = globalBean.descifrarEConverterParaInteger(idCifrado);
			
			Out<List<Usuario>> outProfessores = new Out<>();
			Out<List<Usuario>> outAlunos = new Out<>();
			Out<Turma> outTurma = new Out<>();
			
			TurmaFachada fachada = new TurmaFachada();
			fachada.buscarTurmaPorId(codTurma,
					outAlunos,
					outProfessores,
					outTurma);
			
			turma = outTurma.get();
			listaAlunos = outAlunos.get();
			listaProfessores = outProfessores.get();
		}else{
			Out<List<Usuario>> outProfessores = new Out<>();
			Out<List<Usuario>> outAlunos = new Out<>();
			
			TurmaFachada fachada = new TurmaFachada();
			fachada.buscarTurmaSemId(outAlunos,outProfessores);
			
			turma = new Turma();
			Usuario professor = new Usuario();
			turma.setUsuario(professor);
			listaAlunos = outAlunos.get();
			listaProfessores = outProfessores.get();
		}
		
		if(turma.getAlunos() == null){
			turma.setAlunos(new ArrayList<Usuario>());
		}
		alunosParaExclusao = new ArrayList<Usuario>();
	}
	
	public void adicionarNovoAluno(){
		if(codUsuarioSelecionado != null){
			for (Usuario usuario : listaAlunos) {
				if(codUsuarioSelecionado == usuario.getCodUsuario()
						&& !turma.getAlunos().contains(usuario)){
					turma.getAlunos().add(usuario);				
				}
			}
		}
	}
	
	public void removerAluno(Usuario aluno){
		alunosParaExclusao.add(aluno);
		
		turma.getAlunos().remove(aluno);
	}
	
	public void cancelar(){
		turma = null;
		FacesUtil.redirecionarParaPagina(PaginasBean.CADASTRO_TURMAS);
	}
	
	public void salvarTurma() throws SistemaException{
		TurmaFachada fachada = new TurmaFachada();
		fachada.inserirOuAtualizarTurma(turma, alunosParaExclusao);
		
		turma = new Turma();
		
		FacesUtil.redirecionarParaPagina(PaginasBean.CADASTRO_TURMAS);
	}

	public String getIdCifrado() {
		return idCifrado;
	}

	public void setIdCifrado(String idCifrado) {
		this.idCifrado = idCifrado;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Usuario> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Usuario> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public List<Usuario> getListaProfessores() {
		return listaProfessores;
	}

	public void setListaProfessores(List<Usuario> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	public Integer getCodUsuarioSelecionado() {
		return codUsuarioSelecionado;
	}

	public void setCodUsuarioSelecionado(Integer codUsuarioSelecionado) {
		this.codUsuarioSelecionado = codUsuarioSelecionado;
	}

	
	
	
	
	
	
	
}
