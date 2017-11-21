package com.javaee.escola.utils;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class PaginasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String SPRING_SEC_CHECK = "/j_spring_security_check";
	public static final String SPRING_SEC_LOGOUT = "/j_spring_security_logout";

	public static final String TEMPLATE = "/template.xhtml";
	
	
	public static final String ACESSO_NEGADO = "/acessonegado.xhtml";
	public static final String PAGINA_404 = "/404.xhtml";
	public static final String ERRO = "/erro.xhtml";
	public static final String LOGIN  = "/login.xhtml";
	
	public static final String CADASTRO_PROFESSOR = "/administrador/cadastro-professor.xhtml";
	public static final String CADASTRO_ALUNO = "/administrador/cadastro-aluno.xhtml";
	public static final String CADASTRO_TURMAS = "/administrador/cadastro-turmas.xhtml";
	public static final String NOVA_TURMA = "/administrador/nova-turma.xhtml";
	
	public static final String PORTAL_ALUNO = "/aluno/portal-aluno.xhtml";
	
	public static final String ANDAMENTO_ATIVIDADES = "/professor/andamento-atividade.xhtml";
	public static final String ATIVIDADES = "/professor/atividades.xhtml";
	public static final String TURMAS = "/professor/turmas.xhtml";
	
	public String getSpringSecCheck() {
		return SPRING_SEC_CHECK;
	}
	public String getSpringSecLogout() {
		return SPRING_SEC_LOGOUT;
	}
	public String getTemplate() {
		return TEMPLATE;
	}
	public String getAcessoNegado() {
		return ACESSO_NEGADO;
	}
	public String getPagina404() {
		return PAGINA_404;
	}
	public String getErro() {
		return ERRO;
	}
	public String getLogin() {
		return LOGIN;
	}
	public String getCadastroProfessor() {
		return CADASTRO_PROFESSOR;
	}
	public String getCadastroAluno() {
		return CADASTRO_ALUNO;
	}
	public String getCadastroTurmas() {
		return CADASTRO_TURMAS;
	}
	public String getNovaTurma() {
		return NOVA_TURMA;
	}
	public String getPortalAluno() {
		return PORTAL_ALUNO;
	}
	public String getAndamentoAtividades() {
		return ANDAMENTO_ATIVIDADES;
	}
	public String getAtividades() {
		return ATIVIDADES;
	}
	public String getTurmas() {
		return TURMAS;
	}
	
	
}
