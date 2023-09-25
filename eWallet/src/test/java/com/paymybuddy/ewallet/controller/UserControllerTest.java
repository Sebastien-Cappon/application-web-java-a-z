package com.paymybuddy.ewallet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.UserService;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	private User userResponse = InstanceBuilder.createUser(1, "John", "Smith", "john.smith@mrandmrs.smth", "NotAnHashedAndSaltedPwd", 30.0, true);
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;

	@Test
	public void getUserById_forLoginPage_shouldReturnOk() throws Exception {
		when(userService.getUserById(any(Integer.class)))
			.thenReturn(userResponse);
		
		mockMvc.perform(get("/users/login/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.firstname").value("John"))
			.andExpect(jsonPath("$.lastname").value("Smith"));
	}
	
	@Test
	public void getUserById_forProfilePage_shouldReturnOk() throws Exception {
		when(userService.getUserById(any(Integer.class)))
			.thenReturn(userResponse);
		
		mockMvc.perform(get("/users/profile/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstname").value("John"))
			.andExpect(jsonPath("$.lastname").value("Smith"))
			.andExpect(jsonPath("$.email").value("john.smith@mrandmrs.smth"));
	}
	@Test
	public void getUserById_forHomePage_shouldReturnOk() throws Exception {
		when(userService.getUserById(any(Integer.class)))
			.thenReturn(userResponse);
		
		mockMvc.perform(get("/users/amount/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value("1"))
			.andExpect(jsonPath("$.amount").value(30.0));
	}
	
	@Test
	public void postUserByEmailAndPassword_shouldReturnOk() throws Exception {
		when(userService.postUserByEmailAndPassword(any(UserLoginDto.class)))
			.thenReturn(userResponse);
		
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(userResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.firstname").value("John"))
			.andExpect(jsonPath("$.lastname").value("Smith"));
	}
	
	@Test
	public void postUserByEmailAndPassword_shouldThrowBadRequest() throws Exception {
		when(userService.postUserByEmailAndPassword(any(UserLoginDto.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/login"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void addUser_shouldReturnCreated() throws Exception {
		when(userService.addUser(any(User.class)))
			.thenReturn(userResponse);
		
		mockMvc.perform(post("/user")
				.content(objectMapper.writeValueAsString(userResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.amount").value(30.0));
	}
	
	@Test
	public void addUser_shouldThrowBadRequest() throws Exception {
		when(userService.addUser(any(User.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/user")
			.content(objectMapper.writeValueAsString(null))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateProfile_shouldReturnOk() throws Exception {
		when(userService.updateProfile(any(Integer.class), any(UserProfileDto.class)))
			.thenReturn(1);
		
		mockMvc.perform(put("/users/{id}/profile", "1")
				.content(objectMapper.writeValueAsString(userResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void updateProfile_shouldThrowBadRequest() throws Exception {
		when(userService.updateProfile(any(Integer.class), any(UserProfileDto.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(put("/users/{id}/profile", "1")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateActive_shouldReturnOk() throws Exception {
		when(userService.updateActive(any(Integer.class), any(Boolean.class)))
			.thenReturn(any(Integer.class));
		
		mockMvc.perform(put("/users/{id}/active", "1")
				.content(objectMapper.writeValueAsString(any(Boolean.class)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void updateActive_shouldThrowBadRequest() throws Exception {
		when(userService.updateActive(any(Integer.class), any(Boolean.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(put("/users/{id}/active", "1")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
}