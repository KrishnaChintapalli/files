package com.appc.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HMACMD5 {
	public static String LOGIN_PASS_KEY = "LIVEXASXLBD";
	public static String FILE_KEY = "FILEAAFFAAB";

	public static String printHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; ++i) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			result = result + hex.toLowerCase();
		}
		return result;
	}

	public static String toHmacMd5(String content, String key)
			throws NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException, InvalidKeyException {
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "HmacMD5");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		byte[] b = mac.doFinal(content.getBytes("UTF-8"));
		return printHexString(b);
	}

	public static void main(String[] args)
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
		System.out.println(toHmacMd5("804148", "TAXIZSMJLBD"));
	}
}