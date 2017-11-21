package com.javaee.escola.modelo.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Entrega;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.enums.tabelas.EnumTbAtividade;
import com.javaee.escola.modelo.enums.tabelas.EnumTbEntrega;
import com.javaee.escola.modelo.enums.tabelas.EnumTbTurma;
import com.javaee.escola.modelo.enums.tabelas.EnumTbUsuario;

public class EntregaDAO extends DAOImpl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Connection connection;

	public EntregaDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public Entrega inserirOuAtualizar(Entrega entrega) throws SQLException{
		CallableStatement stmt = null;
		String sql = "{CALL Proc_EntregaInserirOuAtualizar(?, ?, ?, ?, ?, ?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodEntrega", seNullEntaoZero(entrega.getCodEntrega()));
			stmt.setInt("ParamCodTurma", entrega.getTurma().getCodTurma());
			stmt.setInt("ParamCodAtividade", entrega.getAtividade().getCodAtividade());
			stmt.setInt("ParamCodUsuario", entrega.getUsuario().getCodUsuario());
			stmt.setString("ParamNomeArquivo", entrega.getNomeArquivo());
			InputStream inputStream = new ByteArrayInputStream(entrega.getConteudoArquivo());
			stmt.setBinaryStream("ParamConteudoArquivo", inputStream);
			stmt.registerOutParameter("OutParamCodEntrega", java.sql.Types.INTEGER);
			stmt.execute();
			
			entrega.setCodEntrega(stmt.getInt("OutParamCodEntrega"));
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		
		return entrega;
	}
	
	public List<Entrega> buscarTodosPorTurmaAtividade(Integer codTurma, Integer codAtividade) throws SQLException{
		List<Entrega> entregas = new ArrayList<Entrega>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_EntregaBuscarTodosPorTurmaAtividade(?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodTurma", codTurma);
			stmt.setInt("ParamCodAtividade", codAtividade);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Entrega entrega = new Entrega();
				entrega.setCodEntrega(rs.getInt(EnumTbEntrega.COD_ENTREGA.getAlias()));
				entrega.setDataSubmetido(deSQLTimestampParaJavaDate(rs.getTimestamp(EnumTbEntrega.DATA_SUBMETIDO.getAlias())));
				entrega.setNomeArquivo(rs.getString(EnumTbEntrega.NOME_ARQUIVO.getAlias()));
				Blob conteudoArquivo = rs.getBlob(EnumTbEntrega.CONTEUDO_ARQUIVO.getAlias());
				entrega.setConteudoArquivo(conteudoArquivo.getBytes(1, (int) conteudoArquivo.length()));	
			
				Turma turma = new Turma();
				turma.setCodTurma(rs.getInt(EnumTbEntrega.COD_TURMA.getAlias()));
				entrega.setTurma(turma);
				
				Atividade atividade = new Atividade();
				atividade.setCodAtividade(rs.getInt(EnumTbEntrega.COD_ATIVIDADE.getAlias()));
				entrega.setAtividade(atividade);
				
				Usuario usuario = new Usuario();
				usuario.setCodUsuario(rs.getInt(EnumTbUsuario.COD_USUARIO.getAlias()));
				usuario.setNome(rs.getString(EnumTbUsuario.NOME.getAlias()));
				entrega.setUsuario(usuario);
				
				entregas.add(entrega);
			}
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
			
		return entregas;
	}

	public List<Entrega> buscarTodosPorUsuario(Usuario usuario) throws SQLException {
		List<Entrega> entregas = new ArrayList<Entrega>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_EntregaBuscarTodosPorUsuario(?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodUsuario", usuario.getCodUsuario());
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Entrega entrega = new Entrega();
				entrega.setCodEntrega(rs.getInt(EnumTbEntrega.COD_ENTREGA.getAlias()));
				entrega.setDataSubmetido(deSQLTimestampParaJavaDate(rs.getTimestamp(EnumTbEntrega.DATA_SUBMETIDO.getAlias())));
				entrega.setNomeArquivo(rs.getString(EnumTbEntrega.NOME_ARQUIVO.getAlias()));
				Blob conteudoArquivo = rs.getBlob(EnumTbEntrega.CONTEUDO_ARQUIVO.getAlias());
				entrega.setConteudoArquivo(conteudoArquivo.getBytes(1, (int) conteudoArquivo.length()));	
			
				Turma turma = new Turma();
				turma.setCodTurma(rs.getInt(EnumTbTurma.COD_TURMA.getAlias()));
				turma.setNome(rs.getString(EnumTbTurma.NOME.getAlias()));
				entrega.setTurma(turma);
				
				Atividade atividade = new Atividade();
				atividade.setCodAtividade(rs.getInt(EnumTbAtividade.COD_ATIVIDADE.getAlias()));
				atividade.setDescricao(rs.getString(EnumTbAtividade.DESCRICAO.getAlias()));
				entrega.setAtividade(atividade);
				
				entrega.setUsuario(usuario);
				
				entregas.add(entrega);
			}
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
			
		return entregas;
	}
	
	
	

}
