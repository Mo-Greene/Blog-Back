package com.mo.mlog.common.jwt.token;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value ="refresh", timeToLive = 10)
@Builder
public class JwtToken {

	@Id
	private String id;

	private Long userId;

	private String role;
}
