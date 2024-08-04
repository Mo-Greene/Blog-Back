package com.mo.mlog.api.login;

import com.mo.mlog.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@GetMapping("/login/oauth2/code/github")
	public CommonResponse<?> afterLoginGithubCallback(@RequestParam String code, HttpServletResponse response) throws IOException {

		loginService.githubCallback(code, response);
		return CommonResponse.ok();
	}
}
