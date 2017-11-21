package com.javaee.escola.modelo.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Tarefa;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.enums.tabelas.EnumTbAtividade;

public class TarefaDAO extends DAOImpl implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Connection connection;

	public TarefaDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public List<Atividade> buscarAtividadesPorTurma(Integer codTurma) throws SQLException {
		List<Atividade> atividades = new ArrayList<Atividade>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_TarefaBuscarAtividadesPorTurma(?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodTurma", codTurma);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Atividade atividade = new Atividade();
				atividade.setCodAtividade(rs.getInt(EnumTbAtividade.COD_ATIVIDADE.getAlias()));
				atividade.setDataInicial(deSQLTimestampParaJavaDate(rs.getTimestamp(EnumTbAtividade.DATA_INICIAL.getAlias())));
				atividade.setDataFinal(deSQLTimestampParaJavaDate(rs.getTimestamp(EnumTbAtividade.DATA_FINAL.getAlias())));
				atividade.setDescricao(rs.getString(EnumTbAtividade.DESCRICAO.getAlias()));
				
				atividades.add(atividade);
			}
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
			
		return atividades;
	}

	public Tarefa inserirOuAtualizar(Turma turma, Atividade atividade) throws SQLException {
		Tarefa tarefa = null;
		CallableStatement stmt = null;
		String sql = "{CALL Proc_TarefaInserirOuAtualizar(?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodTurma", turma.getCodTurma());
			stmt.setInt("ParamCodAtividade", atividade.getCodAtividade());
			stmt.execute();
			
			tarefa = new Tarefa();
			tarefa.setAtividade(atividade);
			tarefa.setTurma(turma);
		
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		return tarefa;
		
	}

}
