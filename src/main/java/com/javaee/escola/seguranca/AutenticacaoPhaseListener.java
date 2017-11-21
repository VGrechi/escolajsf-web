package com.javaee.escola.seguranca;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AutenticacaoPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;
	
	public static final String SESSION_KEY_AUTENTICACAO_MSG = "painel_auth_mensagem";

	@Override
	public void afterPhase(PhaseEvent arg) {
	}

	@Override
	public void beforePhase(PhaseEvent arg) {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            FacesMessage mensagem = (FacesMessage) session.getAttribute(SESSION_KEY_AUTENTICACAO_MSG);
 
            if (mensagem != null) {
                context.addMessage(null, mensagem);
                session.setAttribute(SESSION_KEY_AUTENTICACAO_MSG, null);
            }
        }
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
