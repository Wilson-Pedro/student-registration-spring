package com.wamk.studantregistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wamk.studantregistration.infra.security.TokenService;
import com.wamk.studantregistration.models.user.AuthenticationDTO;
import com.wamk.studantregistration.models.user.LoginResponseDTO;
import com.wamk.studantregistration.models.user.RegisterDTO;
import com.wamk.studantregistration.models.user.User;
import com.wamk.studantregistration.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO data) {
		if(userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, data.role());
		
		userRepository.save(newUser);
		
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
}
