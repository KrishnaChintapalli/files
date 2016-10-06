package com.appc.util;

import java.io.ByteArrayOutputStream;

public final class HexUtils {
	public static String convert(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; ++i) {
			sb.append(convertDigit(bytes[i] >> 4));
			sb.append(convertDigit(bytes[i] & 0xF));
		}
		return sb.toString();
	}

	public static byte[] convert(String s) {
		if (s.length() % 2 != 0) {
			throw new IllegalArgumentException("Not a valid encoding.");
		}

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte b = 0;
		boolean msb = true;
		for (char c : s.toCharArray()) {
			if (msb) {
				b = (byte) (b | convertChar(c) << 4);
				msb = false;
			} else {
				b = (byte) (b | convertChar(c));
				result.write(b);
				b = 0;
				msb = true;
			}
		}
		return result.toByteArray();
	}

	private static char convertDigit(int value) {
		value &= 15;
		if (value >= 10) {
			return (char) (value - 10 + 97);
		}
		return (char) (value + 48);
	}

	private static byte convertChar(char value) {
		if ((value >= 'a') && (value <= 'f'))
			return (byte) ('\n' + value - 97);
		if ((value >= 'A') && (value <= 'F'))
			return (byte) ('\n' + value - 65);
		if ((value >= '0') && (value <= '9')) {
			return (byte) (value - '0');
		}
		throw new IllegalArgumentException();
	}
}