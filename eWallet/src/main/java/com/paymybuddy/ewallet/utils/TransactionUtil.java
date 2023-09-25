package com.paymybuddy.ewallet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.TransactionRepository;
import com.paymybuddy.ewallet.repository.UserRepository;

/**
 * A class that contains two methods for filtering and ordering
 * <code>Transaction</code> model entities.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Component
public class TransactionUtil {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * A method that removes transactions the user's transactions to itself
	 * (e-wallet credits and debits) from a list of transactions.
	 *
	 * @return A <code>Transaction</code> list.
	 */
	public List<Transaction> filterTransactionsOfEwalletHistory(List<Transaction> transactions) {
		List<Transaction> filteredTransactions = new ArrayList<>();

		for (Transaction transaction : transactions) {
			if (transaction.getSender().getId() != transaction.getReceiver().getId()) {
				filteredTransactions.add(transaction);
			}
		}

		return filteredTransactions;
	}

	/**
	 * A method that retrieves buddies (!= active user) from a
	 * <code>Transaction</code> list, regardless of whether they are in sender or
	 * receiver.
	 *
	 * @return A <code>TreeMap</code> where the keys are user names and the values
	 *         are user ids.
	 */
	public TreeMap<String, Integer> getBuddiesNameByTransactionByUser_orderByBuddyNameAsc(int userId) {
		TreeMap<String, Integer> buddies = new TreeMap<>();

		if (userRepository.findById(userId).isPresent()) {
			User user = userRepository.findById(userId).get();
			List<Transaction> transactionsByUser = transactionRepository.findBySenderOrReceiverOrderByIdDesc(user, user);

			for (Transaction transaction : transactionsByUser) {
				if (transaction.getSender() == user) {
					String buddyName = transaction.getReceiver().getFirstname() + transaction.getReceiver().getLastname();
					buddies.put(buddyName, transaction.getReceiver().getId());
				} else {
					String buddyName = transaction.getSender().getFirstname() + transaction.getSender().getLastname();
					buddies.put(buddyName, transaction.getSender().getId());
				}
			}
		}

		return buddies;
	}
}