package com.paymybuddy.ewallet.model;

public class Buddy {

	private int user_id;
	private int buddy_id;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getBuddy_id() {
		return buddy_id;
	}

	public void setBuddy_id(int buddy_id) {
		this.buddy_id = buddy_id;
	}

	@Override
	public String toString() {
		return ("[user=" + user_id + "]" + "[buddy=" + buddy_id + "]");
	}
}
