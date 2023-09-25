package com.paymybuddy.ewallet.utils;

import java.time.LocalDate;

import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.BuddyKey;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;

public class InstanceBuilder {

	public static Transaction createTransaction(int id, LocalDate date, User sender, User receiver, double amount, double fee, String description) {
		Transaction transaction = new Transaction();
		transaction.setId(id);
		transaction.setDate(date);
		transaction.setSender(sender);
		transaction.setReceiver(receiver);
		transaction.setAmount(amount);
		transaction.setFee(fee);
		transaction.setDescription(description);

		return transaction;
	}
	
	public static User createUser(int id, String firstname, String lastName, String email, String password, double amount, boolean active) {
		User user = new User();
		user.setId(id);
		user.setFirstname(firstname);
		user.setLastname(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setAmount(amount);
		user.setActive(active);
		
		return user;
	}
	
	public static BuddyKey createBuddyKey(User firstUser, User SecondUser) {
		BuddyKey buddyKey = new BuddyKey();
		buddyKey.setFirstUser(firstUser);
		buddyKey.setSecondUser(SecondUser);
		
		return buddyKey;
	}
	
	public static Buddy createBuddy(BuddyKey buddyKey) {
		Buddy buddy = new Buddy();
		buddy.setId(buddyKey);
		
		return buddy;
	}
}
