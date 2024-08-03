package com.mo.mlog.common.feign.github.dto;

import com.mo.mlog.common.util.DateUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@NoArgsConstructor
public class CreateContentBodyRequest {

	private String message;
	private String content;

	@Builder
	public CreateContentBodyRequest(String message, String content) {
		LocalDateTime now = LocalDateTime.now();
		this.message = DateUtil.dateTimeFormat(now) + " " + message;
		this.content = Base64.getEncoder().encodeToString(content.getBytes());
	}
}
