package com.paymybuddy.ewallet.dto;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>EwalletTransactionAddDto</code>. It contains getters and setters, as
 * well as an override <code>toSring()</code> method for display in the console.
 * 
 * @frontCall Refers to <code>EwalletTransferValue</code> model class.
 * 
 * @singularity <code>EwalletTransactionAddDto</code> contains the active
 *              <code>User</code> id and the amount to be credited to, or
 *              debited from, his e-wallet.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
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

	/**
	 * An override method for user-friendly display of
	 * <code>EwalletTransactionAddDto</code> attributes in the console. Not
	 * necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>EwalletTransactionAddDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + userId + "]" + "[" + amount + "]");
	}
}