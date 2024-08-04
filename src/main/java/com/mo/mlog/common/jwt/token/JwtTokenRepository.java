package com.mo.mlog.common.jwt.token;

import org.springframework.data.repository.CrudRepository;

public interface JwtTokenRepository extends CrudRepository<JwtToken, String> {
}
