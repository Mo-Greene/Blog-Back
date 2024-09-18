package com.mo.mlog.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

	/**
	 * 비동기 설정
	 * <p>최대 스레드 수 3</p>
	 */
	@Override
	@Bean(name = "taskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(5);
		executor.setThreadNamePrefix("Async-");
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new CustomAsyncExceptionHandler();
	}

	private static class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

		@Override
		public void handleUncaughtException(Throwable ex, Method method, Object... params) {
			log.error("Async Method '{}' : {}", method.getName(), ex.getMessage());
			log.error("Error : ", ex);
		}
	}
}
