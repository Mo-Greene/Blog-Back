package com.mo.mlog.common.feign.github.dto;

import lombok.Getter;

@Getter
public class GithubUserResponse {

	private Long id;
	private String node_id;
	private String login;

}
