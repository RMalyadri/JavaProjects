package com.maly.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maly.dto.LoginRequest;
import com.maly.dto.User;
import com.maly.jwt.JwtAuthenticationResponse;
import com.maly.jwt.JwtTokenProvider;
import com.maly.service.UserThreadHolder;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthenticationController {
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = tokenProvider.generateToken(authentication);
	    log.info("authenticatio is successfull for:{}",loginRequest.getUserId());
	    User userInfo = UserThreadHolder.userDetails.get();
		log.info("operation perfomed user in authentication:{}",userInfo);
		UserThreadHolder.userDetails.remove();
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	
	
	
}
