package com.javaee.escola.modelo.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaee.escola.modelo.TipoUsuario;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.enums.tabelas.EnumTbTipoUsuario;
import com.javaee.escola.modelo.enums.tabelas.EnumTbUsuario;
import com.javaee.escola.utils.GeralUtils;
import com.javaee.escola.utils.RNBancoException;
import com.javaee.escola.utils.SistemaException;

public class UsuarioDAO extends DAOImpl implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Connection connection;

	public UsuarioDAO(Connection connection) {
		super();
		this.connection = connection;
	}


	public Usuario buscarParaLogin(String nomeLogin, String senha) throws SQLException, RNBancoException {
		Usuario usuario = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_UsuarioBuscarParaLogin(?, ?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setString("ParamEmail", nomeLogin);
			stmt.setString("ParamSenha", GeralUtils.criptografaStringSHA256(senha));
			stmt.registerOutParameter("OutParamResult", java.sql.Types.INTEGER);
			rs = stmt.executeQuery();
			
			if(rs.next()){			
				usuario = new Usuario();
				usuario.setCodUsuario(rs.getInt(EnumTbUsuario.COD_USUARIO.getAlias()));
				usuario.setNome(rs.getString(EnumTbUsuario.NOME.getAlias()));
				usuario.setEmail(rs.getString(EnumTbUsuario.EMAIL.getAlias()));
				usuario.setSenha(rs.getString(EnumTbUsuario.SENHA.getAlias()));
				
				TipoUsuario tipo = new TipoUsuario();
				tipo.setTipoUsuario(rs.getString(EnumTbTipoUsuario.TIPO.getAlias()));
				tipo.setDescricao(rs.getString(EnumTbTipoUsuario.DESCRICAO.getAlias()));
				usuario.setTipo(tipo);
			}
			
			verificaParametrosDeSaida(stmt.getInt("OutParamResult"));
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
			
		return usuario;
	}
	
	public List<Usuario> buscarTodos(String tipoUsuario) throws SQLException {
		List<Usuario> usuarios = new ArrayList<>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "{CALL Proc_UsuarioBuscarTodos(?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setString("ParamTipoUsuario", tipoUsuario);
			rs = stmt.executeQuery();
			
			while(rs.next()){			
				Usuario usuario = new Usuario();
				usuario.setCodUsuario(rs.getInt(EnumTbUsuario.COD_USUARIO.getAlias()));
				usuario.setNome(rs.getString(EnumTbUsuario.NOME.getAlias()));
				usuario.setEmail(rs.getString(EnumTbUsuario.EMAIL.getAlias()));
				
				TipoUsuario tipo = new TipoUsuario();
				tipo.setTipoUsuario(rs.getString(EnumTbUsuario.TIPO_USUARIO.getAlias()));
				usuario.setTipo(tipo);
				
				usuarios.add(usuario);
			}
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
			rs.close();
		}
		return usuarios;
	}


	public void inserirOuAtualizar(Usuario usuario) throws SQLException {
		CallableStatement stmt = null;
		String sql = "{CALL Proc_UsuarioInserirOuAtualizar(?, ?, ?, ?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodUsuario", seNullEntaoZero(usuario.getCodUsuario()));
			stmt.setString("ParamTipoUsuario", usuario.getTipo().getTipoUsuario());
			stmt.setString("ParamNome", usuario.getNome());
			stmt.setString("ParamEmail", usuario.getEmail());
			stmt.setString("ParamSenha", GeralUtils.criptografaStringSHA256(usuario.getSenha()));
			stmt.execute();
			
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		
	}


	public void deletar(Usuario usuario) throws SQLException, SistemaException, RNBancoException {
		CallableStatement stmt = null;
		Integer paramResult = 0;
		String sql = "{CALL Proc_UsuarioRemoverUsuario(?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodUsuario", seNullEntaoZero(usuario.getCodUsuario()));
			stmt.registerOutParameter("ParamResult", java.sql.Types.INTEGER);
			stmt.execute();
			
			paramResult = stmt.getInt("ParamResult");
			verificaParametrosDeSaida(paramResult);
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		
	}


	public void alterarSenha(Integer codUsuario, String novaSenha) throws SQLException {
		CallableStatement stmt = null;
		String sql = "{CALL Proc_UsuarioAlterarSenha(?, ?)}";
		
		try{
			stmt = connection.prepareCall(sql);
			stmt.setInt("ParamCodUsuario", codUsuario);
			stmt.setString("ParamSenha", GeralUtils.criptografaStringSHA256(novaSenha));
			stmt.executeUpdate();
			
		}catch(SQLException e){
			throw e;
		}finally {
			stmt.close();
		}
		
	}



}
