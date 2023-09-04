package com.paymybuddy.ewallet.dto;

public class BuddyAddDto {

	private int userId;
	private String newBuddyEmail;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNewBuddyEmail() {
		return newBuddyEmail;
	}

	public void setNewBuddyEmail(String newBuddyEmail) {
		this.newBuddyEmail = newBuddyEmail;
	}

	@Override
	public String toString() {
		return ("[" + userId + "]" + "[" + newBuddyEmail + "]");
	}
}
