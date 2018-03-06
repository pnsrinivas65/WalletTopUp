package com.altimetrik.wallet.walrew.api.service.util;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.jasypt.util.text.BasicTextEncryptor;

public class PasswordEncryption {

	public String encrypt(String tobeEncrypt) 
			throws NoSuchAlgorithmException, NoSuchProviderException {

		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword("SecretKey0206");
		String encrypted = encryptor.encrypt(tobeEncrypt);
		return encrypted;
	}
	
	public String decrypt(String tobeDecrypt) 
			throws NoSuchAlgorithmException, NoSuchProviderException {

		BasicTextEncryptor decryptor = new BasicTextEncryptor();
		decryptor.setPassword("SecretKey0206");  
		String decrypted = decryptor.decrypt(tobeDecrypt);
		return decrypted;
	}

}
