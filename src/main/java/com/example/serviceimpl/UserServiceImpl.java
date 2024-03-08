package com.example.serviceimpl;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dtos.UsersDto;
import com.example.entites.Users;
import com.example.exceptions.EmailException;
import com.example.exceptions.UserNotFoundException;
import com.example.repos.UsersRepo;
import com.example.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private ModelMapper modelMapper;
	 
	@Autowired
	private UsersRepo userrepo;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UsersDto saveUser(UsersDto userDto) {
		userDto.setPassword(encoder.encode(userDto.getPassword()));
		Users user = modelMapper.map(userDto,Users.class);
		Users savedUsers;
		try {
			savedUsers = userrepo.save(user);
		}
		catch(Exception e) {
			throw new UserNotFoundException(String.format("Try with anothr email %s",user.getEmail()));	
		}
	return modelMapper.map(savedUsers,UsersDto.class);
	}

}
