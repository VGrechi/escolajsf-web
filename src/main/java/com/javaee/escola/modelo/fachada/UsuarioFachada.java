package com.javaee.escola.modelo.fachada;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.dao.UsuarioDAO;
import com.javaee.escola.utils.RNBancoException;
import com.javaee.escola.utils.SistemaException;
import com.javaee.escola.utils.SistemaExceptionEnum;

public class UsuarioFachada extends FachadaImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	public Usuario buscarParaLogin(String nomeLogin, String senha) throws SistemaException, RNBancoException {
		Connection connection = null;
		Usuario usuario = null;
		try{
			connection = conectar();
			UsuarioDAO dao = new UsuarioDAO(connection);
			usuario = dao.buscarParaLogin(nomeLogin, senha);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		return usuario;
	}

	public List<Usuario> buscarTodos(String tipoUsuario) throws SistemaException {
		List<Usuario> usuarios = null;
		Connection connection = null;
		
		try{
			connection = conectar();
			UsuarioDAO dao = new UsuarioDAO(connection);
			usuarios = dao.buscarTodos(tipoUsuario);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		return usuarios;
	}
	
	public void inserirOuAtualizarUsuario(Usuario usuario) throws SistemaException {
		Connection connection = null;
		
		try{
			connection = conectar();
			UsuarioDAO dao = new UsuarioDAO(connection);
			dao.inserirOuAtualizar(usuario);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
	}

	public void deletarUsuario(Usuario aluno) throws SistemaException, RNBancoException {
		Connection connection = null;
		
		try{
			connection = conectar();
			UsuarioDAO dao = new UsuarioDAO(connection);
			dao.deletar(aluno);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		
	}

	public void alterarSenha(Integer codUsuario, String novaSenha) throws SistemaException {
		Connection connection = null;
		
		try{
			connection = conectar();
			UsuarioDAO dao = new UsuarioDAO(connection);
			dao.alterarSenha(codUsuario, novaSenha);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		
	}
	
	

}
