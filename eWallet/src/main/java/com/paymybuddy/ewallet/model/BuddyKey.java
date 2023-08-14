package com.paymybuddy.ewallet.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class BuddyKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne()
	@JoinColumn(name = "buddies_user_id")
	private User firstUser;
	
	@ManyToOne()
	@JoinColumn(name = "buddies_buddy_id")
	private User secondUser;
	
	public User getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(User firstUser) {
		this.firstUser = firstUser;
	}

	public User getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(User secondUser) {
		this.secondUser = secondUser;
	}
}
