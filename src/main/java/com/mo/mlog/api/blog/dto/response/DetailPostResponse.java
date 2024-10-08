package com.mo.mlog.api.blog.dto.response;

import com.mo.mlog.common.util.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DetailPostResponse {

	private Long id;
	private String tagName;
	private String title;
	private String content;
	private String slug;
	private String createdAt;

	public DetailPostResponse(Long id, String tagName, String title, String content, String slug, LocalDateTime createdAt) {
		this.id = id;
		this.tagName = tagName;
		this.title = title;
		this.content = content;
		this.slug = decodeSlug(slug);
		this.createdAt = DateUtil.dateTimeFormat(createdAt);
	}

	private String decodeSlug(String slug) {
		return URLDecoder.decode(slug, StandardCharsets.UTF_8);
	}
}
