package com.paymybuddy.ewallet.model;

import java.util.Date;

public class Transaction {

	private int id;
	private Date date;
	private int sender;
	private int receiver;
	private float amount;
	private float fee;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + date + "]" + "[" + receiver + "]" + "[" + amount + "]" + "[" + fee + "]" + "["
				+ description + "]");
	}
}
