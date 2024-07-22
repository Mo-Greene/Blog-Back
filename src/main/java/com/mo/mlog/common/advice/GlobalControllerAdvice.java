package com.mo.mlog.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mo.mlog.common.exception.ErrorCode;
import com.mo.mlog.common.response.CommonResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

	/**
	 * http status : 400 AND result : FAIL 파라미터 에러
	 * @param e MethodArgumentNotValidException
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public CommonResponse<?> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		BindingResult bindingResult = e.getBindingResult();
		FieldError fieldError = bindingResult.getFieldError();
		if (fieldError != null) {
			String message = "Request Error " + fieldError.getField() + " = " + fieldError.getRejectedValue() + " (" + fieldError.getDefaultMessage() + ")";
			return CommonResponse.fail(message);
		} else {
			return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorMessage());
		}
	}
}
