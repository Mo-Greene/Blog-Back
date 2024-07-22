package com.mo.mlog.api.blog.dto.response;

import com.mo.mlog.common.util.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DetailPostResponse {

	private Long id;
	private String tagName;
	private String title;
	private String content;
	private String createdAt;

	public DetailPostResponse(Long id, String tagName, String title, String content, LocalDateTime createdAt) {
		this.id = id;
		this.tagName = tagName;
		this.title = title;
		this.content = content;
		this.createdAt = DateUtil.dateTimeFormat(createdAt);
	}
}
