package com.mo.mlog.common.util;

import java.text.Normalizer;

public class UrlSlugUtil {

	/**
	 * Title Slug Generate
	 *
	 * @param title 제목
	 */
	public static String generateSlug(String title) {

		String normalizedTitle = Normalizer.normalize(title, Normalizer.Form.NFKC);

		return normalizedTitle.trim()
			.replaceAll("[\\s]+", "-")
			.replaceAll("[^\\w가-힣-]", "")
			.replaceAll("-{2,}", "-")
			.replaceAll("^-|-$", "");
	}
}
