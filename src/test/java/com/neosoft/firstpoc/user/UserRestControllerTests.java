package com.neosoft.firstpoc.user;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.firstpoc.model.User;
import com.neosoft.firstpoc.repository.UserRepository;
import com.neosoft.firstpoc.rest.UserRestController;
import com.neosoft.firstpoc.service.IUserService;

@WebMvcTest(UserRestController.class)
public class UserRestControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private IUserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	
	public static List<User> setUp() throws ParseException {
		User user = new User();
		
		user.setId(1);
		user.setFirstName("Harekrus");
		user.setMiddleName("Kumarrrr");
		user.setLastName("Sahooooo");
		user.setEmail("Harekrus@gmail.com");
		user.setPhoneNumber("9937010695");
		user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-15"));
		user.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1987-06-16 23:37:50"));
		user.setDepartment("ITDeapt");
		user.setTechnology("JavaTech");
		user.setSalary(16000.00);
		user.setAddress("Charampa,Bhadrak");
		user.setFlag(true);
		
		User user1 = new User();
		user1.setId(2);
		user1.setFirstName("Rameshhh");
		user1.setMiddleName("Kumarrrr");
		user1.setLastName("Sahooooo");
		user1.setEmail("Harekrus@gmail.com");
		user1.setPhoneNumber("9937010695");
		user1.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-15"));
		user1.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1987-06-16 23:37:50"));
		user1.setDepartment("ITDeapt");
		user1.setTechnology("JavaTech");
		user1.setSalary(16000.00);
		user1.setAddress("Charampa,Bhadrak");
		user1.setFlag(true);
		
		User user2 = new User();
		user2.setId(3);
		user2.setFirstName("Rameshhh");
		user2.setMiddleName("Kumarrrr");
		user2.setLastName("Sahooooo");
		user2.setEmail("Harekrus@gmail.com");
		user2.setPhoneNumber("9937010695");
		user2.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-15"));
		user2.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1987-06-16 23:37:50"));
		user2.setDepartment("ITDeapt");
		user2.setTechnology("JavaTech");
		user2.setSalary(16000.00);
		user2.setAddress("Charampa,Bhadrak");
		user2.setFlag(true);
		
		LinkedList<User> list = new LinkedList<>();
		list.add(user);
		list.add(user1);
		list.add(user2);
		
		return list;
	}
	
	@Test
	public void testCreateNewUser() throws Exception {
		
		Mockito.when(userService.saveUser(UserRestControllerTests.setUp().get(0))).thenReturn(1);
		
		String payLoad = objectMapper.writeValueAsString(UserRestControllerTests.setUp().get(0));
		
		MvcResult result = mockMvc.perform(post("/api/user/save")
				         .content(payLoad)
				         .contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isCreated()).andReturn();
		
		int status = result.getResponse().getStatus();
		
		assertEquals(201, status);
	}
	
	@Test
	public void testGetOneUserDetails() throws Exception{
		
		String response = objectMapper.writeValueAsString(UserRestControllerTests.setUp().get(0));
		
		Mockito.when(userService.getOneUser(1)).thenReturn(UserRestControllerTests.setUp().get(0));
		
		MvcResult result = mockMvc.perform(get("/api/user/one/1").contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk()).andReturn();
		
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
		
	}
	
	@Test
	public void testUpdateUserDetails() throws Exception{
		
		Mockito.when(userService.updateUser(UserRestControllerTests.setUp().get(2))).thenReturn(3);
		
		String payload = objectMapper.writeValueAsString(UserRestControllerTests.setUp().get(2));
		
		MvcResult result = mockMvc.perform(put("/api/user/modify/3").content(payload).contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isResetContent()).andReturn();
		
		int response = result.getResponse().getStatus();
		assertEquals(205, response);
	}
	
	@Test
	public void testGetAllUserDetails() throws Exception{
		
		Page<User> listOfUser = (Page<User>) UserRestControllerTests.setUp();
		String response = objectMapper.writeValueAsString(listOfUser);
		Mockito.when(userService.getAllUser(null)).thenReturn(listOfUser);
		MvcResult result = mockMvc.perform(get("/api/user/all").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		String userActual = result.getResponse().getContentAsString();
		assertEquals(response, userActual);
	}
	
	@Test
	public void testDeleteEmployeeDetails() throws Exception{
		
		MvcResult result = mockMvc.perform(delete("/api/user/remove/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
		
		int response = result.getResponse().getStatus();
		
		assertEquals(200, response);
		assertEquals("User '"+1+"' deleted", result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testSearchUserByFirstNameOrLastNameOrDepartment() throws Exception{
		
		User user = new User();
		
		user.setId(1);
		user.setFirstName("Harekrus");
		user.setMiddleName("Kumarrrr");
		user.setLastName("Sahooooo");
		user.setEmail("Harekrus@gmail.com");
		user.setPhoneNumber("9937010695");
		user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-15"));
		user.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1987-06-16 23:37:50"));
		user.setDepartment("ITDeapt");
		user.setTechnology("JavaTech");
		user.setSalary(16000.00);
		user.setAddress("Charampa,Bhadrak");
		user.setFlag(true);
		
		LinkedList<User> list = new LinkedList<>();
		list.add(user);
		
		String respone = objectMapper.writeValueAsString(list);
		
		Mockito.when(userService.searchByUserFirstNameOrLastNameOrDepartment("Harekrus")).thenReturn(list);
		
		MvcResult result = mockMvc.perform(get("/api/user/search/Harekrus").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
		
		assertEquals(respone, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
		
	}
	
	@Test
	public void testSortByDob() throws Exception{
		
		User user = new User();
		
		user.setId(2);
		user.setFirstName("Harekrus");
		user.setMiddleName("Kumarrrr");
		user.setLastName("Sahooooo");
		user.setEmail("Harekrus@gmail.com");
		user.setPhoneNumber("9937010695");
		user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-15"));
		user.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1987-06-16 23:37:50"));
		user.setDepartment("ITDeapt");
		user.setTechnology("JavaTech");
		user.setSalary(16000.00);
		user.setAddress("Charampa,Bhadrak");
		user.setFlag(true);
		
		User user1 = new User();
		user1.setId(1);
		user1.setFirstName("Rameshhh");
		user1.setMiddleName("Kumarrrr");
		user1.setLastName("Sahooooo");
		user1.setEmail("Harekrus@gmail.com");
		user1.setPhoneNumber("9937010695");
		user1.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-15"));
		user1.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1987-06-16 23:37:50"));
		user1.setDepartment("ITDeapt");
		user1.setTechnology("JavaTech");
		user1.setSalary(16000.00);
		user1.setAddress("Charampa,Bhadrak");
		user1.setFlag(true);
		
		LinkedList<User> list = new LinkedList<>();
		list.add(user);
		list.add(user1);
		
		String response = objectMapper.writeValueAsString(list);
		
		Mockito.when(userService.sortByDob()).thenReturn(list);
		
		MvcResult result = mockMvc.perform(get("/api/user/sort").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
		
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
		
	}
	
	@Test
	public void testSortByDoj() throws Exception{
		
		User user = new User();
		
		user.setId(2);
		user.setFirstName("Harekrus");
		user.setMiddleName("Kumarrrr");
		user.setLastName("Sahooooo");
		user.setEmail("Harekrus@gmail.com");
		user.setPhoneNumber("9937010695");
		user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-15"));
		user.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1987-06-16 23:37:50"));
		user.setDepartment("ITDeapt");
		user.setTechnology("JavaTech");
		user.setSalary(16000.00);
		user.setAddress("Charampa,Bhadrak");
		user.setFlag(true);
		
		User user1 = new User();
		user1.setId(1);
		user1.setFirstName("Rameshhh");
		user1.setMiddleName("Kumarrrr");
		user1.setLastName("Sahooooo");
		user1.setEmail("Harekrus@gmail.com");
		user1.setPhoneNumber("9937010695");
		user1.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1987-06-15"));
		user1.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1987-06-16 23:37:50"));
		user1.setDepartment("ITDeapt");
		user1.setTechnology("JavaTech");
		user1.setSalary(16000.00);
		user1.setAddress("Charampa,Bhadrak");
		user1.setFlag(true);
		
		LinkedList<User> list = new LinkedList<>();
		list.add(user);
		list.add(user1);
		
		String response = objectMapper.writeValueAsString(list);
		
		Mockito.when(userService.sortByDoj()).thenReturn(list);
		
		MvcResult result = mockMvc.perform(get("/api/user/sortDoj").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
		
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
		
	}
}
