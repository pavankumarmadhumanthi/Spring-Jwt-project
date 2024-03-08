package com.example.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entites.Users;

public interface UsersRepo extends JpaRepository<Users, Integer>{

	public Optional<Users> findByEmail(String email);

}
