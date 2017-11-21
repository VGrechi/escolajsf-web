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
import com.javaee.escola.utils.SistemaException;
import com.javaee.escola.utils.SistemaExceptionEnum;

@Named
@ViewScoped
public class CadastroAlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Usuario> listaAlunos;
	private Usuario aluno;
	private boolean modoEditar;

	public void init() throws SistemaException{
		UsuarioFachada fachada = new UsuarioFachada();
		listaAlunos = fachada.buscarTodos(EnumUsuarioAutenticadoRoles.ALUNO.getTipo());
		
		this.aluno = new Usuario();
		TipoUsuario tipo = new TipoUsuario();
		this.aluno.setTipo(tipo);
	}
	
	public void abrirDialog(Usuario aluno, boolean modoEditar){
		this.modoEditar = modoEditar;
		
		if(modoEditar){
			this.aluno = aluno;
		}else{
			this.aluno = new Usuario();
		}
	}
	
	public void salvarAluno() throws SistemaException{
		UsuarioFachada fachada = new UsuarioFachada();
		
		if(!modoEditar){
			TipoUsuario tipo = new TipoUsuario();
			tipo.setTipoUsuario(EnumUsuarioAutenticadoRoles.ALUNO.getTipo());
			aluno.setTipo(tipo);
		}
		fachada.inserirOuAtualizarUsuario(aluno);
		FacesUtil.redirecionarParaPagina(PaginasBean.CADASTRO_ALUNO);
	}

	public void deletarAluno(Usuario aluno) throws SistemaException{
		UsuarioFachada fachada = new UsuarioFachada();
		try {
			fachada.deletarUsuario(aluno);
		} catch (RNBancoException e) {
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}
		FacesUtil.redirecionarParaPagina(PaginasBean.CADASTRO_ALUNO);
	}

	public List<Usuario> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Usuario> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public boolean isModoEditar() {
		return modoEditar;
	}

	public void setModoEditar(boolean modoEditar) {
		this.modoEditar = modoEditar;
	}

	public Usuario getAluno() {
		return aluno;
	}

	public void setAluno(Usuario aluno) {
		this.aluno = aluno;
	}
	
	
	
}
