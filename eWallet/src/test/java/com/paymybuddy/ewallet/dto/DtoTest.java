package com.paymybuddy.ewallet.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.paymybuddy.ewallet.utils.DtoInstanceBuilder;

public class DtoTest {

	@Test
	public void userLoginDtoToString_isNotBlank() {
		UserLoginDto userLoginDto = DtoInstanceBuilder.createUserLoginDto("john.smith@mrandmrs.smth", "NotAnHashedAndSaledPwd");
		
		assertThat(userLoginDto.toString()).isNotBlank();
	}
	
	@Test
	public void userProfileDtoToString_isNotBlank() {
		UserProfileDto userProfileDto = DtoInstanceBuilder.createUserProfileDto("John", "Smith", "john.smith@mrandmrs.smth", "NotAnHashedAndSaledPwd");
		
		assertThat(userProfileDto.toString()).isNotBlank();
	}

	@Test
	public void userTransactionDtoToString_isNotBlank() {
		UserTransactionDto userTransactionDto = DtoInstanceBuilder.createUserTransactionDto(1, "John", "Smith", "john.smith@mrandmrs.smth", true);
		
		assertThat(userTransactionDto.toString()).isNotBlank();
	}
		
	@Test
	public void transactionAddDtoToString_isNotBlank() {
		UserTransactionDto receiver = DtoInstanceBuilder.createUserTransactionDto(2, "Jane", "Smith", "jane.smith@mrandmrs.smth", true);
		TransactionAddDto transactionAddDto = DtoInstanceBuilder.createTransactionAddDto(1, receiver, 2.55, "Test transaction");
		
		assertThat(transactionAddDto.toString()).isNotBlank();
	}
	
	@Test
	public void buddyAddDtoToString_isNotBlank() {
		BuddyAddDto buddyAddDto = DtoInstanceBuilder.createBuddyAddDto(1, "john.smith@mrandmrs.smth");
		
		assertThat(buddyAddDto.toString()).isNotBlank();
	}
}