package com.example.demo;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableWebFluxSecurity
public class SecuObservabilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuObservabilityApplication.class, args);
	}
	
	
	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {

		http.cors(withDefaults());
		http.csrf(ServerHttpSecurity.CsrfSpec::disable);
		http.httpBasic(withDefaults());
		return http.build();
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
