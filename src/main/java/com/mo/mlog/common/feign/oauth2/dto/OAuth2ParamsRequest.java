package com.mo.mlog.common.feign.oauth2.dto;

public record OAuth2ParamsRequest(
	String client_id,
	String client_secret,
	String code
) {
}
