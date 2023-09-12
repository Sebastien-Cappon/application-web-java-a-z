package com.paymybuddy.ewallet.model;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.ewallet.views.TransactionView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(TransactionView.IdView.class)
	@Column(name = "transaction_id")
	private int id;
	@JsonView(TransactionView.DateView.class)
	@Column(name = "transaction_date")
	private LocalDate date;
	@JsonView(TransactionView.SenderView.class)
	@ManyToOne()
	@JoinColumn(name = "transaction_sender")
	private User sender;
	@JsonView(TransactionView.ReceiverView.class)
	@ManyToOne()
	@JoinColumn(name = "transaction_receiver")
	private User receiver;
	@Column(name = "transaction_amount")
	@JsonView(TransactionView.AmountView.class)
	private double amount;
	@JsonView(TransactionView.FeeView.class)
	@Column(name = "transaction_fee")
	private double fee;
	@JsonView(TransactionView.DescriptionView.class)
	@Column(name = "transaction_description")
	private String description;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
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
