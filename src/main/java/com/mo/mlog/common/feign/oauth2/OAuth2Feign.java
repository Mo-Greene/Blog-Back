package com.mo.mlog.common.feign.oauth2;

import com.mo.mlog.common.feign.oauth2.dto.OAuth2ParamsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "oauth2-github", url = "https://github.com")
public interface OAuth2Feign {

	@PostMapping(value = "/login/oauth/access_token", consumes = "application/json")
	String getAccessToken(@RequestBody OAuth2ParamsRequest params);

}
