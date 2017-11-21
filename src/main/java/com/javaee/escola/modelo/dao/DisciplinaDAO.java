package com.javaee.escola.modelo.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaee.escola.modelo.Disciplina;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.enums.tabelas.EnumTbUsuario;

public class DisciplinaDAO extends DAOImpl implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Connection connection;

	public DisciplinaDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public Disciplina inserirOuAtualizar(Integer codTurma, Integer codUsuario) throws SQLException{
		CallableStatement stmt = null;
		Disciplina disciplina = null;
		String sql = "{CALL Proc_DisciplinaInserirOuAtualizar(?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodTurma", codTurma);
			stmt.setInt("ParamCodUsuario", codUsuario);
			stmt.execute();
			
			disciplina = new Disciplina();
			
			Usuario aluno = new Usuario();
			aluno.setCodUsuario(codUsuario);
			disciplina.setUsuario(aluno);
			
			Turma turma = new Turma();
			turma.setCodTurma(codTurma);
			disciplina.setTurma(turma);
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		return disciplina;
	}

	public List<Usuario> buscarAlunosPorTurma(Integer codTurma) throws SQLException{
		List<Usuario> alunos = new ArrayList<>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_DisciplinaBuscarAlunosPorTurma(?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodTurma", codTurma);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Usuario aluno = new Usuario();
				aluno.setCodUsuario(rs.getInt(EnumTbUsuario.COD_USUARIO.getAlias()));
				aluno.setNome(rs.getString(EnumTbUsuario.NOME.getAlias()));
				
				alunos.add(aluno);
			}
			
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		return alunos;
		
	}

	public void remove(Integer codTurma, Integer codUsuario) throws SQLException {
		CallableStatement stmt = null;
		String sql = "{CALL Proc_DisciplinaRemoverDisciplina(?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodTurma", codTurma);
			stmt.setInt("ParamCodUsuario", codUsuario);
			stmt.execute();
		
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		
	}
}
