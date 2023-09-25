package com.paymybuddy.ewallet.dto;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>TransactionAddDto</code>. It contains getters and setters, as well as
 * an override <code>toSring()</code> method for display in the console.
 * 
 * @frontCall Refers to <code>TransferValue</code> model class.
 * 
 * @singularity <code>TransactionAddDto</code> contains the active
 *              <code>User</code> id, as sender, and a
 *              <code>UserTransactionDto</code> as receiver. The two others
 *              attributes are the amount of the transaction and its optional
 *              comment.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
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

	/**
	 * An override method for user-friendly display of
	 * <code>TransactionAddDto</code> attributes in the console. Not necessary,
	 * except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>TransactionAddDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + senderId + "]" + "[" + receiver.getId() + "]" + "[" + amount + "]" + "[" + comment + "]");
	}
}