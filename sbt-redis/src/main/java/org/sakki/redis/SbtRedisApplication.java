package org.sakki.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SbtRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbtRedisApplication.class, args);
	}

}
