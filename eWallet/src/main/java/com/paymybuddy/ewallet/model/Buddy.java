package com.paymybuddy.ewallet.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_user")
public class Buddy {

	@EmbeddedId
	private BuddyKey id;

	public BuddyKey getId() {
		return id;
	}

	public void setId(BuddyKey id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return ("[" + id + "]");
	}
}
