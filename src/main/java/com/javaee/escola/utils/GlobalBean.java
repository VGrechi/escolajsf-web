package com.javaee.escola.utils;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class GlobalBean implements Serializable{

	private static final long serialVersionUID = 1L;

	public String cifrar(String str) {
		return CriptografiaPBE.cifrar(str);
	}
	
	public String decifrar(String str) {
		return CriptografiaPBE.decifrar(str);
	}
	
	public Integer descifrarEConverterParaInteger(String str) {
		Integer intDescifrado = null;
		try {
			intDescifrado = null;
			String strDecifrada = decifrar(str);
			intDescifrado = Integer.parseInt(strDecifrada);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return intDescifrado;
	}
}
