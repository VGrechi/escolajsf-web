package com.javaee.escola.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class CriptografiaPBE {
	
	private static final String PASSWORD = "Ecp10";

	public static final String cifrar(String str) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(PASSWORD);
		return encryptor.encrypt(str);
	}
	
	public static final String decifrar(String str) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(PASSWORD);
		return encryptor.decrypt(str);
	}

}
