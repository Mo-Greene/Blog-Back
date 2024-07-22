package com.mo.mlog.common.response;

import java.util.Collections;

public record CommonResponse<T>(

	Result result,
	T data

) {
	public enum Result {
		SUCCESS,
		FAIL
	}

	public static <T> CommonResponse<?> ok() {
		return new CommonResponse<>(
			Result.SUCCESS,
			Collections.EMPTY_LIST
		);
	}

	public static <T> CommonResponse<?> ok(T data) {
		return new CommonResponse<>(
			Result.SUCCESS,
			data
		);
	}

	public static <T> CommonResponse<?> fail(String message) {
		return new CommonResponse<>(
			Result.FAIL,
			message
		);
	}
}
