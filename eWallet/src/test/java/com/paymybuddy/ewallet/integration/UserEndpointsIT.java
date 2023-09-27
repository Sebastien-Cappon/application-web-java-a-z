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
import com.paymybuddy.ewallet.repository.TransactionRepository;
import com.paymybuddy.ewallet.repository.UserRepository;
import com.paymybuddy.ewallet.service.IUserService;
import com.paymybuddy.ewallet.utils.DtoInstanceBuilder;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class UserEndpointsIT {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	

	@Test
	public void getUserById_forLoginPage_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, true));
		
		mockMvc.perform(get("/users/login/{id}", String.valueOf(testUser.getId()))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(testUser.getId()))
			.andExpect(jsonPath("$.firstname").value("Santino"))
			.andExpect(jsonPath("$.lastname").value("D'Antonio"));
		
		userRepository.delete(testUser);
	}

	@Test
	public void getUserById_forProfilePage_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, true));
		
		mockMvc.perform(get("/users/profile/{id}", String.valueOf(testUser.getId()))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstname").value("Santino"))
			.andExpect(jsonPath("$.lastname").value("D'Antonio"))
			.andExpect(jsonPath("$.email").value("santino.dantonio@testuser.874"));
		
		userRepository.delete(testUser);
	}

	@Test
	public void getUserById_forHomePage_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, true));
		
		mockMvc.perform(get("/users/amount/{id}", String.valueOf(testUser.getId()))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(testUser.getId()))
			.andExpect(jsonPath("$.amount").value(0));
		
		userRepository.delete(testUser);
	}
	
	@Test
	public void postUserByEmailAndPassword_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, true));
		UserLoginDto requestBody = DtoInstanceBuilder.createUserLoginDto("santino.dantonio@testuser.874", "G14nn4sBr0th3r");
		
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(testUser.getId()))
			.andExpect(jsonPath("$.firstname").value("Santino"))
			.andExpect(jsonPath("$.lastname").value("D'Antonio"));

		userRepository.delete(testUser);
	}

	@Test
	public void postUserByEmailAndPassword_shouldThrowBadRequest() throws Exception {
		UserLoginDto requestBody = DtoInstanceBuilder.createUserLoginDto("santino.dantonio@testuser.874", "DoesntExist");
		
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void addUser_shouldReturnCreated() throws Exception {
		User testUser = InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, true);
		
		mockMvc.perform(post("/user")
				.content(objectMapper.writeValueAsString(testUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.amount").value(0));

		userRepository.delete(userRepository.findByEmail("santino.dantonio@testuser.874").get());
	}
	
	@Test
	public void addUser_reactivate_shouldReturnCreated() throws Exception {
		iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, false));
		User testUser = InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "aFr35hNewP455word", 0, true);
		
		mockMvc.perform(post("/user")
				.content(objectMapper.writeValueAsString(testUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.amount").value(0));

		userRepository.delete(userRepository.findByEmail("santino.dantonio@testuser.874").get());
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
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, true));
		UserProfileDto requestBody = DtoInstanceBuilder.createUserProfileDto("", "", "", "");
		
		mockMvc.perform(put("/users/{id}/profile", String.valueOf(testUser.getId()))
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
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
	public void updateActive_turnInactive_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, true));
		
		mockMvc.perform(put("/users/{id}/active", String.valueOf(testUser.getId()))
				.content(objectMapper.writeValueAsString(0))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		transactionRepository.delete(transactionRepository.findBySender(testUser).get());
		userRepository.delete(testUser);
	}
	
	@Test
	public void updateActive_turnActive_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 0, true));
		
		mockMvc.perform(put("/users/{id}/active", String.valueOf(testUser.getId()))
				.content(objectMapper.writeValueAsString(1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
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