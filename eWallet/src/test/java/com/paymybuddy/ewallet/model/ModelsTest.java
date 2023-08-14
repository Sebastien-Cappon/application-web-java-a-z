package com.paymybuddy.ewallet.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.paymybuddy.ewallet.utils.InstanceBuilder;

public class ModelsTest {

	private User user = InstanceBuilder.createUser(1, "John", "Smith", "john.smith@mrandmrs.smth", false, "NotAnHashedAndSaledPwd", 30.0, true);
	
	@Test
	public void userToString_isNotBlank() {
		assertThat(user.toString()).isNotBlank();
	}
	
	@Test
	public void transactionToString_isNotBlank() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Transaction transaction = InstanceBuilder.createTransaction(1, LocalDate.parse("2023-07-22", dateTimeFormatter), user, user, 20, 0.1, "First transaction");
		
		assertThat(transaction.toString()).isNotBlank();
	}
	
	@Test
	public void buddyToString_isNotBlank() {
		BuddyKey buddyKey = InstanceBuilder.createBuddyKey(user, user);
		Buddy buddy = new Buddy();
		buddy.setId(buddyKey);
		
		assertThat(buddy.toString()).isNotBlank();
	}
}
