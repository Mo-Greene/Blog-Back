package com.mo.mlog.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlSlugUtil {

	/**
	 * slug 디코딩
	 *
	 * @param slug 슬러그
	 */
	public static String decodeSlug(String slug) {
		return URLDecoder.decode(slug, StandardCharsets.UTF_8);
	}

	/**
	 * Title Slug Generate
	 *
	 * @param title 제목
	 */
	public static String generateSlug(String title) {

		String slug = title.trim()
			.replaceAll("[\\s]+", "-")
			.replaceAll("[^\\w가-힣-]", "")
			.replaceAll("-{2,}", "-")
			.replaceAll("^-|-$", "");

		return URLEncoder.encode(slug, StandardCharsets.UTF_8);
	}
}
