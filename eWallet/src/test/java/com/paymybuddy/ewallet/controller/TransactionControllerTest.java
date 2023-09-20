package com.paymybuddy.ewallet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.paymybuddy.ewallet.dto.EwalletTransactionAddDto;
import com.paymybuddy.ewallet.dto.TransactionAddDto;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.TransactionService;
import com.paymybuddy.ewallet.service.UserService;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private User senderResponse = InstanceBuilder.createUser(1, "John", "Smith", "john.smith@mrandmrs.smth", false, "NotAnHashedAndSaledPwd", 30.0, true);
	private User receiverResponse = InstanceBuilder.createUser(2, "Jane", "Smith", "jane.smith@mrandmrs.smth", false, "DammitTheSecuritySucks", 120.0, true);
	private Transaction transactionResponse = InstanceBuilder.createTransaction(1, LocalDate.parse("2023-07-22", dateTimeFormatter), senderResponse, receiverResponse, 20, 0.1, "First transaction");
	private List<Transaction> transactionResponseList = new ArrayList<Transaction>(List.of(transactionResponse, transactionResponse, transactionResponse));

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private TransactionService transactionService;
	
	@Test
	public void getTransactions_shouldReturnOk() throws Exception {
		when(transactionService.getTransactions())
			.thenReturn(transactionResponseList);
		
		mockMvc.perform(get("/transactions")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].date").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver").isNotEmpty())
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
	public void getTransactionById_shouldReturnOk() throws Exception {
		when(transactionService.getTransactionById(any(Integer.class)))
			.thenReturn(transactionResponse);

		mockMvc.perform(get("/transaction").param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))		
			.andExpect(jsonPath("$.date").value(LocalDate.parse("2023-07-22", dateTimeFormatter).toString()))		
			.andExpect(jsonPath("$.sender.firstname").value("John"))		
			.andExpect(jsonPath("$.sender.lastname").value("Smith"))		
			.andExpect(jsonPath("$.receiver.firstname").value("Jane"))		
			.andExpect(jsonPath("$.receiver.lastname").value("Smith"))		
			.andExpect(jsonPath("$.amount").value(20))		
			.andExpect(jsonPath("$.fee").value(0.1))		
			.andExpect(jsonPath("$.description").value("First transaction"));		
	}

	@Test
	public void getTransactionsByUser_shouldReturnOk() throws Exception {
		when(transactionService.getTransactionsByUser(any(Integer.class)))
			.thenReturn(transactionResponseList);
	
		mockMvc.perform(get("/history/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].date").isNotEmpty())
			.andExpect(jsonPath("$.[*].sender").isNotEmpty())
			.andExpect(jsonPath("$.[*].receiver").isNotEmpty())
			.andExpect(jsonPath("$.[*].amount").isNotEmpty())
			.andExpect(jsonPath("$.[*].fee").isNotEmpty())
			.andExpect(jsonPath("$.[*].description").isNotEmpty());	
	}
	
	@Test
	public void getTransactionsByUser_orderByDateAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "asc");
		
		when(transactionService.getTransactionsByUser_orderByDateAsc(any(Integer.class)))
			.thenReturn(transactionResponseList);
		
		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getTransactionsByUser_orderByDateDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "desc");
		
		when(transactionService.getTransactionsByUser_orderByDateDesc(any(Integer.class)))
			.thenReturn(transactionResponseList);
	
		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getTransactionsByUser_orderByAmountAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "asc");
		
		when(transactionService.getTransactionsByUser_orderByAmountAsc(any(Integer.class)))
			.thenReturn(transactionResponseList);
		
		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getTransactionsByUser_orderByAmountDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "desc");
		
		when(transactionService.getTransactionsByUser_orderByAmountDesc(any(Integer.class)))
			.thenReturn(transactionResponseList);
	
		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getTransactionsByUser_orderByBuddyNameAsc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "receiver");
		sortByAndOrderParams.add("order", "asc");
		
		when(transactionService.getTransactionsByUser_orderByBuddyNameAsc(any(Integer.class)))
			.thenReturn(transactionResponseList);
	
		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getTransactionsByUser_orderByBuddyNameDesc_shouldReturnOk() throws Exception {
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "receiver");
		sortByAndOrderParams.add("order", "desc");
		
		when(transactionService.getTransactionsByUser_orderByBuddyNameDesc(any(Integer.class)))
			.thenReturn(transactionResponseList);
	
		mockMvc.perform(get("/history/{id}", "1").params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void addEwalletTransaction_shouldReturnCreated() throws Exception {
		when(transactionService.addEwalletTransaction(any(EwalletTransactionAddDto.class)))
			.thenReturn(transactionResponse);
		
		mockMvc.perform(post("/ewallet")
				.content(objectMapper.writeValueAsString(transactionResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.date").value(LocalDate.parse("2023-07-22", dateTimeFormatter).toString()))		
		.andExpect(jsonPath("$.sender.id").value("1"))		
		.andExpect(jsonPath("$.receiver.id").value("2"))		
		.andExpect(jsonPath("$.amount").value(20))		
		.andExpect(jsonPath("$.fee").value(0.1))		
		.andExpect(jsonPath("$.description").value("First transaction"));
	}
	
	@Test
	public void addEwalletTransaction_shouldThrowBadRequest() throws Exception {
		when(transactionService.addEwalletTransaction(any(EwalletTransactionAddDto.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/ewallet"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void addTransaction_shouldReturnCreated() throws Exception {
		when(transactionService.addTransaction(any(TransactionAddDto.class)))
			.thenReturn(transactionResponse);
		
		mockMvc.perform(post("/transaction")
				.content(objectMapper.writeValueAsString(transactionResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.date").value(LocalDate.parse("2023-07-22", dateTimeFormatter).toString()))		
		.andExpect(jsonPath("$.sender.id").value("1"))		
		.andExpect(jsonPath("$.receiver.id").value("2"))		
		.andExpect(jsonPath("$.amount").value(20))		
		.andExpect(jsonPath("$.fee").value(0.1))		
		.andExpect(jsonPath("$.description").value("First transaction"));
	}
	
	@Test
	public void addTransaction_shouldThrowBadRequest() throws Exception {
		when(transactionService.addTransaction(any(TransactionAddDto.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/transaction"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTransactionById_shouldReturnOk() throws Exception {
		when(transactionService.updateTransactionById(any(Transaction.class)))
			.thenReturn(transactionResponse);
		
		mockMvc.perform(put("/transaction")
				.content(objectMapper.writeValueAsString(transactionResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))		
			.andExpect(jsonPath("$.date").value(LocalDate.parse("2023-07-22", dateTimeFormatter).toString()))		
			.andExpect(jsonPath("$.sender.firstname").value("John"))		
			.andExpect(jsonPath("$.sender.lastname").value("Smith"))		
			.andExpect(jsonPath("$.receiver.firstname").value("Jane"))		
			.andExpect(jsonPath("$.receiver.lastname").value("Smith"))		
			.andExpect(jsonPath("$.amount").value(20))		
			.andExpect(jsonPath("$.fee").value(0.1))		
			.andExpect(jsonPath("$.description").value("First transaction"));;
	}
	
	@Test
	public void updateTransactionById_shouldThrowBadRequest() throws Exception {
		when(transactionService.updateTransactionById(any(Transaction.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(put("/transaction"))
			.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void deleteTransaction_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/transaction")
				.content(objectMapper.writeValueAsString(transactionResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
