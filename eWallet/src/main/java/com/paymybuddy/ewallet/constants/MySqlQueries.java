package com.paymybuddy.ewallet.constants;

/**
 * A class of constants that groups together a list of JPQL <i>(Java Persistence
 * Query Language)</i> queries. Most of these are linked to the application's
 * sorting system. Others are specific to the bidirectionnality of the
 * <code>user_user</code> join table, represented by <code>Buddy.java</code>
 * model class.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class MySqlQueries {

	public static final String allTransaction_orderBySenderNameAsc = "SELECT * FROM transaction JOIN user ON transaction.transaction_sender = user.user_id ORDER BY user.user_firstname ASC, user.user_lastname ASC";
	public static final String allTransaction_orderBySenderNameDesc = "SELECT * FROM transaction JOIN user ON transaction.transaction_sender = user.user_id ORDER BY user.user_firstname DESC, user.user_lastname DESC";
	public static final String allTransaction_orderByReceiverNameAsc = "SELECT * FROM transaction JOIN user ON transaction.transaction_receiver = user.user_id ORDER BY user.user_firstname ASC, user.user_lastname ASC";
	public static final String allTransaction_orderByReceiverNameDesc = "SELECT * FROM transaction JOIN user ON transaction.transaction_receiver = user.user_id ORDER BY user.user_firstname DESC, user.user_lastname DESC";
	
	public static final String allTransactionBySenderAndReceiver = "SELECT * FROM transaction WHERE transaction_sender = ?1 AND transaction_receiver = ?2 OR transaction_sender = ?2 AND transaction_receiver = ?1 ORDER BY transaction_date DESC;";
	public static final String allTransactionBySenderAndReceiver_orderByDateAsc = "SELECT * FROM transaction WHERE transaction_sender = ?1 AND transaction_receiver = ?2 OR transaction_sender = ?2 AND transaction_receiver = ?1 ORDER BY transaction_date ASC;";
	public static final String allTransactionBySenderAndReceiver_orderByDateDesc = "SELECT * FROM transaction WHERE transaction_sender = ?1 AND transaction_receiver = ?2 OR transaction_sender = ?2 AND transaction_receiver = ?1 ORDER BY transaction_date DESC;";
	public static final String allTransactionBySenderAndReceiver_orderByAmountAsc = "SELECT * FROM transaction WHERE transaction_sender = ?1 AND transaction_receiver = ?2 OR transaction_sender = ?2 AND transaction_receiver = ?1 ORDER BY transaction_amount ASC;";
	public static final String allTransactionBySenderAndReceiver_orderByAmountDesc = "SELECT * FROM transaction WHERE transaction_sender = ?1 AND transaction_receiver = ?2 OR transaction_sender = ?2 AND transaction_receiver = ?1 ORDER BY transaction_amount DESC;";
	
	public static final String updateTransactionById = "UPDATE transaction SET transaction_description = ?2 WHERE transaction_id = ?1 ;";
	
	public static final String allUser_orderByNameAsc = "SELECT * FROM user ORDER BY user.user_lastname ASC, user.user_firstname ASC, user.user_id ASC";
	public static final String allUser_orderByNameDesc = "SELECT * FROM user ORDER BY user.user_lastname DESC, user.user_firstname DESC, user.user_id ASC";
	public static final String updateProfileById = "UPDATE user  SET user_firstname = ?2, user_lastname = ?3, user_email = ?4, user_password = ?5  WHERE user_id = ?1 ;";
	public static final String updateActiveById = "UPDATE user  SET user_active = ?2  WHERE user_id = ?1 ;";
	public static final String updateAmountById = "UPDATE user  SET user_amount = ?2  WHERE user_id = ?1 ;";
	
	public static final String allBuddiesByUser = "SELECT * FROM user_user WHERE buddies_user_id = ?1 OR buddies_buddy_id = ?1";
	public static final String deleteBuddiesByUser = "DELETE FROM user_user WHERE buddies_user_id = ?1 OR buddies_buddy_id = ?1";
}