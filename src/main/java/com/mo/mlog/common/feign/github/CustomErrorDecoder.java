package com.mo.mlog.common.feign.github;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String s, Response response) {
		if (response.status() == 201) {
			return null;
		}
		return new RuntimeException("Failed to create content on GitHub with status: " + response.status());
	}
}
