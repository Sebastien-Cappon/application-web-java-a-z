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
	public void buddyAddDtoToString_isNotBlank() {
		BuddyAddDto buddyAddDto = DtoInstanceBuilder.createBuddyAddDto(1, "john.smith@mrandmrs.smth");
		
		assertThat(buddyAddDto.toString()).isNotBlank();
	}
}