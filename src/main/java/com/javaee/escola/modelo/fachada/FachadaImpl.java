package com.javaee.escola.modelo.fachada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.javaee.escola.utils.SistemaException;
import com.javaee.escola.utils.SistemaExceptionEnum;

public class FachadaImpl {
	
	private Log LOGGER = LogFactory.getLog(getClass());

	private Connection connection;
	
	private String URL_SERVIDOR = "jdbc:mysql://localhost:3306/escolajsf";
	private String USUARIO = "root";
	private String SENHA = "keepcalm";
	private String DRIVER = "com.mysql.jdbc.Driver";
	
	protected Log LOGGER() {
		return LOGGER;
	}
	
	public Connection conectar() throws SQLException, SistemaException{
		try{
			LOGGER.info("Obtendo conexao...");
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL_SERVIDOR, USUARIO, SENHA);
			LOGGER.info("Conexao obtida: " + connection);
		}catch (SQLException e){
			LOGGER.error("obterConexao >> erro ao tentar obter conexao: " + e);
			throw e;
		} catch (ClassNotFoundException e) {
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_UTILIZAR_BIBLIOTECA);
		}
		return connection;
	}
	
	public boolean estaConectado(){
		if(connection != null){
			return true;
		}else {
			return false;
		}
	}
	
	public void desconectar() throws SistemaException{
		try{
			LOGGER.info("Liberando conexao...");
			connection.close();
			LOGGER.info("Conexao liberada: " + connection);
			LOGGER.info("-----------------------------------------------------------------------\n");
		}catch(SQLException e){
			LOGGER.error("liberarConexao >> erro ao tentar fechar/liberar conexao: " + e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}
	}

	
	public void setAutoCommit(Connection connection, boolean autoCommit) throws SistemaException{
		if (connection != null) {
			try {
				connection.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				LOGGER.error("autoCommitConfiguracaoManual >> erro ao tentar configurar os commit automáticos da conexao: " + e);
				throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
			}
		}
	}
	
	public void commitar(Connection connection) throws SistemaException{
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				LOGGER.error("commitarTransacaoDaConexaoManualmenteGerenciada >> erro ao tentar commitar transacao da conexao: " + e);
				throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
			}
		}
	}
	
	public void rollback(Connection connection) throws SistemaException{
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				LOGGER.error("rollbackTransacaoDaConexaoManualmenteGerenciada >> erro ao tentar rollback transacao da conexao: " + e);
				throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
			}
		}
	}
}
