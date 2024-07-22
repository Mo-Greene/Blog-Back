package com.mo.mlog.common.exception;

public class EntityException extends BaseException {

	public EntityException() {
		super(ErrorCode.COMMON_ENTITY_NOT_FOUND.getErrorMessage());
	}

}
