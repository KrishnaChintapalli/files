package com.appc.util.html.renderer;

import java.util.HashMap;
import java.util.Map;

public class FormatNameUtil {
	public static Map<String, String> types = new HashMap();
	private static final String DEFAULT_FORMAT = "png";

	public static String formatForExtension(String extension) {
		String type = (String) types.get(extension);
		if (type == null) {
			return "png";
		}
		return type;
	}

	public static String getDefaultFormat() {
		return "png";
	}

	public static String formatForFilename(String fileName) {
		int dotIndex = fileName.lastIndexOf(46);
		if (dotIndex < 0) {
			return "png";
		}
		String ext = fileName.substring(dotIndex + 1);
		return formatForExtension(ext);
	}

	static {
		types.put("gif", "gif");
		types.put("jpg", "jpg");
		types.put("jpeg", "jpg");
		types.put("png", "png");
		types.put("bmp", "bmp");
	}
}