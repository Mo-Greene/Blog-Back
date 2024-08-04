package com.mo.mlog.common.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtParser {

	private final SecretKey key;

	/**
	 * 토큰 유효성 검사 메서드
	 *
	 * @param token 토큰
	 * @return true, false
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.warn("잘못된 Jwt 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.warn("만료된 Jwt 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.warn("지원되지 않는 Jwt 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.warn("Jwt 토큰이 잘못되었습니다.");
		}
		return false;
	}

	/**
	 * Jwt 정보 추출 메서드
	 *
	 * @param token accessToken
	 * @param role  역할
	 */
	public Authentication getAuthentication(String id, String role) {

		Collection<? extends GrantedAuthority> authorities = Arrays.stream(role.split(","))
			.map(SimpleGrantedAuthority::new)
			.toList();

		UserDetails principal = new User(id, "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	/**
	 * Parse Claim
	 *
	 * @param token AccessToken
	 */
	public Claims parseClaims(String token) {
		try {
			return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
