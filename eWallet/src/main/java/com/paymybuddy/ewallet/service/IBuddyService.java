package com.paymybuddy.ewallet.service;

import java.util.List;

import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.model.User;

/**
 * <code>BuddyService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IBuddyService {

	public List<User> getBuddiesByUser(int userId);
	public List<User> getActiveBuddiesByUser(int userId);
	
	public User addBuddy(BuddyAddDto buddyAddDto);
	public void deleteBuddy(int firstUserId, int secondUserId);
}