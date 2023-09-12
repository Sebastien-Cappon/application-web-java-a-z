package com.paymybuddy.ewallet.dto;

public class TransactionAddDto {

	private int senderId;
	private UserTransactionDto receiver;
	private double amount;
	private String comment;

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public UserTransactionDto getReceiver() {
		return receiver;
	}

	public void setReceiver(UserTransactionDto receiver) {
		this.receiver = receiver;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return ("[" + senderId + "]" + "[" + receiver.getId() + "]" + "[" + amount + "]" + "[" + comment + "]");
	}
}
