package com.paymybuddy.ewallet.constants;

public class MySqlQueries {

	public static final String allTransaction_orderByReceiverNameAsc = "SELECT * FROM transaction JOIN user ON transaction.transaction_receiver = user.user_id ORDER BY user.user_lastname ASC, user.user_firstname ASC, transaction_date DESC;";
	public static final String allTransaction_orderByReceiverNameDesc = "SELECT * FROM transaction JOIN user ON transaction.transaction_receiver = user.user_id ORDER BY user.user_lastname DESC, user.user_firstname DESC, transaction_date DESC;";
	public static final String allTransactionBySender_orderByReceiverNameAsc = "SELECT * FROM transaction JOIN user ON transaction.transaction_receiver = user.user_id WHERE transaction_sender = ? ORDER BY user.user_lastname ASC, user.user_firstname ASC, transaction_date DESC;";
	public static final String allTransactionBySender_orderByReceiverNameDesc = "SELECT * FROM transaction JOIN user ON transaction.transaction_receiver = user.user_id WHERE transaction_sender = ? ORDER BY user.user_lastname DESC, user.user_firstname DESC, transaction_date DESC;";
	public static final String updateTransactionById = "UPDATE transaction SET transaction_description = ?2 WHERE transaction_id = ?1 ;";
	
	public static final String allUser_orderByNameAsc = "SELECT * FROM user ORDER BY user.user_lastname ASC, user.user_firstname ASC, user.user_id ASC";
	public static final String allUser_orderByNameDesc = "SELECT * FROM user ORDER BY user.user_lastname DESC, user.user_firstname DESC, user.user_id ASC";
	
	public static final String allBuddiesByUser = "SELECT * FROM user_user WHERE buddies_user_id = ?1 OR buddies_buddy_id = ?1";
	public static final String deleteBuddiesByUser = "DELETE FROM user_user WHERE buddies_user_id = ?1 OR buddies_buddy_id = ?1";
}