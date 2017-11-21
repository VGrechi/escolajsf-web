package com.javaee.escola.modelo.fachada;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.javaee.escola.modelo.Atividade;
import com.javaee.escola.modelo.Turma;
import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.dao.AtividadeDAO;
import com.javaee.escola.modelo.dao.DisciplinaDAO;
import com.javaee.escola.modelo.dao.TarefaDAO;
import com.javaee.escola.modelo.dao.TurmaDAO;
import com.javaee.escola.modelo.dao.UsuarioDAO;
import com.javaee.escola.modelo.enums.EnumUsuarioAutenticadoRoles;
import com.javaee.escola.utils.Out;
import com.javaee.escola.utils.SistemaException;
import com.javaee.escola.utils.SistemaExceptionEnum;

public class TurmaFachada extends FachadaImpl implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public List<Turma> buscarTodos() throws SistemaException {
		List<Turma> turmas = null;
		Connection connection = null;
		
		try{
			connection = conectar();
			TurmaDAO dao = new TurmaDAO(connection);
			turmas = dao.buscarTodos();
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
		return turmas;
	}
	
	public void buscarTurmaPorId(Integer codTurma, Out<List<Usuario>> outAlunos, Out<List<Usuario>> outProfessores, Out<Turma> outTurma) throws SistemaException {
		Connection connection = null;

		try{
			connection = conectar();
			TurmaDAO dao = new TurmaDAO(connection);
			UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
			DisciplinaDAO disciplinaDAO = new DisciplinaDAO(connection);
			
			Turma turma = dao.buscarTurmaPorId(codTurma);
			List<Usuario> alunosPorTurma = disciplinaDAO.buscarAlunosPorTurma(turma.getCodTurma());
			turma.setAlunos(alunosPorTurma);
			outTurma.set(turma);
			
			List<Usuario> alunos = usuarioDAO.buscarTodos(EnumUsuarioAutenticadoRoles.ALUNO.getTipo());
			outAlunos.set(alunos);
			
			List<Usuario> professores = usuarioDAO.buscarTodos(EnumUsuarioAutenticadoRoles.PROFESSOR.getTipo());
			outProfessores.set(professores);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
	}
	
	public void buscarTurmaSemId(Out<List<Usuario>> outAlunos, Out<List<Usuario>> outProfessores) throws SistemaException {
		Connection connection = null;
	
		try{
			connection = conectar();
			UsuarioDAO dao = new UsuarioDAO(connection);
			
			List<Usuario> alunos = dao.buscarTodos(EnumUsuarioAutenticadoRoles.ALUNO.getTipo());
			outAlunos.set(alunos);
			
			List<Usuario> professores = dao.buscarTodos(EnumUsuarioAutenticadoRoles.PROFESSOR.getTipo());
			outProfessores.set(professores);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
	}
	
	public void inserirOuAtualizarTurma(Turma turma, List<Usuario> alunosParaExclusao) throws SistemaException {
		Connection connection = null;
		
		try{
			connection = conectar();
			setAutoCommit(connection, false);
			
			TurmaDAO dao = new TurmaDAO(connection);
			DisciplinaDAO disciplinaDAO = new DisciplinaDAO(connection);
			
			turma = dao.inserirOuAtualizar(turma);

			for (Usuario aluno : turma.getAlunos()) {
				disciplinaDAO.inserirOuAtualizar(turma.getCodTurma(), aluno.getCodUsuario());
			}
			
			if(!alunosParaExclusao.isEmpty()){
				for (Usuario aluno : alunosParaExclusao) {
					disciplinaDAO.remove(turma.getCodTurma(), aluno.getCodUsuario());
				}
			}
			commitar(connection);
		}catch(SQLException e){
			LOGGER().error(e);
			rollback(connection);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
	}
	
	
	public void buscarTodosPorProfessor(Usuario usuario, Out<List<Turma>> outTurmas, Out<List<Atividade>> outAtividades) throws SistemaException {
		Connection connection = null;
		
		try{
			connection = conectar();
			TurmaDAO dao = new TurmaDAO(connection);
			TarefaDAO tarefaDAO = new TarefaDAO(connection);
			AtividadeDAO atividadeDAO = new AtividadeDAO(connection);
			
			List<Turma> turmas = dao.buscarTodosPorUsuario(usuario);
			for (Turma turma : turmas) {
				List<Atividade> atividadesDaTurma = tarefaDAO.buscarAtividadesPorTurma(turma.getCodTurma());
				turma.setAtividades(atividadesDaTurma);
			}
			outTurmas.set(turmas);
			
			List<Atividade> atividades = atividadeDAO.buscarTodosPorUsuario(usuario);
			outAtividades.set(atividades);
		}catch(SQLException e){
			LOGGER().error(e);
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}finally {
			desconectar();
		}
	}

	
	
}
