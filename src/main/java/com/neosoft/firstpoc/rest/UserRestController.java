package com.neosoft.firstpoc.rest;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.firstpoc.exception.UserNotFoundException;
import com.neosoft.firstpoc.model.User;
import com.neosoft.firstpoc.service.IUserService;
import com.neosoft.util.UserUtil;



@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user){
		
		ResponseEntity<?> resp = null;
		
		try {
			log.info("Inside saveUser Method In saveUser For Save An User Entity");
			Integer userId = userService.saveUser(user);
			String message = "User "+userId+" Is Saved Succefully";
			log.info(message);
			resp = new ResponseEntity<String>(message,HttpStatus.CREATED);
			log.info("ResponseEntity Is Returned As a String Message");
			
		} catch (Exception e) {
			log.error("Due To Internal Server Error. User Entity Is Not Saved Succefully");
			resp = new ResponseEntity<String>("Unable To Save Part Entity",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resp;
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOneUser(@PathVariable Integer id) {
		
		ResponseEntity<?> resp = null;
		try {
			User p = userService.getOneUser(id);
			resp = new ResponseEntity<User>(p,HttpStatus.OK);
			
		} catch(UserNotFoundException unfe) {
			throw unfe;
		} catch (Exception e) {
			resp = new ResponseEntity<String>(
					"UNABLE TO FETCH USER",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resp;
	}
	
	@PutMapping("/modify/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Integer id,@RequestBody User user) {
		
		ResponseEntity<String> resp = null;
		try {
			
			//db object
			User oneUser = userService.getOneUser(id);
			//copy non-null values from request to Database object
			UserUtil.copyNonNullValues(user, oneUser);
			//finally update this object
			userService.updateUser(oneUser);
			
			resp = new ResponseEntity<String>("USER '"+id+"' Updated",HttpStatus.RESET_CONTENT); //205- Reset-Content(PUT)
			
		} catch(UserNotFoundException une) {
			throw une; // re-throw exception to handler
		} catch (Exception e) {
			
			resp = new ResponseEntity<String>("Unable to Update USER",HttpStatus.INTERNAL_SERVER_ERROR); //500-ISE
			e.printStackTrace();
		}
		
		return resp;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers(@PageableDefault(page = 0,size = 10) Pageable p) 
	{
		log.info("ENTERED INTO GET ALL METHOD");
		ResponseEntity<?> resp = null;
		try {
			
			Page<User> page = userService.getAllUser(p);
			log.info("SERVICE METHOD CALLED FOR ALL DATA FETCH");
			resp = new ResponseEntity<Page<User>>(page,HttpStatus.OK);
			log.info("SUCCESS RESPONSE CREATED");
		} catch (Exception e) {
			log.error("PROBLEM IN FETCHING DATA {}",e.getMessage());
			resp = new ResponseEntity<String>("Unable to Fetch Users",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		log.info("ABOUT TO RETURN FROM GET ALL METHOD");
		return resp;
	}
	
	
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
		
		ResponseEntity<String> resp = null;
		
		try {
			userService.deleteUserById(id);
			resp = new ResponseEntity<String>("User '"+id+"' deleted", HttpStatus.OK);
		} catch(UserNotFoundException une) {
			throw une; // re-throw exception to handler
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to Delete Product", HttpStatus.INTERNAL_SERVER_ERROR); //500-ISE
		}
		
		return resp;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> removeUser(@PathVariable Integer id) {
		
		ResponseEntity<String> resp = null;
		
		try {
			userService.deleteUser(id);
			resp = new ResponseEntity<String>("User '"+id+"' deleted", HttpStatus.OK);
		} catch(UserNotFoundException une) {
			throw une; // re-throw exception to handler
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to Delete User", HttpStatus.INTERNAL_SERVER_ERROR); //500-ISE
		}
		
		return resp;
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> getDataByFirstNameOrLastNameOrDepartment(@PathVariable String keyword) {
		log.info("ENTERED INTO Search User METHOD");
		ResponseEntity<?> resp = null;
		try {
			
			List<User> searchData = userService.searchByUserFirstNameOrLastNameOrDepartment(keyword);
			log.info("SERVICE METHOD CALLED FOR SEARCH DATA FETCH");
			resp = new ResponseEntity<List<User>>(searchData,HttpStatus.OK);
			log.info("SUCCESSFULLY SEARCH DATA SENDED");
		} catch (Exception e) {
			log.error("PROBLEM IN SEACHING DATA {}",e.getMessage());
			resp = new ResponseEntity<String>("Unable to Search",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		log.info("ABOUT TO RETURN FROM SEARCH METHOD");
		return resp;
	}
	
	
	@GetMapping("/sort")
	public ResponseEntity<?> sortDataByDob() {
		log.info("ENTERED INTO SORT User METHOD");
		ResponseEntity<?> resp = null;
		try {
			
			List<User> sortData = userService.sortByDob();
			log.info("SORT METHOD CALLED FOR SORT DATA FETCH");
			resp = new ResponseEntity<List<User>>(sortData,HttpStatus.OK);
			log.info("SUCCESSFULLY SORT DATA SENDED");
		} catch (Exception e) {
			log.error("PROBLEM IN SORTING DATA {}",e.getMessage());
			resp = new ResponseEntity<String>("Unable to Sort",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		log.info("ABOUT TO RETURN FROM SORT METHOD");
		return resp;
	}
	
	@GetMapping("/sortDoj")
	public ResponseEntity<?> sortDataByJoiningDate() {
		log.info("ENTERED INTO SORT User METHOD");
		ResponseEntity<?> resp = null;
		try {
			
			List<User> sortData = userService.sortByDoj();
			log.info("SORT METHOD CALLED FOR SORT DATA FETCH");
			resp = new ResponseEntity<List<User>>(sortData,HttpStatus.OK);
			log.info("SUCCESSFULLY SORT DATA SENDED");
		} catch (Exception e) {
			log.error("PROBLEM IN SORTING DATA {}",e.getMessage());
			resp = new ResponseEntity<String>("Unable to Sort",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		log.info("ABOUT TO RETURN FROM SORT METHOD");
		return resp;
	}
	
	


}
