package com.mo.mlog.common.config;

import com.mo.mlog.common.feign.github.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.mo.mlog.common.feign")
public class FeignConfig {

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
}
