package com.paymybuddy.ewallet.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ModelsTest {

	@Test
	public void userToStringIsNotBlank() {
		String userToString = new User().toString();
		assertThat(userToString).isNotBlank();
	}
	
	@Test
	public void buddyToStringIsNotBlank() {
		String buddyToString = new Buddy().toString();
		assertThat(buddyToString).isNotBlank();
	}
	
	@Test
	public void transactionToStringIsNotBlank() {
		String transactionToString = new Transaction().toString();
		assertThat(transactionToString).isNotBlank();
	}
}
