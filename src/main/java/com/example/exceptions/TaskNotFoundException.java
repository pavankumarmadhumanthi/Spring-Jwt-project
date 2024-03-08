package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TaskNotFoundException extends RuntimeException{
	public TaskNotFoundException(String message) {
		super(message);
	}

}
