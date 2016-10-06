package com.appc.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Base64FileUtil {
	public static String imageToBase64(String path) {
		byte[] data = null;
		try {
			InputStream in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "data:" + getContentType(path) + ";base64," + Base64.encode(data);
	}

	public static boolean base64ToImage(String base64, String path) {
		if (base64 == null) {
			return false;
		}
		if (base64.indexOf(",") > 0) {
			base64 = base64.substring(base64.indexOf(",") + 1);
		}
		try {
			byte[] bytes = Base64.decode(base64);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					int tmp49_48 = i;
					byte[] tmp49_47 = bytes;
					tmp49_47[tmp49_48] = (byte) (tmp49_47[tmp49_48] + 256);
				}
			}

			OutputStream out = new FileOutputStream(path);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private static String getContentType(String pathToFile) {
		Path path = Paths.get(pathToFile, new String[0]);
		String contentType = "image/png";
		try {
			contentType = Files.probeContentType(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentType;
	}

	public static void main(String[] args) {
		System.out.println(imageToBase64("D:\\7.jpg"));
	}
}