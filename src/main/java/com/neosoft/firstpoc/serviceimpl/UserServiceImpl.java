package com.neosoft.firstpoc.serviceimpl;



import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neosoft.firstpoc.exception.UserNotFoundException;
import com.neosoft.firstpoc.model.User;
import com.neosoft.firstpoc.repository.UserRepository;
import com.neosoft.firstpoc.service.IUserService;



@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository  userRepository;

	@Override
	public Integer saveUser(User user) {
		
		log.info("Inside saveUser Method In UserServiceImpl");
		User savedUser = userRepository.save(user);
		log.info("User Object Is Saved");
		return savedUser.getId();
	}

	@Override
	public User getOneUser(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User '"+id+"' Not exist"));
		return user;
	}

	@Override
	public Integer updateUser(User oneUser) {
		return userRepository.save(oneUser).getId();
	}

	@Override
	public void deleteUser(Integer id) {
		
		User oneUser = getOneUser(id);
		userRepository.delete(oneUser);
		
	}

	@Override
	public Page<User> getAllUser(Pageable page) {
		
		return userRepository.findAll(page);
	}

	@Override
	public void deleteUserById(Integer id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public List<User> searchByUserFirstNameOrLastNameOrDepartment(String keyword) {
		
		if(keyword!=null) {
			return userRepository.searchFirstNameOrLastNameOrDepartment(keyword);
		}
		return userRepository.findAll();
	}

	
	@Override
	public List<User> sortByDob() {
		
		return userRepository.findAllByOrderByDobAsc();
	}
	
	@Override
	public List<User> sortByDoj() {
		
		return userRepository.findAllByOrderByJoiningDateAsc();
	}
}
