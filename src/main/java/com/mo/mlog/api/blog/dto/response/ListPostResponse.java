package com.mo.mlog.api.blog.dto.response;

import com.mo.mlog.common.util.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ListPostResponse {

	private Long id;
	private String title;
	private String preview;
	private String thumbnail;
	private String tagName;
	private String createdAt;

	public ListPostResponse(Long id, String title, String preview, String thumbnail, String tagName, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.preview = preview;
		this.thumbnail = thumbnail;
		this.tagName = tagName;
		this.createdAt = DateUtil.dateTimeFormat(createdAt);
	}
}
