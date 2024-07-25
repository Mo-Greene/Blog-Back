package com.mo.mlog.api.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record PostRequest(

	@NotEmpty(message = "제목을 적어주세요.")
	@Size(max = 100, message = "제목은 100자 이하로 적어주세요.")
	String title,

	@NotEmpty(message = "내용을 적어주세요.")
	String content,

	@NotEmpty(message = "태그를 제외한 내용을 보내주세요.")
	String plainContent,

	Long tagId,

	MultipartFile thumbnail

) {
}
