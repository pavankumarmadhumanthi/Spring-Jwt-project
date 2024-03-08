package com.example.service;

import java.util.List;

import com.example.dtos.TaskDto;

public interface TaskService {
	public TaskDto save(Integer userId ,TaskDto taskDto);
	public TaskDto getTask(Integer userId,Integer taskId);
	public List<TaskDto> getAllTasks(Integer userId);
	public void deleteTask(Integer userId,Integer taskId);

}
