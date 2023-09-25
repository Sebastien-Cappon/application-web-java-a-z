package com.paymybuddy.ewallet.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.utils.DtoInstanceBuilder;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class UserEndpointsIT {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	public void getUserById_forLoginPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/users/login/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.firstname").value("John"))
			.andExpect(jsonPath("$.lastname").value("Wick"));
	}

	@Test
	public void getUserById_forProfilePage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/users/profile/{id}", "2")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstname").value("Winston"))
			.andExpect(jsonPath("$.lastname").value("Scott"))
			.andExpect(jsonPath("$.email").value("winston.scott@continental.ny"));
	}

	@Test
	public void getUserById_forHomePage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/users/amount/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value("1"))
			.andExpect(jsonPath("$.amount").isNotEmpty());
	}

	@Test
	public void postUserByEmailAndPassword_shouldReturnOk() throws Exception {
		UserLoginDto requestBody = DtoInstanceBuilder.createUserLoginDto("abram.tarasov@tarasov.mob", "IamSt177@liv3");
		
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(54))
			.andExpect(jsonPath("$.firstname").value("Abram"))
			.andExpect(jsonPath("$.lastname").value("Tarasov"));
	}

	@Test
	public void postUserByEmailAndPassword_shouldThrowBadRequest() throws Exception {
		UserLoginDto requestBody = DtoInstanceBuilder.createUserLoginDto("abram.tarasov@tarasov.mob", "J'ai oublié");
		
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void addUser_shouldReturnCreated() throws Exception {
		User requestBody = InstanceBuilder.createUser(9999, "Santino", "D'Antonio", "santino.dantonio@camorra.tbl", "G14nn4sBro7h3r", 0, true);
		
		mockMvc.perform(post("/user")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.amount").value(0));
	}

	@Test
	public void addUser_shouldThrowBadRequest() throws Exception {
		mockMvc.perform(post("/user")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void updateProfile_shouldReturnOk() throws Exception {
		UserProfileDto requestBody = DtoInstanceBuilder.createUserProfileDto("John", "Wick", "john.wick@runaway.run", "iMu5tFl33");
		
		mockMvc.perform(put("/users/{id}/profile", "1")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void updateProfile_shouldThrowBadRequest() throws Exception {
		UserProfileDto requestBody = DtoInstanceBuilder.createUserProfileDto(null, null, null, null);
		
		mockMvc.perform(put("/users/{id}/profile", "0")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void updateActive_shouldReturnOk() throws Exception {
		mockMvc.perform(put("/users/{id}/active", "1")
				.content(objectMapper.writeValueAsString(1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void updateActive_shouldThrowBadRequest() throws Exception {
		mockMvc.perform(put("/users/{id}/active", "0")
				.content(objectMapper.writeValueAsString(1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
}