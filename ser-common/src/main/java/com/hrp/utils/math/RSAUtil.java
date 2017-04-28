package com.hrp.utils.math;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

import com.hrp.utils.io.Base64;

/** 
 * @ClassName	RSAUtil.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月17日 下午6:49:04
 * @Version 	V1.0    
 */
public class RSAUtil {

	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private RSAUtil() {
		throw new Error("工具类不能实例化！");
	}

	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		HashMap keyMap = new HashMap(2);
		keyMap.put("RSAPublicKey", publicKey);
		keyMap.put("RSAPrivateKey", privateKey);
		return keyMap;
	}

	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get("RSAPublicKey");
		return Base64.encode(String.valueOf(key.getEncoded()));
	}

	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get("RSAPrivateKey");
		return Base64.encode(String.valueOf(key.getEncoded()));
	}

	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decode(privateKey).getBytes();
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateK);
		signature.update(data);
		return Base64.encode(String.valueOf(signature.sign()));
	}

	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = Base64.decode(publicKey).getBytes();
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64.decode(sign).getBytes());
	}

	public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
		byte[] keyBytes = Base64.decode(key).getBytes();
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(1, privateKey);
		return cipher.doFinal(data);
	}

	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		byte[] keyBytes = Base64.decode(key).getBytes();
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(2, privateKey);
		return cipher.doFinal(data);
	}

	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		byte[] keyBytes = Base64.decode(key).getBytes();
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(1, publicKey);
		return cipher.doFinal(data);
	}

	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		byte[] keyBytes = Base64.decode(key).getBytes();
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(2, publicKey);
		return cipher.doFinal(data);
	}
	
}
 