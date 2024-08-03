package com.mo.mlog.common.util;

import com.mo.mlog.common.feign.github.GithubFeign;
import com.mo.mlog.common.feign.github.dto.CreateContentBodyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class GithubUtil {

	private final GithubFeign githubFeign;

	@Value("${github.owner}")
	private String owner;

	@Value("${github.token}")
	private String token;

	@Value("${github.repo}")
	private String repo;

	private static final String ACCEPT = "application/vnd.github+json";
	private static final String API_VERSION = "2022-11-28";

	/**
	 * 블로그 게시글 깃허브 커밋
	 *
	 * @param title   제목
	 * @param content 내용
	 */
	public void commitGithub(String title, String content) {

		CreateContentBodyRequest request = CreateContentBodyRequest.builder()
			.message(title)
			.content(content)
			.build();

		String authorization = "Bearer " + token;
		String path = LocalDate.now() + " " + title;

		githubFeign.createContent(
			ACCEPT, authorization, API_VERSION, owner, repo, path, request
		);
	}
}
