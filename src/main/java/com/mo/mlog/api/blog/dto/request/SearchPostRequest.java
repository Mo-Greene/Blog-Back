package com.mo.mlog.api.blog.dto.request;

public record SearchPostRequest(

	Long lastPostId,
	Long tagId,
	String title

) {
}
