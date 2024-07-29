package com.mo.mlog.api.blog.dto.request;

public record SearchPostRequest(

	Long lastIndex,
	Long tagId,
	String title

) {
}
