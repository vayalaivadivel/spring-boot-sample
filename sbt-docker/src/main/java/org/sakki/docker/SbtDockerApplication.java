package org.sakki.docker;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@RestController
public class SbtDockerApplication {
	private static final Logger LOG = LoggerFactory.getLogger(SbtDockerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SbtDockerApplication.class, args);
	}
	
	/*
	 * @Value("${sbt.message}") private String message;
	 */
	@GetMapping("/")
	public String welcome(HttpServletRequest request) {
		LOG.info("Success request");
		return "Success";
	}

}
