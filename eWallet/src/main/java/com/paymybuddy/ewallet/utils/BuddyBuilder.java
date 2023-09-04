package com.paymybuddy.ewallet.utils;

import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.BuddyKey;
import com.paymybuddy.ewallet.model.User;

public class BuddyBuilder {
	
	public static BuddyKey createBuddyKey(User firstUser, User secondUser) {
		BuddyKey buddyKey = new BuddyKey();
		buddyKey.setFirstUser(firstUser);
		buddyKey.setSecondUser(secondUser);

		return buddyKey;
	}
	
	public static Buddy createBuddyFromUsers(User firstUser, User secondUser) {
		BuddyKey buddyKey = createBuddyKey(firstUser, secondUser);
		
		Buddy buddy = new Buddy();
		buddy.setId(buddyKey);

		return buddy;
	}
}
