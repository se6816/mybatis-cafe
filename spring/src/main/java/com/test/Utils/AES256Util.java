package com.test.Utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class AES256Util {
	private String iv; 
	private Key keySpec;
	private final String Alg="AES/CBC/PKCS5Padding";
	public AES256Util(String key) throws UnsupportedEncodingException {
		this.iv = key.substring(0, 16); 
		SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES"); 
		this.keySpec = keySpec; 
	}
	public String encrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException{
		Cipher c = Cipher.getInstance(Alg); 
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes())); 
		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = Base64.encodeBase64URLSafeString(encrypted); 
		return enStr;
		
	} 
	public String decrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException { 
		Cipher c = Cipher.getInstance(Alg); 
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes())); 
		byte[] byteStr = Base64.decodeBase64(str);
		return new String(c.doFinal(byteStr), "UTF-8"); 
	} 
}


