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
	public void userLoginResponseDtoToString_isNotBlank() {
		UserLoginResponseDto userLoginDto = DtoInstanceBuilder.createUserLoginResponseDto(1, "John", "Smith", "john.smith@mrandmrs.smth");
		
		assertThat(userLoginDto.toString()).isNotBlank();
	}
}