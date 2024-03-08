package com.example.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entites.Task;

public interface TaskRepo extends JpaRepository<Task, Integer>{
	Optional<List<Task>> findByUserId(Integer userId);

}
