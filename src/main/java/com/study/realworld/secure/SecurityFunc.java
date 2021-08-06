package com.study.realworld.secure;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.study.realworld.common.Func;

public class SecurityFunc {
	public static final String RSA_ALGORITHM	= "RSA";
	public static final String AES_ALGORITHM	= "AES";
	public static String AES_ALGORITHM_ENCRYPT = "AES/ECB/PKCS5Padding";
	
	private static final String SESSION_SECURITY_KEY = "SECURITY_KEY";
	private static final int AES_LEN = 16;

	public static <T> String encryptData(final String algorithm, final Key key, final T data) {
		String json = new Gson().toJson(data);
		if( null == json ) return null;
		
		try {
			return encrypt(algorithm, key, json);
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encrypt(final String algorithm, final Key key, final String data) throws Exception {
		return encrypt(algorithm, key, data.getBytes("utf-8"));
	}

	public static String encrypt(final String algorithm, final Key key, final byte[] bytes) throws Exception {
		
		String cipherAlgorithm = algorithm;
		
		if( algorithm.equals(AES_ALGORITHM) ) {
			cipherAlgorithm = AES_ALGORITHM_ENCRYPT;
		}
		
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		 
		byte[] encryptBytes = cipher.doFinal(bytes);

		return DatatypeConverter.printHexBinary(encryptBytes);
	}
	
	public static <T> T decryptData(final Key key, final String bin, final Class<T> classOfT) throws Exception {		
		return SecurityFunc.decryptData(SecurityFunc.AES_ALGORITHM, key, bin, classOfT);
	}
	
	public static <T> T decryptData(final String algorithm, final Key key, final String bin, final Class<T> classOfT) throws Exception {
		String json = decrypt(algorithm, key, bin);
		if( null == json ) return null;
		
		return new Gson().fromJson(json, classOfT);		
	}
	
	public static String decrypt(final String algorithm, Key key, String binData) throws Exception {
		
		byte[] bytes = DatatypeConverter.parseHexBinary(binData);
		
		if( null == bytes ) return null;
		
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		 
		byte[] decryptedBytes = cipher.doFinal(bytes);
		
		return new String(decryptedBytes, "utf-8");
	}
	
	public static Key generateKey(String algorithm, byte[] keyData) throws NoSuchAlgorithmException {
		return new SecretKeySpec(keyData, algorithm);
	}
	
	public static Key generateRSAKey(String modulus, String exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return generateRSAKey(new BigInteger(modulus, 16), new BigInteger(exponent, 16));
	}
	
	public static Key generateRSAKey(BigInteger modulus, BigInteger exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, exponent);
		 
	    KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
	     
	    return keyFactory.generatePublic(pubKeySpec);
	}
	
	public static class ResKey {
		String key;
		public ResKey(String key) {
			this.key = key;
		}
	}
	
	public static void reqKey(HttpSession session, String modulus, String exponent, PrintWriter out) throws Exception {
		Key aeskey = (Key) session.getAttribute( SESSION_SECURITY_KEY );
		if (null == aeskey) {
			String strHex = SecureGUIDGenerator.genHex(AES_LEN);
			aeskey = generateKey(SecurityFunc.AES_ALGORITHM, strHex.getBytes());
			session.setAttribute(SESSION_SECURITY_KEY, aeskey);
		}
		
		String strAseKey = DatatypeConverter.printHexBinary(aeskey.getEncoded());
		
		Key rsaPubKey = generateRSAKey(modulus, exponent);

		String bin = SecurityFunc.encryptData(RSA_ALGORITHM, rsaPubKey, new ResKey(strAseKey));
		
		JsonObject result = new JsonObject();
		result.addProperty("bin", bin);
		out.write(Func.getResultJson(result));
	}
	
	public static Key getKey( HttpSession session ) throws Exception {
		return (Key) session.getAttribute( SESSION_SECURITY_KEY );
	}
}