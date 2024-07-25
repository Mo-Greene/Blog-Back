package com.mo.mlog.api.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

	private final AmazonS3Client amazonS3Client;

	private static final long EXPIRE_TIME = 1000 * 60 * 10;
	private static final String BUCKET_BOARD = "board/";

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	/**
	 * 프리사인드 url 작성
	 */
	public String generatePresignedUrlForPut() {

		Date expiration = new Date();
		long expireTime = expiration.getTime();
		expireTime += EXPIRE_TIME;
		expiration.setTime(expireTime);

		String objectKey = BUCKET_BOARD + UUID.randomUUID();

		return amazonS3Client.generatePresignedUrl(bucket, objectKey, expiration, HttpMethod.PUT).toString();
	}
}
