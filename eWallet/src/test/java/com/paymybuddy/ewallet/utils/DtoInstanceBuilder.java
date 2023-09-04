package com.paymybuddy.ewallet.utils;

import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;

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
	
	public static BuddyAddDto createBuddyAddDto(int id, String email) {
		BuddyAddDto buddyAddDto = new BuddyAddDto();
		buddyAddDto.setUserId(id);
		buddyAddDto.setNewBuddyEmail(email);

		return buddyAddDto;
	}
}
