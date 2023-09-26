package com.paymybuddy.ewallet.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.UserRepository;
import com.paymybuddy.ewallet.service.IBuddyService;
import com.paymybuddy.ewallet.service.IUserService;
import com.paymybuddy.ewallet.utils.DtoInstanceBuilder;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BuddyEndpointsIT {

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	IUserService iUserService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	IBuddyService iBuddyService;

	@Test
	public void getBuddiesByUser_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testBuddy = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		BuddyAddDto newBuddies = DtoInstanceBuilder.createBuddyAddDto(testUser.getId(), testBuddy.getEmail());
		iBuddyService.addBuddy(newBuddies);
		
		mockMvc.perform(get("/mybuddies/{id}", testUser.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].email").isNotEmpty())
			.andExpect(jsonPath("$.[*].active").isNotEmpty());
		
		iBuddyService.deleteBuddy(testUser.getId(), testBuddy.getId());
		userRepository.delete(testUser);
		userRepository.delete(testBuddy);
	}

	@Test
	public void getActiveBuddiesByUser_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testBuddy = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		BuddyAddDto newBuddies = DtoInstanceBuilder.createBuddyAddDto(testUser.getId(), testBuddy.getEmail());
		iBuddyService.addBuddy(newBuddies);
		
		mockMvc.perform(get("/mybuddies/active/{id}", testUser.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].email").isNotEmpty())
			.andExpect(jsonPath("$.[*].active").isNotEmpty());
		
		iBuddyService.deleteBuddy(testUser.getId(), testBuddy.getId());
		userRepository.delete(testUser);
		userRepository.delete(testBuddy);
	}

	@Test
	public void addBuddy_shouldReturnCreated() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testBuddy = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		BuddyAddDto newBuddies = DtoInstanceBuilder.createBuddyAddDto(testUser.getId(), testBuddy.getEmail());

		mockMvc.perform(post("/buddy")
				.content(objectMapper.writeValueAsString(newBuddies))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(testBuddy.getId()))
			.andExpect(jsonPath("$.firstname").value("Gianna"))
			.andExpect(jsonPath("$.lastname").value("D'Antonio"))
			.andExpect(jsonPath("$.email").value("gianna.dantonio@testuser.874"))
			.andExpect(jsonPath("$.active").value(true));
		
		iBuddyService.deleteBuddy(testUser.getId(), testBuddy.getId());
		userRepository.delete(testUser);
		userRepository.delete(testBuddy);
	}

	@Test
	public void addBuddy_shouldThrowBadRequest() throws Exception {
		BuddyAddDto requestBody = DtoInstanceBuilder.createBuddyAddDto(0, null);

		mockMvc.perform(post("/buddy")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteBuddy_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testBuddy = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		BuddyAddDto newBuddies = DtoInstanceBuilder.createBuddyAddDto(testUser.getId(), testBuddy.getEmail());
		iBuddyService.addBuddy(newBuddies);
		
		mockMvc.perform(delete("/buddy/{firstUserId}-{secondUserId}", String.valueOf(testUser.getId()), String.valueOf(testBuddy.getId()))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
		userRepository.delete(testBuddy);
	}
}
