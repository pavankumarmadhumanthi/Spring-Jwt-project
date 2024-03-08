package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.LoginDto;
import com.example.dtos.UsersDto;
import com.example.security.JwtTokenProvider;
import com.example.serviceimpl.UserServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServiceImpl userserviceimpl;
	
	@Autowired
	private JwtTokenProvider tokenprovider;
	
	@PostMapping("/register")
	public ResponseEntity<UsersDto> addUser(@RequestBody UsersDto userDto) {
		UsersDto user = userserviceimpl.saveUser(userDto);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> validateUser(@RequestBody LoginDto loginDto){
	Authentication authenticate = 
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
	 authenticate.isAuthenticated();
	 SecurityContextHolder.getContext().setAuthentication(authenticate);
	 String token = tokenprovider.generateToken(authenticate);
	 return new ResponseEntity<>(token,HttpStatus.OK);
	}
	

}
