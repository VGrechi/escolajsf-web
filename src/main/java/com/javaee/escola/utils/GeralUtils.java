package com.javaee.escola.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeralUtils {
	
	public static String criptografaStringSHA256(String texto){
		String retorno = null;
		
		if(texto != null){
			StringBuilder sb = new StringBuilder();
			
			try{
				MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
				byte[] senhaDigerida = algoritmo.digest(texto.getBytes());
				
				for (byte b : senhaDigerida) {
					sb.append(String.format("%02X", 0xFF & b));
				}
				
				retorno = sb.toString();
			}catch(NoSuchAlgorithmException e){
				
			}
		}
		
		return retorno;
		
		
	}
}
