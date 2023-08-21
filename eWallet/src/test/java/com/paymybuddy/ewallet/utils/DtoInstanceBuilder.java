package com.paymybuddy.ewallet.utils;

import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserLoginResponseDto;

public class DtoInstanceBuilder {
	public static UserLoginDto createUserLoginDto(String email, String password) {
		UserLoginDto userLoginDto = new UserLoginDto();
		userLoginDto.setEmail(email);
		userLoginDto.setPassword(password);

		return userLoginDto;
	}
	
	public static UserLoginResponseDto createUserLoginResponseDto(int id, String firstname, String lastName, String email) {
		UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
		userLoginResponseDto.setId(id);;
		userLoginResponseDto.setFirstname(firstname);
		userLoginResponseDto.setLastname(lastName);
		userLoginResponseDto.setEmail(email);

		return userLoginResponseDto;
	}
}
