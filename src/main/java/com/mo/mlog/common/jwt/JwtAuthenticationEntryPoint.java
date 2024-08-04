package com.mo.mlog.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mo.mlog.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.mo.mlog.common.response.CommonResponse.Result.FAIL;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper = new ObjectMapper();

	/**
	 * 자격 증명 실패 401
	 *
	 * @param request       that resulted in an <code>AuthenticationException</code>
	 * @param response      so that the user agent can begin authentication
	 * @param authException that caused the invocation
	 * @throws IOException IOException
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException {

		CommonResponse<?> result = new CommonResponse<>(FAIL, "유효하지 않은 토큰입니다.");
		String jsonResult = mapper.writeValueAsString(result);

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResult);
	}
}
