package com.example.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenProvider tokenprovider;
	@Autowired
	private UsersDetailsProvider usersdetailsprovider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String str = request.getHeader("Authorization");
		String token = null;
		if (str != null && str.startsWith("Bearer ")) {
			token = str.substring(7);
		}
		if (token != null && tokenprovider.validateToken(token)) {
			UserDetails users = usersdetailsprovider.loadUserByUsername(tokenprovider.getEmailFromToken(token));
			UsernamePasswordAuthenticationToken authtoken 
									=new UsernamePasswordAuthenticationToken(users.getUsername(),null,users.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authtoken);
			
		}
		filterChain.doFilter(request, response);

	}

}
