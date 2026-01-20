package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableWebSecurity
public class SecuObservabilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuObservabilityApplication.class, args);
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
			.sessionManagement((manager) -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests((request) -> request
				.anyRequest()
				.authenticated())
			//.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
			.httpBasic(Customizer.withDefaults())
			.build();
	}
	
	@RestController
	@RequestMapping("/demo")
	public static class Controller {
		
		Logger log = LoggerFactory.getLogger(getClass());
		
		@GetMapping(path = "{id}")
		public ResponseEntity<String> demo(){
			log.info("ok");
			return ResponseEntity.ok("hello world");
		}
		
	}

}
