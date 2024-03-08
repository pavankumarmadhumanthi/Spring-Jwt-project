package com.example.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dtos.TaskDto;
import com.example.entites.Task;
import com.example.entites.Users;
import com.example.exceptions.TaskNotFoundException;
import com.example.exceptions.UserNotFoundException;
import com.example.repos.TaskRepo;
import com.example.repos.UsersRepo;
import com.example.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private UsersRepo userrepo;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TaskRepo taskrepo;
	
	@Override
	public TaskDto save(Integer userId, TaskDto taskDto) {
		
		Users user = userrepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("userid %d id not found", userId)));
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUser(user);
		return modelMapper.map(taskrepo.save(task), TaskDto.class);
	}

	@Override
	public TaskDto getTask(Integer userId, Integer taskId) {
		Users user = userrepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("userid %d id not found", userId)));
		Task task = taskrepo.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException(String.format("task id %d is not found", taskId)));
		if (user.getId() != task.getUser().getId()) {
			throw new IllegalAccessError(String.format("Task id %d is not associated with ${userId}", taskId));
		}
		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(Integer userId) {
		Users user = userrepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("userid %d id not found", userId)));
		List<Task> tasks = taskrepo.findByUserId(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("User with given id %d not found", userId)));

		return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public void deleteTask(Integer userId, Integer taskId) {
		Users user = userrepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("userid %d id not found", userId)));
		Task task = taskrepo.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException(String.format("task id %d is not found", taskId)));
		if (user.getId() != task.getUser().getId()) {
			throw new IllegalAccessError(String.format("Task id %d is not associated with ${userId}", taskId));
		}
		taskrepo.deleteById(taskId);
	}

}
