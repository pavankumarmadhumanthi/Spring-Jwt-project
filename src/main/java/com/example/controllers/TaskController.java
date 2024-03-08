package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.TaskDto;
import com.example.serviceimpl.TaskServiceImpl;

@RestController
@RequestMapping("/api")
public class TaskController {
	@Autowired
	private TaskServiceImpl taskserviceimpl;
	@PostMapping("/{userId}/task")
	public ResponseEntity<TaskDto> addTask(@PathVariable("userId") Integer userId ,@RequestBody TaskDto taskDto){
		TaskDto task = taskserviceimpl.save(userId, taskDto);
		return new ResponseEntity<>(task,HttpStatus.OK);
	}
	@GetMapping("/{userId}/task/{taskId}")
	public ResponseEntity<TaskDto> getTask(@PathVariable("userId")Integer userId,@PathVariable("taskId")Integer taskId){
		TaskDto task = taskserviceimpl.getTask(userId, taskId);
		return new ResponseEntity<>(task,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/tasks")
	public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable("userId")Integer userId){
		List<TaskDto> allTasks = taskserviceimpl.getAllTasks(userId);
		return new ResponseEntity<>(allTasks,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}/task/{taskId}")
	public ResponseEntity<String> deleteTask(@PathVariable("userId")Integer userId,@PathVariable("taskId")Integer taskId){
		taskserviceimpl.deleteTask(userId, taskId);
		return new ResponseEntity<>("Task Deleted successfully ",HttpStatus.OK);
	}

}
