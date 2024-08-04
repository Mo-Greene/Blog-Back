package com.mo.mlog.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mo.mlog.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.mo.mlog.common.response.CommonResponse.Result.FAIL;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper mapper = new ObjectMapper();

	/**
	 * 권한없이 접근 시 403
	 *
	 * @param request               that resulted in an <code>AccessDeniedException</code>
	 * @param response              so that the user agent can be advised of the failure
	 * @param accessDeniedException that caused the invocation
	 * @throws IOException      IOException
	 */
	@Override
	public void handle(
		HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
		throws IOException {

		response.sendError(HttpServletResponse.SC_FORBIDDEN);

		CommonResponse<?> result = new CommonResponse<>(FAIL, "권한이 없습니다.");
		String jsonResult = mapper.writeValueAsString(result);

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResult);
	}
}
