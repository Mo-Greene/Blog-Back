package com.mo.mlog.api.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FileService {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String generatePresignedUrlForGet(String objectKey) {
		Date expiration = new Date();
		long expireTime = expiration.getTime();
		expireTime += 1000 * 60 * 10;
		expiration.setTime(expireTime);

		return amazonS3Client.generatePresignedUrl(bucket, objectKey, expiration, HttpMethod.GET).toString();
	}
}
