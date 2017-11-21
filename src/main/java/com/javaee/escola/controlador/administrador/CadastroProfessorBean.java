package com.javaee.escola.controlador.administrador;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.javaee.escola.modelo.TipoUsuario;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.enums.EnumUsuarioAutenticadoRoles;
import com.javaee.escola.modelo.fachada.UsuarioFachada;
import com.javaee.escola.utils.FacesUtil;
import com.javaee.escola.utils.PaginasBean;
import com.javaee.escola.utils.RNBancoException;
import com.javaee.escola.utils.RNBancoExceptionEnum;
import com.javaee.escola.utils.SistemaException;

@Named
@ViewScoped
public class CadastroProfessorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Usuario> listaProfessores;
	private Usuario professor;
	private boolean modoEditar;

	public void init() throws SistemaException{
		UsuarioFachada fachada = new UsuarioFachada();
		listaProfessores = fachada.buscarTodos(EnumUsuarioAutenticadoRoles.PROFESSOR.getTipo());
		
		this.professor = new Usuario();
		TipoUsuario tipo = new TipoUsuario();
		this.professor.setTipo(tipo);
	}
	
	public void abrirDialog(Usuario professor, boolean modoEditar){
		this.modoEditar = modoEditar;
		
		if(modoEditar){
			this.professor = professor;
		}else{
			this.professor = new Usuario();
		}
	}
	
	public void salvarProfessor() throws SistemaException{
		UsuarioFachada fachada = new UsuarioFachada();
		
		if(!modoEditar){
			TipoUsuario tipo = new TipoUsuario();
			tipo.setTipoUsuario(EnumUsuarioAutenticadoRoles.PROFESSOR.getTipo());
			professor.setTipo(tipo);
		}
		fachada.inserirOuAtualizarUsuario(professor);
		
		FacesUtil.redirecionarParaPagina(PaginasBean.CADASTRO_PROFESSOR);
	}

	public void deletarProfessor(Usuario professor) throws SistemaException {
		try{
			UsuarioFachada fachada = new UsuarioFachada();
			fachada.deletarUsuario(professor);
			FacesUtil.redirecionarParaPagina(PaginasBean.CADASTRO_PROFESSOR);						
		}catch(RNBancoException e){
			if(e.getParamResult() == RNBancoExceptionEnum.PROFESSOR_VINCULADO_A_TURMA.getParamResult()){
				FacesUtil.addErrorMessage("Não foi possível excluir. "
						+ "Professor vinculado a uma turma.");
			}
		}
	}
	
	public boolean isModoEditar() {
		return modoEditar;
	}

	public void setModoEditar(boolean modoEditar) {
		this.modoEditar = modoEditar;
	}

	public List<Usuario> getListaProfessores() {
		return listaProfessores;
	}

	public void setListaProfessores(List<Usuario> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	public Usuario getProfessor() {
		return professor;
	}

	public void setProfessor(Usuario professor) {
		this.professor = professor;
	}


	
	
}
