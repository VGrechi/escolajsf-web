package com.javaee.escola.modelo.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.enums.tabelas.EnumTbAtividade;

public class AtividadeDAO extends DAOImpl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Connection connection;

	public AtividadeDAO(Connection connection) {
		super();
		this.connection = connection;
	}

	
	public List<Atividade> buscarTodosPorUsuario(Usuario usuarioLogado) throws SQLException {
		List<Atividade> atividades = new ArrayList<Atividade>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_AtividadeBuscarTodosPorUsuario(?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodUsuario", usuarioLogado.getCodUsuario());
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

	public Atividade inserirOuAtualizar(Usuario usuarioLogado, Atividade atividade) throws SQLException {
		CallableStatement stmt = null;
		String sql = "{CALL Proc_AtividadeInserirOuAtualizar(?, ?, ?, ?, ?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodAtividade", seNullEntaoZero(atividade.getCodAtividade()));
			stmt.setInt("ParamCodUsuario", usuarioLogado.getCodUsuario());
			stmt.setTimestamp("ParamDataInicial", deJavaDateParaSQLTimestamp(atividade.getDataInicial()));
			stmt.setTimestamp("ParamDataFinal", deJavaDateParaSQLTimestamp(atividade.getDataFinal()));
			stmt.setString("ParamDescricao", atividade.getDescricao());
			stmt.registerOutParameter("OutParamCodAtividade", java.sql.Types.INTEGER);
			stmt.execute(); 
			
			atividade.setCodAtividade(stmt.getInt("OutParamCodAtividade"));
			
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		return atividade;
		
	}

	public void deletar(Atividade atividade) throws SQLException {
		CallableStatement stmt = null;
		String sql = "{CALL Proc_AtividadeRemoverAtividade(?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodAtividade", atividade.getCodAtividade());
			stmt.execute();
			
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		
	}

	public Atividade buscarAtividadePorId(Integer codAtividade) throws SQLException {
		Atividade atividade = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_AtividadeBuscarAtividadePorId(?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodAtividade", codAtividade);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				atividade = new Atividade();
				atividade.setCodAtividade(rs.getInt(EnumTbAtividade.COD_ATIVIDADE.getAlias()));
				atividade.setDataInicial(deSQLTimestampParaJavaDate(rs.getTimestamp(EnumTbAtividade.DATA_INICIAL.getAlias())));
				atividade.setDataFinal(deSQLTimestampParaJavaDate(rs.getTimestamp(EnumTbAtividade.DATA_FINAL.getAlias())));
				atividade.setDescricao(rs.getString(EnumTbAtividade.DESCRICAO.getAlias()));
			}
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
		return atividade;
	}

	
	
	

}
