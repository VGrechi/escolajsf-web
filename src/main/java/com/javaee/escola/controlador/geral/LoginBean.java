package com.javaee.escola.controlador.geral;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaee.escola.seguranca.AutenticacaoFilter;

@Named
@ViewScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;
	
	public void fazerLogin() throws ServletException, IOException {
		// Recupera a requisicao e a resposta
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = currentInstance.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		// Encaminha p/ AutenticacaoFilter#attemptAuthentication
		RequestDispatcher dispatcher = request.getRequestDispatcher(AutenticacaoFilter.SPRING_SECURITY_CHECK_URL);
		dispatcher.forward(request, response);

		// Devolve a resposta
		currentInstance.responseComplete();
	}
    
	public void deslogar() throws ServletException, IOException {
		// Recupera a requisicao e a resposta
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = currentInstance.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		// Encaminha p/ o Spring Security realizar o logout conforme definido em
		// applicationContext.xml
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(AutenticacaoFilter.SPRING_SECURITY_LOGOUT_URL);
		dispatcher.forward(request, response);

		// Devolve a resposta
		currentInstance.responseComplete();
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

	
	
	
}
