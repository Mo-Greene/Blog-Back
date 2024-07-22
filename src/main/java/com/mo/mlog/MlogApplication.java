package com.mo.mlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlogApplication.class, args);
	}

}
