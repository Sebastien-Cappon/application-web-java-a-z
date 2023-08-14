package com.paymybuddy.ewallet.service;

import java.util.List;

import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.User;

public interface IBuddyService {

	public List<Buddy> getBuddies();
	public List<Buddy> getBuddiesByUser(User user);
	
	public Buddy addBuddy(Buddy buddy);
	public void deleteBuddy(Buddy buddy);
	public void deleteBuddiesByUser(User user);
}
