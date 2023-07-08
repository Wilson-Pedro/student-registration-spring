package com.wamk.studantregistration.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.wamk.studantregistration.models.user.User;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("studant-registration")
					.withSubject(user.getLogin())
					.withExpiresAt(genExperationDate())
					.sign(algorithm);
			return token;
		} catch(JWTCreationException e) {
			throw new RuntimeException("Error while generating token", e);
		}
	}
	
	public String valideToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("studant-registration")
					.build()
					.verify(token)
					.getSubject();
			
		} catch(JWTCreationException e) {
			return "";
		}
	}
	
	private Instant genExperationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
