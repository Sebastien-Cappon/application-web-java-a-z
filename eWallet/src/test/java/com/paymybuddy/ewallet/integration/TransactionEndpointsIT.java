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
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.TransactionRepository;
import com.paymybuddy.ewallet.repository.UserRepository;
import com.paymybuddy.ewallet.service.ITransactionService;
import com.paymybuddy.ewallet.service.IUserService;
import com.paymybuddy.ewallet.utils.DtoInstanceBuilder;
import com.paymybuddy.ewallet.utils.InstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionEndpointsIT {
	
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	IUserService iUserService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ITransactionService iTransactionService;
	@Autowired
	TransactionRepository transactionRepository;
	

	@Test
	public void getTransactions_shouldReturnOk() throws Exception {
		User testSender = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testReceiver = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		UserTransactionDto testReceiverDto = DtoInstanceBuilder.createUserTransactionDto(testReceiver.getId(), testReceiver.getFirstname(), testReceiver.getLastname(), testReceiver.getEmail(), testReceiver.isActive());
		Transaction testTransaction = iTransactionService.addTransaction(DtoInstanceBuilder.createTransactionAddDto(testSender.getId(), testReceiverDto, 20, "Test transaction"));
		
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
		
		transactionRepository.delete(testTransaction);
		userRepository.delete(testSender);
		userRepository.delete(testReceiver);
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
		User testSender = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testReceiver = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		UserTransactionDto testReceiverDto = DtoInstanceBuilder.createUserTransactionDto(testReceiver.getId(), testReceiver.getFirstname(), testReceiver.getLastname(), testReceiver.getEmail(), testReceiver.isActive());
		Transaction testTransaction = iTransactionService.addTransaction(DtoInstanceBuilder.createTransactionAddDto(testSender.getId(), testReceiverDto, 20, "Test transaction"));
		
		mockMvc.perform(get("/history/{id}", String.valueOf(testSender.getId()))
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
		
		transactionRepository.delete(testTransaction);
		userRepository.delete(testSender);
		userRepository.delete(testReceiver);
	}

	@Test
	public void getTransactionsByUser_orderByDateAsc_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/{id}", testUser.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
	}

	@Test
	public void getTransactionsByUser_orderByDateDesc_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/{id}", testUser.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
	}

	@Test
	public void getTransactionsByUser_orderByAmountAsc_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/{id}", testUser.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
	}

	@Test
	public void getTransactionsByUser_orderByAmountDesc_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/{id}", testUser.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
	}

	@Test
	public void getTransactionsByUser_orderByBuddyNameAsc_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "buddy");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/{id}", testUser.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
	}

	@Test
	public void getTransactionsByUser_orderByBuddyNameDesc_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "buddy");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/{id}", testUser.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testUser);
	}

	@Test
	public void getTransactionsFromEwallet_shouldReturnOk() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		EwalletTransactionAddDto testEwalletTransaction = DtoInstanceBuilder.createEwalletTransactionAddDto(testUser.getId(), 10);
		Transaction testTransaction = iTransactionService.addEwalletTransaction(testEwalletTransaction);
		
		mockMvc.perform(get("/history/ewallet/{id}", String.valueOf(testUser.getId()))
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
		
		transactionRepository.delete(testTransaction);
		userRepository.delete(testUser);
	}

	@Test
	public void getTransactionsBetweenUsers_shouldReturnOk() throws Exception {
		User testSender = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testReceiver = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		UserTransactionDto testReceiverDto = DtoInstanceBuilder.createUserTransactionDto(testReceiver.getId(), testReceiver.getFirstname(), testReceiver.getLastname(), testReceiver.getEmail(), testReceiver.isActive());
		Transaction testTransaction = iTransactionService.addTransaction(DtoInstanceBuilder.createTransactionAddDto(testSender.getId(), testReceiverDto, 20, "Test transaction"));

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", String.valueOf(testSender.getId()), String.valueOf(testReceiver.getId()))
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
		
		transactionRepository.delete(testTransaction);
		userRepository.delete(testSender);
		userRepository.delete(testReceiver);
	}

	@Test
	public void getTransactionsBetweenUsers_orderByDateAsc_shouldReturnOk() throws Exception {
		User testSender = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testReceiver = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", testSender.getId(), testReceiver.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testSender);
		userRepository.delete(testReceiver);
	}

	@Test
	public void getTransactionsBetweenUsers_orderByDateDesc_shouldReturnOk() throws Exception {
		User testSender = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testReceiver = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "date");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", testSender.getId(), testReceiver.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testSender);
		userRepository.delete(testReceiver);
	}

	@Test
	public void getTransactionsBetweenUsers_orderByAmountAsc_shouldReturnOk() throws Exception {
		User testSender = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testReceiver = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "asc");

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", testSender.getId(), testReceiver.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testSender);
		userRepository.delete(testReceiver);
	}

	@Test
	public void getTransactionsBetweenUsers_orderByAmountDesc_shouldReturnOk() throws Exception {
		User testSender = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testReceiver = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		
		MultiValueMap<String, String> sortByAndOrderParams = new LinkedMultiValueMap<>();
		sortByAndOrderParams.add("sortBy", "amount");
		sortByAndOrderParams.add("order", "desc");

		mockMvc.perform(get("/history/between/{userId}-{buddyId}", testSender.getId(), testReceiver.getId()).params(sortByAndOrderParams)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		userRepository.delete(testSender);
		userRepository.delete(testReceiver);
	}

	@Test
	public void addEwalletTransaction_shouldReturnCreated() throws Exception {
		User testUser = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		EwalletTransactionAddDto requestBody = DtoInstanceBuilder.createEwalletTransactionAddDto(testUser.getId(), 10);

		mockMvc.perform(post("/ewallet")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.date").isNotEmpty())
			.andExpect(jsonPath("$.sender.id").value(testUser.getId()))
			.andExpect(jsonPath("$.receiver.id").value(testUser.getId()))
			.andExpect(jsonPath("$.amount").value(10))
			.andExpect(jsonPath("$.fee").value(0))
			.andExpect(jsonPath("$.description").value("You have credit your account."));
		
		transactionRepository.delete(transactionRepository.findBySender(testUser).get());
		userRepository.delete(testUser);
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
		User testSender = iUserService.addUser(InstanceBuilder.createItUser("Santino", "D'Antonio", "santino.dantonio@testuser.874", "G14nn4sBr0th3r", 50, true));
		User testReceiver = iUserService.addUser(InstanceBuilder.createItUser("Gianna", "D'Antonio", "gianna.dantonio@testuser.874", "S4nt1n05ist3r", 50, true));
		UserTransactionDto testReceiverDto = DtoInstanceBuilder.createUserTransactionDto(testReceiver.getId(), testReceiver.getFirstname(), testReceiver.getLastname(), testReceiver.getEmail(), testReceiver.isActive());
		TransactionAddDto requestBody = DtoInstanceBuilder.createTransactionAddDto(testSender.getId(), testReceiverDto, 20, "Test transaction");
		
		mockMvc.perform(post("/transaction")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.date").isNotEmpty())
			.andExpect(jsonPath("$.sender.id").value(testSender.getId()))
			.andExpect(jsonPath("$.receiver.id").value(testReceiver.getId()))
			.andExpect(jsonPath("$.amount").value(20))
			.andExpect(jsonPath("$.fee").value(0.1))
			.andExpect(jsonPath("$.description").value("Test transaction"));
		
		transactionRepository.delete(transactionRepository.findBySender(testSender).get());
		userRepository.delete(testSender);
		userRepository.delete(testReceiver);
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
