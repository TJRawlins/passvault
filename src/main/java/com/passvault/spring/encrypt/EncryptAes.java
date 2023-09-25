package com.passvault.spring.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// AES - Advanced Encryption Standard
public class EncryptAes {
	// Secret key property
	private static SecretKeySpec secretKey;
	// create byte array to hold key
	private static byte[] key;
	
	// set key
	public static void setKey(String myKey) {
		try {
			key = myKey.getBytes("UTF-8");
			// Checksum - error detection, validation
			// Hash function - used to produce the checksum
			// Hash value - uniquely fixed length numeric value that identifies data
			// Message digest - fixed sized numeric representation of message contents computed by hash function
			// SHA-1 & SHA-256 - Secure Hashing Algorithm
			
			// Create Message Digest object using SHA-256 algorithm
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			// Create the digest (key)
			key = sha.digest(key);
			// Generate copy of key using a new length of bytes
			key = Arrays.copyOf(key, 16);
			// Instantiate secret key using the key and AES 
			secretKey = new SecretKeySpec(key, "AES");
			
		} 
		catch (NoSuchAlgorithmException e) {}
		catch (UnsupportedEncodingException e) {}
	}
	
	// Encryption
	public static String encrypt(String password, String secKey) {
		try {
			// Set key
			setKey(secKey);
			// Create the cipher instance
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// Initialize the cipher using encrypt mode and instantiated secret key
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			// Return encrypted text
			return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
		}
		catch (Exception e) {	
		}
		return null;
	}
	
	// Decryption
	public static String decrypt(String password, String secKey) {
		try {
			// Set key
			setKey(secKey);
			// Create the cipher instance
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// Initialize the cipher using decrypt mode and instantiated secret key
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			// Return decrypted text
			return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
		}
		catch (Exception e) {	
		}
		return null;
	}	

}
