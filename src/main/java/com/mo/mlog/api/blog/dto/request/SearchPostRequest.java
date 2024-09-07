package com.mo.mlog.api.blog.dto.request;

public record SearchPostRequest(

	Long tagId,
	String title,
	Long cursor

) {
}
