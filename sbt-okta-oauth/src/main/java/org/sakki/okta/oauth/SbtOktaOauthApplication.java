package org.sakki.okta.oauth;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class SbtOktaOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbtOktaOauthApplication.class, args);
	}
	
	@GetMapping("/")
	public String welcome(Principal principal) {
		return "Hi "+principal.getName()+" welcome to oauth-security app";
	}

}
