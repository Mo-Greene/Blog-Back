package com.mo.mlog.common.feign;

import com.mo.mlog.common.config.FeignConfig;
import com.mo.mlog.common.feign.dto.CreateContentBodyRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "github-api", url = "https://api.github.com", configuration = FeignConfig.class)
public interface GithubFeign {

	@PutMapping("/repos/{owner}/{repo}/contents/{path}")
	Response createContent(@RequestHeader("Accept") String acceptHeader,
	                       @RequestHeader("Authorization") String authorizationHeader,
	                       @RequestHeader("X-GitHub-Api-Version") String apiVersion,
	                       @PathVariable("owner") String owner,
	                       @PathVariable("repo") String repo,
	                       @PathVariable("path") String path,
	                       @RequestBody CreateContentBodyRequest request);
}
