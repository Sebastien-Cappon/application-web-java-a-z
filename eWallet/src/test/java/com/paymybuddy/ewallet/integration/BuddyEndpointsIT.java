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
import com.paymybuddy.ewallet.utils.DtoInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BuddyEndpointsIT {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void getBuddiesByUser_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/mybuddies/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].email").isNotEmpty())
			.andExpect(jsonPath("$.[*].active").isNotEmpty());
	}

	@Test
	public void getActiveBuddiesByUser_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/mybuddies/active/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].email").isNotEmpty())
			.andExpect(jsonPath("$.[*].active").isNotEmpty());
	}

	@Test
	public void addBuddy_shouldReturnCreated() throws Exception {
		BuddyAddDto requestBody = DtoInstanceBuilder.createBuddyAddDto(1, "akira.shimazy@continental.osa");

		mockMvc.perform(post("/buddy")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value("4"))
			.andExpect(jsonPath("$.firstname").value("Akira"))
			.andExpect(jsonPath("$.lastname").value("Shimazu"))
			.andExpect(jsonPath("$.email").value("akira.shimazy@continental.osa"))
			.andExpect(jsonPath("$.active").value(true));
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
		mockMvc.perform(delete("/buddy/{firstUserId}-{secondUserId}", "1", "4")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
