package com.javaee.escola.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {

	public static void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	public static void addErrorMessage(String clientId, String message) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	public static void addInfoMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}
	
	public static void addInfoMessage(String clientId, String message) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}

	public static void addWarnMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
	}
	
	public static void addWarnMessage(String clientId, String message) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
	}
	
	public static void redirecionarParaPagina(String pagina) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) facesContext.getApplication()
				.getNavigationHandler();

		StringBuilder outcome = new StringBuilder();
		outcome.append(pagina).append("?faces-redirect=true");

		nav.performNavigation(outcome.toString());
	}

	/**
	 * Redireciona p/ a pagina e inclui parametros na url.
	 * 
	 * @param pagina
	 *            Pagina p/ redirecionar.
	 * 
	 * @param parametros
	 *            Parametros. Map<NomeParametro, ValorParametro> ...
	 */
	public static void redirecionarParaPaginaComParametros(String pagina, Map<String, String> parametros) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) facesContext.getApplication()
				.getNavigationHandler();

		StringBuilder outcome = new StringBuilder();
		outcome.append(pagina).append("?faces-redirect=true");

		// Monta os parametros fornecidos na url e aplica o encoding no valor de
		// cada um
		try {
			for (Entry<String, String> entry : parametros.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				outcome.append("&").append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
			}

		} catch (UnsupportedEncodingException e) {
		}

		nav.performNavigation(outcome.toString());
	}
}
