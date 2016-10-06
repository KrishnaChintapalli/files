package com.appc.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	private static Properties properties;

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	static {
		try {
			InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream("conf/conf.properties");
			try {
				properties = new Properties();
				properties.load(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}