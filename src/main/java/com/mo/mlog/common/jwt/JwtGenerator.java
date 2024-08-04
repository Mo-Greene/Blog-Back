package com.mo.mlog.common.jwt;

import com.mo.mlog.common.jwt.token.JwtToken;
import com.mo.mlog.common.jwt.token.JwtTokenRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import static com.mo.mlog.common.jwt.constant.JWT.CLAIM_KEY;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

	private final SecretKey key;
	private final JwtTokenRepository jwtTokenRepository;

	/**
	 * create Redis AccessToken
	 *
	 * @param userId github user PK
	 */
	public String tokenRedis(Long userId) {

		UUID redisId = UUID.randomUUID();

		String accessToken = generateToken(redisId);

		JwtToken jwtToken = JwtToken.builder()
			.id(String.valueOf(redisId))
			.userId(userId)
			.role(UserRole.ADMIN.toString())
			.build();
		jwtTokenRepository.save(jwtToken);

		return accessToken;
	}

	/**
	 * 토큰 생성
	 */
	public String generateToken(UUID redisId) {

		Date now = new Date();
		long currentMillis = now.getTime();
		Date tokenExpire = new Date(currentMillis + Duration.ofDays(1).toMillis());

		return Jwts.builder()
			.claim(CLAIM_KEY, redisId)
			.issuedAt(now)
			.expiration(tokenExpire)
			.signWith(key, Jwts.SIG.HS512)
			.compact();
	}
}
