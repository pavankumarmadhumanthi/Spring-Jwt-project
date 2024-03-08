package com.example.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.entites.Users;
import com.example.repos.UsersRepo;
@Component
public class UsersDetailsProvider implements UserDetailsService {
	@Autowired
	private UsersRepo userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userrepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username));
		Set<String> roles=new HashSet<>();
		roles.add("ROLE_ADMIN");
		return new User(user.getEmail(),user.getPassword(),usersAuthorities(roles));
	}

	private Collection<? extends GrantedAuthority> usersAuthorities(Set<String> roles) {
		return roles.stream().map(e -> new SimpleGrantedAuthority(e)).collect(Collectors.toSet());
	}
}

 
