package com.neosoft.firstpoc.service;


import java.util.Date;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.neosoft.firstpoc.model.User;



public interface IUserService {
	
	public Integer saveUser(User user);

	public User getOneUser(Integer id);

	public Integer updateUser(User oneUser);

	public void deleteUser(Integer id);
	
	public void deleteUserById(Integer id);
	
	public Page<User> getAllUser(Pageable page);
	
	public List<User> searchByUserFirstNameOrLastNameOrDepartment(String keyword);
	
	public List<User> sortByDob();
	
	public List<User> sortByDoj();

}
