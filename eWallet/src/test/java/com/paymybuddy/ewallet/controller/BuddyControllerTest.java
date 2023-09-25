package com.paymybuddy.ewallet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.BuddyService;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@WebMvcTest(controllers = BuddyController.class)
public class BuddyControllerTest {

	private User userResponse = InstanceBuilder.createUser(1, "John", "Smith", "john.smith@mrandmrs.smth", "NotAnHashedAndSaltedPwd", 30.0, true);
	private List<User> userResponseList = new ArrayList<>(List.of(userResponse, userResponse, userResponse));

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	BuddyService buddyService;

	@Test
	public void getBuddiesByUser_shouldReturnOk() throws Exception {
		when(buddyService.getBuddiesByUser(any(Integer.class)))
			.thenReturn(userResponseList);
		
		mockMvc.perform(get("/mybuddies/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(1))
			.andExpect(jsonPath("$.[0].firstname").value("John"))
			.andExpect(jsonPath("$.[0].lastname").value("Smith"))
			.andExpect(jsonPath("$.[0].email").value("john.smith@mrandmrs.smth"))
			.andExpect(jsonPath("$.[0].active").value(true));
	}

	@Test
	public void getActiveBuddiesByUser_shouldReturnOk() throws Exception {
		when(buddyService.getActiveBuddiesByUser(any(Integer.class)))
			.thenReturn(userResponseList);
		
		mockMvc.perform(get("/mybuddies/active/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(1))
			.andExpect(jsonPath("$.[0].firstname").value("John"))
			.andExpect(jsonPath("$.[0].lastname").value("Smith"))
			.andExpect(jsonPath("$.[0].email").value("john.smith@mrandmrs.smth"))
			.andExpect(jsonPath("$.[0].active").value(true));
	}

	@Test
	public void addBuddy_shouldReturnCreated() throws Exception {
		when(buddyService.addBuddy(any(BuddyAddDto.class)))
			.thenReturn(userResponse);
		
		mockMvc.perform(post("/buddy")
				.content(objectMapper.writeValueAsString(userResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.firstname").value("John"))
			.andExpect(jsonPath("$.lastname").value("Smith"))
			.andExpect(jsonPath("$.email").value("john.smith@mrandmrs.smth"))
			.andExpect(jsonPath("$.active").value(true));
	}

	@Test
	public void addBuddy_shouldThrowBadRequest() throws Exception {
		when(buddyService.addBuddy(any(BuddyAddDto.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/buddy")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteBuddy_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/buddy/{firstUserId}-{secondUserId}", "1", "2").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}