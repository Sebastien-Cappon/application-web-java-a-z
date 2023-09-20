package com.paymybuddy.ewallet.dto;

public class EwalletTransactionAddDto {
	private int userId;
	private double amount;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return ("[" + userId + "]" + "[" + amount + "]");
	}
}
