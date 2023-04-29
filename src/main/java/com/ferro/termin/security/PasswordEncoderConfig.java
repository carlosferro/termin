package com.ferro.termin.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public PasswordEncoder delegatingPasswordEncoder() {
//	    PasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
//	    Map<String, PasswordEncoder> encoders = new HashMap<>();
//	    encoders.put("bcrypt", defaultEncoder);
//	    DelegatingPasswordEncoder passworEncoder = new DelegatingPasswordEncoder(
//	      "bcrypt", encoders);
//	    passworEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);
//
//	    return passworEncoder;
//	}
}
