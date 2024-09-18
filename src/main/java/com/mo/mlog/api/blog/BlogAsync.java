package com.mo.mlog.api.blog;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mo.mlog.common.util.GithubUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogAsync {

	private final AmazonS3Client amazonS3Client;
	private final GithubUtil githubUtil;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private static final String BUCKET_THUMBNAIL = "thumbnail/";

	/**
	 * 썸네일 파일 업로드
	 *
	 * @param file 썸네일 파일
	 */
	@Async
	public CompletableFuture<String> uploadFile(MultipartFile file) {

		String name = String.valueOf(UUID.randomUUID());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());

		try {
			amazonS3Client.putObject(
				new PutObjectRequest(bucket, BUCKET_THUMBNAIL + name, file.getInputStream(), objectMetadata)
			);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패");
		}

		return CompletableFuture.completedFuture(BUCKET_THUMBNAIL + name);
	}

	/**
	 * 깃헙 커밋
	 *
	 * @param title   글 제목
	 * @param content 글 내용
	 */
	@Async
	public void commitGithub(String title, String content) {
		try {
			githubUtil.commitGithub(title, content);
		} catch (Exception e) {
			log.error("github commit failed : {}", e.getMessage());
		}
	}
}
