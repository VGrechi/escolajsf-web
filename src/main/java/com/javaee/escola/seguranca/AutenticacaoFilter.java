package com.javaee.escola.seguranca;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.enums.EnumUsuarioAutenticadoRoles;
import com.javaee.escola.modelo.fachada.UsuarioFachada;
import com.javaee.escola.utils.FacesUtil;
import com.javaee.escola.utils.PaginasBean;
import com.javaee.escola.utils.RNBancoException;
import com.javaee.escola.utils.SistemaException;
import com.javaee.escola.utils.SistemaExceptionEnum;

public class AutenticacaoFilter extends UsernamePasswordAuthenticationFilter implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final String PARAMETER_LOGIN = "j_login";
	private static final String PARAMETER_SENHA = "j_senha";
	
	public static final String SPRING_SECURITY_CHECK_URL = PaginasBean.SPRING_SEC_CHECK;
	public static final String SPRING_SECURITY_LOGOUT_URL = PaginasBean.SPRING_SEC_LOGOUT;
	
	private String mensagem;

	private Usuario usuario;

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// Obtem da requisicao os parametros do formulario
		String login = request.getParameter(PARAMETER_LOGIN);
        String senha = request.getParameter(PARAMETER_SENHA);
        
        try {
            usuario = buscarParaLogin(login, senha);
            if (usuario != null) {    
            	
            	// Roles
            	List<GrantedAuthority> roles = new ArrayList<>();
            	
            	if(usuario.getTipo().getTipoUsuario().equals(EnumUsuarioAutenticadoRoles.ADMINISTRADOR.getTipo())){
            		roles.add(new SimpleGrantedAuthority(EnumUsuarioAutenticadoRoles.ADMINISTRADOR.getRole()));
            	}
            	
            	if(usuario.getTipo().getTipoUsuario().equals(EnumUsuarioAutenticadoRoles.PROFESSOR.getTipo())){
            		roles.add(new SimpleGrantedAuthority(EnumUsuarioAutenticadoRoles.PROFESSOR.getRole()));
            	}
            	
            	if(usuario.getTipo().getTipoUsuario().equals(EnumUsuarioAutenticadoRoles.ALUNO.getTipo())){
            		roles.add(new SimpleGrantedAuthority(EnumUsuarioAutenticadoRoles.ALUNO.getRole()));
            	}

                Collection<GrantedAuthority> authorities = roles;

                // Add o usuario ao bean de sessao Seguranca
                SegurancaBean segurancaSessionBean = CDIServiceLocator.getBean(SegurancaBean.class);
                segurancaSessionBean.setUsuarioLogado(usuario);
                
                // Mensagem de sucesso no login
                this.mensagem = "Bem vindo, " + usuario.getNome() + "!";
                
                // Constroi para o Spring Security o usuario autenticado e seus principais dados
                return new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha(), authorities);
            } else {
                this.mensagem =  "Usuario ou senha inválidos"; 
                throw new BadCredentialsException(mensagem);
            }
        } catch (Exception e) {
        		this.mensagem = "Usuario ou senha inválidos";
        		throw new BadCredentialsException(mensagem);       	
        }
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		
		// Add a mensagem na sessao para o AutenticacaoPhaseListener capturar e redirecionar p/ o JSF
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, this.mensagem, "");
        request.getSession().setAttribute(AutenticacaoPhaseListener.SESSION_KEY_AUTENTICACAO_MSG, facesMessage);
        
        switch (usuario.getTipo().getTipoUsuario()) {
		case "AD":
			FacesUtil.redirecionarParaPagina(PaginasBean.CADASTRO_PROFESSOR);
			break;
		case "PR":
			FacesUtil.redirecionarParaPagina(PaginasBean.TURMAS);
			break;
		case "AL":
			FacesUtil.redirecionarParaPagina(PaginasBean.PORTAL_ALUNO);
			break;
		}
            	
        
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		// Add a mensagem na sessao para o AutenticacaoPhaseListener capturar e redirecionar p/ o JSF
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, this.mensagem, "");
	    request.getSession().setAttribute(AutenticacaoPhaseListener.SESSION_KEY_AUTENTICACAO_MSG, facesMessage);
		
	    FacesUtil.redirecionarParaPagina(PaginasBean.LOGIN);
	}
	
	private Usuario buscarParaLogin(String nomeLogin, String senha) throws SistemaException, RNBancoException{
		Usuario usuario = null;
		try {
			UsuarioFachada usuarioFachada = new UsuarioFachada();
			usuario = usuarioFachada.buscarParaLogin(nomeLogin, senha);
		}catch (SistemaException e) {
			throw new SistemaException(e, SistemaExceptionEnum.ERRO_AO_MANIPULAR_CONEXAO_BANCO_DADOS);
		}
		return usuario;
	}

}
