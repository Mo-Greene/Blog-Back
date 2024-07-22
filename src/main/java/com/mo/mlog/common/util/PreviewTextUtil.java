package com.mo.mlog.common.util;

public class PreviewTextUtil {

	public static String previewText(String text) {

		if (text.length() > 150) {
			return text.substring(0, 149);
		} else {
			return text;
		}
	}
}
