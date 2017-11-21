package com.javaee.escola.modelo.fachada;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.dao.TarefaDAO;
import com.javaee.escola.utils.SistemaException;
import com.javaee.escola.utils.SistemaExceptionEnum;

public class TarefaFachada extends FachadaImpl implements Serializable{

	private static final long serialVersionUID = 1L;

	public void adicionarAtividade(Turma turma, Atividade atividade) throws SistemaException {
		Connection connection = null;
		
		try{
			connection = conectar();
			TarefaDAO dao = new TarefaDAO(connection);
			
			dao.inserirOuAtualizar(turma, atividade);

		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		
	}

	
}
