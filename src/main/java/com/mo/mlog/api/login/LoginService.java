package com.mo.mlog.api.login;

import com.mo.mlog.common.exception.EntityException;
import com.mo.mlog.common.feign.github.GithubFeign;
import com.mo.mlog.common.feign.github.dto.GithubUserResponse;
import com.mo.mlog.common.feign.oauth2.OAuth2Feign;
import com.mo.mlog.common.feign.oauth2.dto.OAuth2ParamsRequest;
import com.mo.mlog.persistence.admin.Admin;
import com.mo.mlog.persistence.admin.AdminRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mo.mlog.common.constant.REDIRECT.REDIRECT_URL;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final AdminRepository adminRepository;
	private final OAuth2Feign oAuth2Feign;
	private final GithubFeign githubFeign;

	@Value("${github.oauth2.client-id}")
	private String clientId;

	@Value("${github.oauth2.client-secret}")
	private String clientSecret;

	/**
	 * github 로그인 콜백
	 *
	 * @param code 콜백 코드
	 */
	public void githubCallback(String code, HttpServletResponse httpServletResponse) throws IOException {

		OAuth2ParamsRequest params = new OAuth2ParamsRequest(clientId, clientSecret, code);

		GithubUserResponse response = proceedGithubAuthorize(params);

		validateAdmin(response);

		UriComponentsBuilder redirectUrl = UriComponentsBuilder.fromUriString(REDIRECT_URL)
				.queryParam("login", true);
		httpServletResponse.sendRedirect(redirectUrl.toUriString());
	}

	/**
	 * Github authorize
	 *
	 * @param params response code
	 */
	private GithubUserResponse proceedGithubAuthorize(OAuth2ParamsRequest params) {

		String responseAccessToken = oAuth2Feign.getAccessToken(params);
		String authorizationHeader = createAuthorizationHeader(responseAccessToken);
		return githubFeign.getUser(authorizationHeader);
	}

	/**
	 * Create Github Authorization Header
	 *
	 * @param responseAccessToken 응답 토큰
	 */
	private String createAuthorizationHeader(String responseAccessToken) {

		Map<String, String> tokenMap = Arrays.stream(responseAccessToken.split("&"))
			.map(token -> token.split("="))
			.collect(Collectors.toMap(kv -> kv[0], kv -> kv.length > 1 ? kv[1] : ""));

		String accessToken = tokenMap.get("access_token");
		String tokenType = tokenMap.get("token_type");

		return tokenType + " " + accessToken;
	}

	/**
	 * Github user and Database admin validation
	 *
	 * @param response Github authorization response
	 */
	private void validateAdmin(GithubUserResponse response) {
		Admin admin = adminRepository.findById(response.getId()).orElseThrow(EntityException::new);
		if (!Objects.equals(admin.getLogin(), response.getLogin())
			&& !Objects.equals(admin.getNodeId(), response.getNode_id())) {
			throw new IllegalArgumentException("Github User Validate Error");
		}
	}
}
