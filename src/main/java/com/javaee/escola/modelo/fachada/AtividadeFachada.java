package com.javaee.escola.modelo.fachada;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.dao.AtividadeDAO;
import com.javaee.escola.utils.SistemaException;
import com.javaee.escola.utils.SistemaExceptionEnum;

public class AtividadeFachada extends FachadaImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	
	
	public List<Atividade> buscarTodosPorUsuario(Usuario usuarioLogado) throws SistemaException {
		List<Atividade> atividades = null;
		Connection connection = null;
		
		try{
			connection = conectar();
			AtividadeDAO dao = new AtividadeDAO(connection);
			
			atividades = dao.buscarTodosPorUsuario(usuarioLogado);
			
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		return atividades;
	}

	public void inserirOuAtualizarAtividade(Usuario usuarioLogado, Atividade atividade) throws SistemaException {
		Connection connection = null;
		
		try{
			connection = conectar();
			AtividadeDAO dao = new AtividadeDAO(connection);
			
			dao.inserirOuAtualizar(usuarioLogado, atividade);
			
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}

	}

	public void deletarAtividade(Atividade atividade) throws SistemaException {
		Connection connection = null;
		
		try{
			connection = conectar();
			AtividadeDAO dao = new AtividadeDAO(connection);
			
			dao.deletar(atividade);
			
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		
	}

	


}
