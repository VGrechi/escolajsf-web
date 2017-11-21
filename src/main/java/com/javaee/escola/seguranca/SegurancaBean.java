package com.javaee.escola.seguranca;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.javaee.escola.modelo.Usuario;
import com.javaee.escola.modelo.fachada.UsuarioFachada;
import com.javaee.escola.utils.FacesUtil;
import com.javaee.escola.utils.GeralUtils;
import com.javaee.escola.utils.SistemaException;

@Named
@SessionScoped
public class SegurancaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioLogado;
	
	private String senhaAtual;
	private String novaSenha;
	private String confirmarSenha;
	
	
	public void alterarSenha() throws SistemaException{
		if(GeralUtils.criptografaStringSHA256(senhaAtual).equals(usuarioLogado.getSenha())){
			if(novaSenha.equals(confirmarSenha)){
				UsuarioFachada fachada = new UsuarioFachada();
				fachada.alterarSenha(usuarioLogado.getCodUsuario(), novaSenha);
				usuarioLogado.setSenha(GeralUtils.criptografaStringSHA256(novaSenha));
				
				RequestContext.getCurrentInstance().execute("PF('dialogTrocarSenhaWidgetVar').hide();");
			}else{
				FacesUtil.addErrorMessage("As senhas não conferem.");
			}
		}else{
			FacesUtil.addErrorMessage("Senha Atual incorreta.");
		}
	}
	
	public void abrirDialogAlterarSenha(){
		senhaAtual = null;
		novaSenha = null;
		confirmarSenha = null;
		RequestContext.getCurrentInstance().execute("PF('dialogTrocarSenhaWidgetVar').show();");
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

}
