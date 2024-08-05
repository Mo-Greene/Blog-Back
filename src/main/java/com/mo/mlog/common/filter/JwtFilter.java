package com.mo.mlog.common.filter;

import com.mo.mlog.common.jwt.JwtParser;
import com.mo.mlog.common.jwt.UserRole;
import com.mo.mlog.common.jwt.token.JwtToken;
import com.mo.mlog.common.jwt.token.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.mo.mlog.common.jwt.constant.JWT.CLAIM_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String USER_ROLE = "ROLE_" + UserRole.ADMIN;
	private final JwtParser jwtParser;
	private final JwtTokenRepository jwtTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		resolveToken(request).ifPresent(
			token -> {
				if (jwtParser.validateToken(token)) {
					Claims claims = jwtParser.parseClaims(token);
					String id = (String) claims.get(CLAIM_KEY);
					JwtToken jwtToken = jwtTokenRepository.findById(id).orElse(null);
					if (jwtToken != null) {
						Long userId = jwtToken.getUserId();
						Authentication authentication = jwtParser.getAuthentication(String.valueOf(userId), USER_ROLE);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		);

		filterChain.doFilter(request, response);
	}

	/**
	 * Token Resolving
	 *
	 * @param request HttpServletRequest
	 */
	private Optional<String> resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return Optional.of(bearerToken.substring(7));
		}

		return Optional.empty();
	}
}
