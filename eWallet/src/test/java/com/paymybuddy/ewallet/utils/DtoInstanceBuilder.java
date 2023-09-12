package com.paymybuddy.ewallet.utils;

import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.dto.TransactionAddDto;
import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.dto.UserTransactionDto;

public class DtoInstanceBuilder {
	
	public static UserLoginDto createUserLoginDto(String email, String password) {
		UserLoginDto userLoginDto = new UserLoginDto();
		userLoginDto.setEmail(email);
		userLoginDto.setPassword(password);

		return userLoginDto;
	}
	
	public static UserProfileDto createUserProfileDto(String firstname, String lastname, String email, String password) {
		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setFirstname(firstname);
		userProfileDto.setLastname(lastname);
		userProfileDto.setEmail(email);
		userProfileDto.setPassword(password);

		return userProfileDto;
	}
	
	public static UserTransactionDto createUserTransactionDto(int id, String firstname, String lastname, String email, boolean active) {
		UserTransactionDto userTransactionDto = new UserTransactionDto();
		userTransactionDto.setId(id);
		userTransactionDto.setFirstname(firstname);
		userTransactionDto.setLastname(lastname);
		userTransactionDto.setEmail(email);
		userTransactionDto.setActive(active);

		return userTransactionDto;
	}
	
	public static TransactionAddDto createTransactionAddDto(int senderId, UserTransactionDto receiver, double amount, String comment) {
		TransactionAddDto transactionAddDto = new TransactionAddDto();
		transactionAddDto.setSenderId(senderId);
		transactionAddDto.setReceiver(receiver);
		transactionAddDto.setAmount(amount);
		transactionAddDto.setComment(comment);

		return transactionAddDto;
	}

	public static BuddyAddDto createBuddyAddDto(int id, String email) {
		BuddyAddDto buddyAddDto = new BuddyAddDto();
		buddyAddDto.setUserId(id);
		buddyAddDto.setNewBuddyEmail(email);

		return buddyAddDto;
	}
}
