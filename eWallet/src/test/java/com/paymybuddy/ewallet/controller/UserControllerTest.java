package com.paymybuddy.ewallet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.UserService;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	private User userResponse = InstanceBuilder.createUser(1, "John", "Smith", "john.smith@mrandmrs.smth", false, "NotAnHashedAndSaltedPwd", 30.0, true);
	private List<User> userResponseList = new ArrayList<>(List.of(userResponse, userResponse, userResponse, userResponse));
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;

	@Test
	public void getUsers_shouldReturnOk() throws Exception {
		when(userService.getUsers())
			.thenReturn(userResponseList);
		
		mockMvc.perform(get("/users")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].email").isNotEmpty())
			.andExpect(jsonPath("$.[*].social").isNotEmpty())
			.andExpect(jsonPath("$.[*].password").isNotEmpty())
			.andExpect(jsonPath("$.[*].amount").isNotEmpty())
			.andExpect(jsonPath("$.[*].active").isNotEmpty());
	}
	
	@Test
	public void getUsers_orderByNameAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "name");
		sortByAndOrderParams.add("order", "asc");
		
		mockMvc.perform(get("/users").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getUsers_orderByNameDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "name");
		sortByAndOrderParams.add("order", "desc");
		
		mockMvc.perform(get("/users").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getUsers_orderByEmailAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "email");
		sortByAndOrderParams.add("order", "asc");
		
		mockMvc.perform(get("/users").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getUsers_orderByEmailDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "email");
		sortByAndOrderParams.add("order", "desc");
		
		mockMvc.perform(get("/users").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getUsers_orderByAmountAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "asc");
		
		mockMvc.perform(get("/users").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getUsers_orderByAmountDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "desc");
		
		mockMvc.perform(get("/users").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
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
			.andExpect(jsonPath("$.firstname").value("John"))
			.andExpect(jsonPath("$.lastname").value("Smith"))
			.andExpect(jsonPath("$.email").value("john.smith@mrandmrs.smth"))
			.andExpect(jsonPath("$.social").value(false))
			.andExpect(jsonPath("$.password").value("NotAnHashedAndSaltedPwd"))
			.andExpect(jsonPath("$.amount").value(30.0))
			.andExpect(jsonPath("$.active").value(true));
	}
	
	@Test
	public void addUser_shouldThrowBadRequest() throws Exception {
		when(userService.addUser(any(User.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/user"))
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
		
		mockMvc.perform(put("/users/{id}/profile", "1"))
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
		
		mockMvc.perform(put("/users/{id}/active", "1"))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void updateAmount_shouldReturnOk() throws Exception {
		when(userService.updateAmount(any(Integer.class), any(Double.class)))
			.thenReturn(any(Integer.class));
		
		mockMvc.perform(put("/users/{id}/amount", "1")
				.content(objectMapper.writeValueAsString(any(String.class)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void updateAmount_shouldThrowBadRequest() throws Exception {
		when(userService.updateAmount(any(Integer.class), any(Double.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(put("/users/{id}/amount", "NotAnId"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteUser_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/users/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
