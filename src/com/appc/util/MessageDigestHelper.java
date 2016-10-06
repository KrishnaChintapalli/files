package com.appc.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestHelper {

	public static String md5(String input) {
		return md5(input, Charset.defaultCharset());
	}

	public static String md5(String input, Charset charset) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes(charset));
			return HexUtils.convert(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError("MD5 not available");
		}
	}
}