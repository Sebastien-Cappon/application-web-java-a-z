package com.paymybuddy.ewallet.integration;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.ewallet.dto.EwalletTransactionAddDto;
import com.paymybuddy.ewallet.dto.TransactionAddDto;
import com.paymybuddy.ewallet.dto.UserTransactionDto;
import com.paymybuddy.ewallet.utils.DtoInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionEndpointsIT {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void getTransactions_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/transactions")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].date").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.id").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.id").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].amount").isNotEmpty())
			.andExpect(jsonPath("$.[*].fee").isNotEmpty())
			.andExpect(jsonPath("$.[*].description").isNotEmpty());
	}

	@Test
	public void getTransactions_orderByDateAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/transactions").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactions_orderByDateDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/transactions").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactions_orderByAmountAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/transactions").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactions_orderByAmountDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/transactions").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactions_orderBySenderNameAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "sender");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/transactions").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactions_orderBySenderNameDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "sender");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/transactions").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactions_orderByReceiverNameAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "receiver");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/transactions").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactions_orderByReceiverNameDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "receiver");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/transactions").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsByUser_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/history/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].date").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.id").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.id").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].amount").isNotEmpty())
			.andExpect(jsonPath("$.[*].fee").isNotEmpty())
			.andExpect(jsonPath("$.[*].description").isNotEmpty());
	}

	@Test
	public void getTransactionsByUser_orderByDateAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsByUser_orderByDateDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsByUser_orderByAmountAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsByUser_orderByAmountDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsByUser_orderByBuddyNameAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "buddy");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsByUser_orderByBuddyNameDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "buddy");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsFromEwallet_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/history/ewallet/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].date").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.id").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.id").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].amount").isNotEmpty())
			.andExpect(jsonPath("$.[*].fee").isNotEmpty())
			.andExpect(jsonPath("$.[*].description").isNotEmpty());
	}

	@Test
	public void getTransactionsBetweenUsers_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/history/between/{userId}-{buddyId}", "1", "2")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].date").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.id").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender.lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.id").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver.lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].amount").isNotEmpty())
			.andExpect(jsonPath("$.[*].fee").isNotEmpty())
			.andExpect(jsonPath("$.[*].description").isNotEmpty());
	}

	@Test
	public void getTransactionsBetweenUsers_orderByDateAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", "1", "2").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsBetweenUsers_orderByDateDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", "1", "2").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsBetweenUsers_orderByAmountAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", "1", "2").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void getTransactionsBetweenUsers_orderByAmountDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", "1", "2").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void addEwalletTransaction_shouldReturnCreated() throws Exception {
		EwalletTransactionAddDto requestBody = DtoInstanceBuilder.createEwalletTransactionAddDto(1, 29.99);
		
		mockMvc.perform(post("/ewallet")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.date").isNotEmpty())
			.andExpect(jsonPath("$.sender.id").value("1"))
			.andExpect(jsonPath("$.receiver.id").value("1"))
			.andExpect(jsonPath("$.amount").value(29.99))
			.andExpect(jsonPath("$.fee").value(0))
			.andExpect(jsonPath("$.description").value("You have credit your account."));
	}

	@Test
	public void addEwalletTransaction_shouldThrowBadRequest() throws Exception {
		mockMvc.perform(post("/ewallet")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void addTransaction_shouldReturnCreated() throws Exception {
		UserTransactionDto receiver = DtoInstanceBuilder.createUserTransactionDto(2, "Winston", "Scott", "winston.scott@continental.ny", true);
		TransactionAddDto requestBody = DtoInstanceBuilder.createTransactionAddDto(1, receiver, 14.50, "Refunds. Thanks for the help !");
		
		mockMvc.perform(post("/transaction")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.date").isNotEmpty())
			.andExpect(jsonPath("$.sender.id").value("1"))
			.andExpect(jsonPath("$.receiver.id").value("2"))
			.andExpect(jsonPath("$.amount").value(14.50))
			.andExpect(jsonPath("$.fee").value(0.07))
			.andExpect(jsonPath("$.description").value("Refunds. Thanks for the help !"));
	}

	@Test
	public void addTransaction_shouldThrowBadRequest() throws Exception {
		mockMvc.perform(post("/transaction")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
}
