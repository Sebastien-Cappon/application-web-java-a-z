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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.BuddyKey;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.BuddyService;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@WebMvcTest(controllers = BuddyController.class)
public class BuddyControllerTests {
	
	private User userResponse = InstanceBuilder.createUser(1, "John", "Smith", "john.smith@mrandmrs.smth", false, "NotAnHashedAndSaltedPwd", 30.0, true);
	private BuddyKey buddyKeyResponse = InstanceBuilder.createBuddyKey(userResponse, userResponse);
	private Buddy buddyResponse = InstanceBuilder.createBuddy(buddyKeyResponse);
	private List<Buddy> buddyResponseList = new ArrayList<>(List.of(buddyResponse, buddyResponse, buddyResponse));
	
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	BuddyService buddyService;
	
	@Test
	public void getBuddies_shouldReturnOk() throws Exception {
		when(buddyService.getBuddies())
			.thenReturn(buddyResponseList);
		
		mockMvc.perform(get("/buddies")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id.firstUser.id").value(1))
			.andExpect(jsonPath("$.[0].id.firstUser.firstname").value("John"))
			.andExpect(jsonPath("$.[0].id.firstUser.lastname").value("Smith"))
			.andExpect(jsonPath("$.[0].id.firstUser.email").value("john.smith@mrandmrs.smth"))
			.andExpect(jsonPath("$.[0].id.firstUser.social").value(false))
			.andExpect(jsonPath("$.[0].id.firstUser.password").value("NotAnHashedAndSaltedPwd"))
			.andExpect(jsonPath("$.[0].id.firstUser.amount").value(30.0))
			.andExpect(jsonPath("$.[0].id.firstUser.active").value(true))
			.andExpect(jsonPath("$.[*].id.secondUser").isNotEmpty());
	}
	
	@Test
	public void getBuddiesByUser_shouldReturnOk() throws Exception {
		when(buddyService.getBuddiesByUser(any(User.class)))
			.thenReturn(buddyResponseList);
		
		mockMvc.perform(get("/mybuddies")
				.content(objectMapper.writeValueAsString(userResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id.firstUser").isNotEmpty())
			.andExpect(jsonPath("$.[*].id.secondUser").isNotEmpty());
	}
	
	@Test
	public void addBuddy_shouldReturnCreated() throws Exception {
		when(buddyService.addBuddy(any(Buddy.class)))
			.thenReturn(buddyResponse);
		
		mockMvc.perform(post("/buddy")
				.content(objectMapper.writeValueAsString(buddyResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id.firstUser.id").value(1))
			.andExpect(jsonPath("$.id.firstUser.firstname").value("John"))
			.andExpect(jsonPath("$.id.firstUser.lastname").value("Smith"))
			.andExpect(jsonPath("$.id.firstUser.email").value("john.smith@mrandmrs.smth"))
			.andExpect(jsonPath("$.id.firstUser.social").value(false))
			.andExpect(jsonPath("$.id.firstUser.password").value("NotAnHashedAndSaltedPwd"))
			.andExpect(jsonPath("$.id.firstUser.amount").value(30.0))
			.andExpect(jsonPath("$.id.firstUser.active").value(true))
			.andExpect(jsonPath("$.id.secondUser").isNotEmpty());
	}
	
	@Test
	public void deleteBuddy_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/buddy")
				.content(objectMapper.writeValueAsString(buddyResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteBuddyByUser_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/mybuddies")
				.content(objectMapper.writeValueAsString(userResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
