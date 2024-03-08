package com.example.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.exceptions.UserNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	static byte[] keyBytes = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded();

	public String generateToken(Authentication authentication) {
		String email = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + 36000000);
		String token = Jwts.builder().setSubject(email).setIssuedAt(currentDate).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS256, keyBytes).compact();

		return token;

	}

	public String getEmailFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(keyBytes).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			System.out.println(token);
			Claims claims = Jwts.parser().setSigningKey(keyBytes).parseClaimsJws(token).getBody();
			if (claims.getExpiration().before(new Date())) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new UserNotFoundException(e.getMessage());
		}
	}

}
