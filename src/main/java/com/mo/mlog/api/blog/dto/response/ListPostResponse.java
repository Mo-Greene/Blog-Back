package com.mo.mlog.api.blog.dto.response;

import com.mo.mlog.common.constant.AWS;
import com.mo.mlog.common.util.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class ListPostResponse {

	private Long id;
	private String title;
	private String preview;
	private String thumbnail;
	private Long tagId;
	private String tagName;
	private String createdAt;

	public ListPostResponse(Long id, String title, String preview, String thumbnail, Long tagId, String tagName, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.preview = preview;
		this.thumbnail = generateUrl(thumbnail).orElse(null);
		this.tagId = tagId;
		this.tagName = tagName;
		this.createdAt = DateUtil.dateTimeFormat(createdAt);
	}

	private Optional<String> generateUrl(String thumbnail) {
		return Optional.ofNullable(thumbnail).map(t -> AWS.getAwsUrl() + t);
	}
}
