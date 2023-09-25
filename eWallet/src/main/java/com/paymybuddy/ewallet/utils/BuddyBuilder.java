package com.paymybuddy.ewallet.utils;

import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.BuddyKey;
import com.paymybuddy.ewallet.model.User;

/**
 * A class that contains two methods for creating a relationship between two
 * users (buddy) and the associated composite key.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class BuddyBuilder {
	
	/**
	 * A method that creates the necessary <code>BuddyKey</code> for creating a
	 * buddy relationship.
	 *
	 * @return A <code>BuddyKey</code>.
	 */
	public static BuddyKey createBuddyKey(User firstUser, User secondUser) {
		BuddyKey buddyKey = new BuddyKey();
		buddyKey.setFirstUser(firstUser);
		buddyKey.setSecondUser(secondUser);

		return buddyKey;
	}
	
	/**
	 * A method that creates a <code>Buddy</code> relationship between two
	 * <code>User</code> relationship.
	 *
	 * @return A <code>Buddy</code>.
	 */
	public static Buddy createBuddyFromUsers(User firstUser, User secondUser) {
		BuddyKey buddyKey = createBuddyKey(firstUser, secondUser);
		
		Buddy buddy = new Buddy();
		buddy.setId(buddyKey);

		return buddy;
	}
}