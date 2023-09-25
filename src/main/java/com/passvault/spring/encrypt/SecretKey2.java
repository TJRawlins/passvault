package com.passvault.spring.encrypt;

public class SecretKey2 {
	
	public static String DecryptSecret() {
		final String secretKeyString = ""; /* <== Enter Secret Key Here AND change class name from SecretKey2 to SecretKey */;
		final String secretKey = EncryptAes.encrypt(secretKeyString, secretKeyString);
		return EncryptAes.decrypt(secretKey, secretKeyString);
	}
	
}
