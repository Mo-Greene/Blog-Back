package com.mo.mlog.common.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AWS {

	@Getter
	private static String awsUrl;

	@Value("${aws.bucket.url}")
	public void setAwsUrl(String awsUrl) {
		AWS.awsUrl = awsUrl;
	}

}
