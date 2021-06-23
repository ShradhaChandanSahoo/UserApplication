package com.neosoft.firstpoc.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neosoft.firstpoc.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("SELECT u FROM User u WHERE CONCAT(u.firstName, ' ', u.lastName, ' ', u.department) LIKE %?1%")
	public List<User> searchFirstNameOrLastNameOrDepartment(String keyword);
	
	public List<User> findAllByOrderByDobAsc();
	
	public List<User> findAllByOrderByJoiningDateAsc();

}
