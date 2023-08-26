package com.paymybuddy.ewallet.model;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.ewallet.views.UserView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(UserView.IdView.class)
	@Column(name = "user_id")
	private int id;
	@JsonView(UserView.FirstnameView.class)
	@Column(name = "user_firstname")
	private String firstname;
	@JsonView(UserView.LastnameView.class)
	@Column(name = "user_lastname")
	private String lastname;
	@JsonView(UserView.EmailView.class)
	@Column(name = "user_email")
	private String email;
	@JsonView(UserView.SocialView.class)
	@Column(name = "user_social")
	private boolean social;
	@JsonView(UserView.PasswordView.class)
	@Column(name = "user_password")
	private String password;
	@JsonView(UserView.AmountView.class)
	@Column(name = "user_amount")
	private double amount;
	@JsonView(UserView.ActiveView.class)
	@Column(name = "user_active")
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
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
		return ("[" + id + "]" + "[" + firstname + "]" + "[" + lastname + "]" + "[" + email + "]" + "[isSocial="
				+ social + "]" + "[" + password + "]" + "[" + amount + "]" + "[isActive=" + active + "]");
	}
}
