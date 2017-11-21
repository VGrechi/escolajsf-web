package com.javaee.escola.modelo.fachada;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Entrega;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.dao.AtividadeDAO;
import com.javaee.escola.modelo.dao.EntregaDAO;
import com.javaee.escola.modelo.dao.TarefaDAO;
import com.javaee.escola.modelo.dao.TurmaDAO;
import com.javaee.escola.utils.Out;
import com.javaee.escola.utils.SistemaException;
import com.javaee.escola.utils.SistemaExceptionEnum;

public class EntregaFachada extends FachadaImpl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Entrega inserirOuAtualizarEntrega(Entrega entrega) throws SistemaException{
		Connection connection = null;
		try{
			connection = conectar();
			EntregaDAO entregaDAO = new EntregaDAO(connection);
			
			entrega = entregaDAO.inserirOuAtualizar(entrega);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		return entrega;
	}
	
	public void buscarTodosPorTurmaAtividade(Integer codTurma, Integer codAtividade, 
			Out<Turma> outTurma, Out<Atividade> outAtividade, Out<List<Entrega>> outEntregas) throws SistemaException{
		
		Connection connection = null;
		try{
			connection = conectar();
			EntregaDAO entregaDAO = new EntregaDAO(connection);
			AtividadeDAO atividadeDAO = new AtividadeDAO(connection);
			TurmaDAO turmaDAO = new TurmaDAO(connection);
			
			List<Entrega> entregas = entregaDAO.buscarTodosPorTurmaAtividade(codTurma, codAtividade);
			outEntregas.set(entregas);
			
			Turma turma = turmaDAO.buscarTurmaPorId(codTurma);
			outTurma.set(turma);
			
			Atividade atividade = atividadeDAO.buscarAtividadePorId(codAtividade);
			outAtividade.set(atividade);
			
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
	}

	public void buscarDadosPortalAluno(Usuario usuario, Out<List<Turma>> outTurmas, Out<List<Entrega>> outEntregas) throws SistemaException {
		Connection connection = null;
		
		try{
			connection = conectar();
			TurmaDAO dao = new TurmaDAO(connection);
			TarefaDAO tarefaDAO = new TarefaDAO(connection);
			EntregaDAO entregaDAO = new EntregaDAO(connection);
			
			List<Turma> turmas = dao.buscarTodosPorUsuario(usuario);
			for (Turma turma : turmas) {
				List<Atividade> atividadesDaTurma = tarefaDAO.buscarAtividadesPorTurma(turma.getCodTurma());
				turma.setAtividades(atividadesDaTurma);
			}
			outTurmas.set(turmas);
			
			List<Entrega> entregas = entregaDAO.buscarTodosPorUsuario(usuario);
			outEntregas.set(entregas);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		
	}

}
