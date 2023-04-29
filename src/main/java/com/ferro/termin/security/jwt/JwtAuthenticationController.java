package com.ferro.termin.security.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class JwtAuthenticationController {

	private final JwtTokenService tokenService;

	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationController(JwtTokenService tokenService, AuthenticationManager authenticationManager) {
		this.tokenService = tokenService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JwtTokenResponse> generateToken(@RequestBody JwtTokenRequest jwtTokenRequest) {

		var authenticationToken = new UsernamePasswordAuthenticationToken(
				jwtTokenRequest.email(),
				jwtTokenRequest.password());

		var authentication = authenticationManager.authenticate(authenticationToken);

		var token = tokenService.generateToken(authentication);

		return ResponseEntity.ok(new JwtTokenResponse(token));
	}
}
