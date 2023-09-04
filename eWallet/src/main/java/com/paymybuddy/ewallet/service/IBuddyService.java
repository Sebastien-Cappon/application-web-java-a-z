package com.paymybuddy.ewallet.service;

import java.util.List;

import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.User;

public interface IBuddyService {

	public List<Buddy> getBuddies();
	public List<User> getBuddiesByUser(int userId);
	
	public User addBuddy(BuddyAddDto buddyAddDto);
	public void deleteBuddy(int firstUserId, int secondUserId);
	public void deleteBuddiesByUser(User user);
}
