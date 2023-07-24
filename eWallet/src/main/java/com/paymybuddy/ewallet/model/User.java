package com.paymybuddy.ewallet.model;

public class User {

	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private boolean social;
	private String password;
	private float amount;
	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isSocial() {
		return social;
	}

	public void setSocial(boolean social) {
		this.social = social;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + firstname + "]" + "[" + lastname + "]" + "[" + email + "]" + "[isSocial=" + social + "]"
				+ "[" + password + "]" + "[" + amount + "]" + "[isActive=" + active + "]");
	}
}
