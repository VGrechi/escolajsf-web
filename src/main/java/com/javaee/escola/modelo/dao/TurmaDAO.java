package com.javaee.escola.modelo.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.enums.EnumStatus;
import com.javaee.escola.modelo.enums.tabelas.EnumTbTurma;
import com.javaee.escola.modelo.enums.tabelas.EnumTbUsuario;

public class TurmaDAO extends DAOImpl implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Connection connection;

	public TurmaDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public List<Turma> buscarTodos() throws SQLException{
		List<Turma> turmas = new ArrayList<Turma>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_TurmaBuscarTodos()}";
		
		try{
			stmt = connection.prepareCall(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Turma turma = new Turma();
				turma.setCodTurma(rs.getInt(EnumTbTurma.COD_TURMA.getAlias()));
				turma.setNome(rs.getString(EnumTbTurma.NOME.getAlias()));
				turma.setCampoVirtualTotalDeAlunos(rs.getInt(EnumTbTurma.CAMPO_VIRTUAL_TOTAL_DE_ALUNOS.getAlias()));
				
				switch (rs.getString(EnumTbTurma.STATUS.getAlias())) {
				case "A":
					turma.setStatus(EnumStatus.ATIVO.getDescricao());
					break;
				case "I":
					turma.setStatus(EnumStatus.INATIVO.getDescricao());
					break;
				}
				
				Usuario professor = new Usuario();
				professor.setCodUsuario(rs.getInt(EnumTbUsuario.COD_USUARIO.getAlias()));
				professor.setNome(rs.getString(EnumTbUsuario.NOME.getAlias()));
				turma.setUsuario(professor);
				
				turmas.add(turma);
			}
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
			
		return turmas;
	}
	
	public Turma inserirOuAtualizar(Turma turma) throws SQLException {
		CallableStatement stmt = null;
		String sql = "{CALL Proc_TurmaInserirOuAtualizar(?, ?, ?, ?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodTurma", seNullEntaoZero(turma.getCodTurma()));
			stmt.setString("ParamNome", turma.getNome());
			stmt.setString("ParamStatus", extraiPrimeiroCaractere(turma.getStatus()));
			stmt.setInt("ParamCodUsuario", turma.getUsuario().getCodUsuario());
			stmt.registerOutParameter("OutParamCodTurma", java.sql.Types.INTEGER);
			stmt.execute();
			
			turma.setCodTurma(stmt.getInt("OutParamCodTurma"));
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		return turma;
	}

	public List<Turma> buscarTodosPorUsuario(Usuario usuario) throws SQLException {
		List<Turma> turmas = new ArrayList<Turma>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_TurmaBuscarTodosPorUsuario(?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodUsuario", usuario.getCodUsuario());
			stmt.setString("ParamTipoUsuario", usuario.getTipo().getTipoUsuario());
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Turma turma = new Turma();
				turma.setCodTurma(rs.getInt(EnumTbTurma.COD_TURMA.getAlias()));
				turma.setNome(rs.getString(EnumTbTurma.NOME.getAlias()));
				turma.setCampoVirtualTotalDeAlunos(rs.getInt(EnumTbTurma.CAMPO_VIRTUAL_TOTAL_DE_ALUNOS.getAlias()));
				
				switch (rs.getString(EnumTbTurma.STATUS.getAlias())) {
				case "A":
					turma.setStatus(EnumStatus.ATIVO.getDescricao());
					break;
				case "I":
					turma.setStatus(EnumStatus.INATIVO.getDescricao());
					break;
				}
				
				Usuario professor = new Usuario();
				professor.setCodUsuario(rs.getInt(EnumTbUsuario.COD_USUARIO.getAlias()));
				professor.setNome(rs.getString(EnumTbUsuario.NOME.getAlias()));
				turma.setUsuario(professor);
				
				turmas.add(turma);
			}
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
			
		return turmas;
	}

	public Turma buscarTurmaPorId(Integer codTurma) throws SQLException {
		Turma turma = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_TurmaBuscarTurmaPorId(?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodTurma", codTurma);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				turma = new Turma();
				turma.setCodTurma(rs.getInt(EnumTbTurma.COD_TURMA.getAlias()));
				turma.setNome(rs.getString(EnumTbTurma.NOME.getAlias()));
				turma.setCampoVirtualTotalDeAlunos(rs.getInt(EnumTbTurma.CAMPO_VIRTUAL_TOTAL_DE_ALUNOS.getAlias()));
				
				switch (rs.getString(EnumTbTurma.STATUS.getAlias())) {
				case "A":
					turma.setStatus(EnumStatus.ATIVO.getDescricao());
					break;
				case "I":
					turma.setStatus(EnumStatus.INATIVO.getDescricao());
					break;
				}
				
				Usuario professor = new Usuario();
				professor.setCodUsuario(rs.getInt(EnumTbUsuario.COD_USUARIO.getAlias()));
				professor.setNome(rs.getString(EnumTbUsuario.NOME.getAlias()));
				turma.setUsuario(professor);
			}
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
			
		return turma;
	}

}
