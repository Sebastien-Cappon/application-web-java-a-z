package com.paymybuddy.ewallet.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.ewallet.dto.EwalletTransactionAddDto;
import com.paymybuddy.ewallet.dto.TransactionAddDto;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.TransactionRepository;
import com.paymybuddy.ewallet.repository.UserRepository;
import com.paymybuddy.ewallet.utils.TransactionUtil;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Transaction</code> before calling the repository.*
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class TransactionService implements ITransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private TransactionUtil transactionUtil;

	/**
	 * A <code>GET</code> method that returns a list of all transactions, filtered
	 * to retain only transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findAll());
	}

	/**
	 * A <code>GET</code> method that returns a list of all transactions ordered by
	 * date ascending, filtered to retain only transactions between two distinct
	 * users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions_orderByDateAsc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findByOrderByDateAsc());
	}

	/**
	 * A <code>GET</code> method that returns a list of all transactions ordered by
	 * date descending, filtered to retain only transactions between two distinct
	 * users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions_orderByDateDesc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findByOrderByDateDesc());
	}

	/**
	 * A <code>GET</code> method that returns a list of all transactions ordered by
	 * amount ascending, filtered to retain only transactions between two distinct
	 * users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions_orderByAmountAsc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findByOrderByAmountAsc());
	}

	/**
	 * A <code>GET</code> method that returns a list of all transactions ordered by
	 * date descending, filtered to retain only transactions between two distinct
	 * users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions_orderByAmountDesc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findByOrderByAmountDesc());
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions ordered by
	 * sender firstname, then lastname, ascending, filtered to retain only
	 * transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions_orderBySenderNameAsc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.getTransactions_orderBySenderNameAsc());
	}

	/**
	 * A <code>GET</code> method that returns a list of all transactions ordered by
	 * sender firstname, then lastname, descending, filtered to retain only
	 * transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions_orderBySenderNameDesc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.getTransactions_orderBySenderNameDesc());
	}

	/**
	 * A <code>GET</code> method that returns a list of all transactions ordered by
	 * receiver firstname, then lastname, ascending, filtered to retain only
	 * transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions_orderByReceiverNameAsc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.getTransactions_orderByReceiverNameAsc());
	}

	/**
	 * A <code>GET</code> method that returns a list of all transactions ordered by
	 * receiver firstname, then lastname, descending, filtered to retain only
	 * transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactions_orderByReceiverNameDesc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.getTransactions_orderByReceiverNameDesc());
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions for a given
	 * <code>User</code>, filtered to retain only transactions between
	 * two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsByUser(int userId) {
		User user = userRepository.findById(userId).get();
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByIdDesc(user, user));
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions for a given
	 * <code>User</code>, ordered by date ascending, filtered to retain only
	 * transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsByUser_orderByDateAsc(int userId) {
		User user = userRepository.findById(userId).get();
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByDateAsc(user, user));
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions for a given
	 * <code>User</code>, ordered by date descending, filtered to retain only
	 * transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsByUser_orderByDateDesc(int userId) {
		User user = userRepository.findById(userId).get();
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByDateDesc(user, user));
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions for a given
	 * <code>User</code>, ordered by amount ascending, filtered to retain only
	 * transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsByUser_orderByAmountAsc(int userId) {
		User user = userRepository.findById(userId).get();
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByAmountAsc(user, user));
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions for a given
	 * <code>User</code>, ordered by amount descending, filtered to retain only
	 * transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsByUser_orderByAmountDesc(int userId) {
		User user = userRepository.findById(userId).get();
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByAmountDesc(user, user));
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions for a given
	 * <code>User</code>, ordered by buddy (sender or receiver who is not the active
	 * user) firstname, then lastname, ascending, filtered to retain only transactions between
	 * two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsByUser_orderByBuddyNameAsc(int userId) {
		List<Transaction> transactionsByUserOrderByBuddyNameDesc = new ArrayList<>();
		TreeMap<String, Integer> buddies = transactionUtil.getBuddiesNameByTransactionByUser_orderByBuddyNameAsc(userId); 
		
		Set<String> keys = buddies.keySet();
		for(String key : keys) { 
			transactionsByUserOrderByBuddyNameDesc.addAll(transactionRepository.getTransactionsBetweenUsers_orderByDateDesc(userId, buddies.get(key)));
		}
		
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionsByUserOrderByBuddyNameDesc);
	}

	/**
	 * A <code>GET</code> method that returns a list of all transactions for a given
	 * <code>User</code>, ordered by buddy (sender or receiver who is not the active
	 * user) firstname, then lastname, descending, filtered to retain only transactions between
	 * two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsByUser_orderByBuddyNameDesc(int userId) {
		List<Transaction> transactionsByUserOrderByBuddyNameAsc = new ArrayList<>();
		TreeMap<String, Integer> buddies = transactionUtil.getBuddiesNameByTransactionByUser_orderByBuddyNameAsc(userId);
		
		Set<String> keys = buddies.descendingKeySet();
		for(String key : keys) { 
			transactionsByUserOrderByBuddyNameAsc.addAll(transactionRepository.getTransactionsBetweenUsers_orderByDateDesc(userId, buddies.get(key)));
		}
		
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionsByUserOrderByBuddyNameAsc);
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions from
	 * himself to himself for a given <code>User</code>. This correspond to e-wallet
	 * credits and debits.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsFromEwallet(int userId) {
		User user = userRepository.findById(userId).get();
		return transactionRepository.findBySenderAndReceiverOrderByIdDesc(user, user);
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions between the
	 * active <code>User</code> and a other given <code>User</code>, filtered to
	 * retain only transactions between two distinct users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsBetweenUsers(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers(firstUserId, secondUserId);
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions between the
	 * active <code>User</code> and a other given <code>User</code>, ordered by
	 * date ascending, filtered to retain only transactions between two distinct
	 * users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsBetweenUsers_orderByDateAsc(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers_orderByDateAsc(firstUserId, secondUserId);
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions between the
	 * active <code>User</code> and a other given <code>User</code>, ordered by
	 * date descending, filtered to retain only transactions between two distinct
	 * users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsBetweenUsers_orderByDateDesc(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers_orderByDateDesc(firstUserId, secondUserId);
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions between the
	 * active <code>User</code> and a other given <code>User</code>, ordered by
	 * amount ascending, filtered to retain only transactions between two distinct
	 * users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsBetweenUsers_orderByAmountAsc(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers_orderByAmountAsc(firstUserId, secondUserId);
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all transactions between the
	 * active <code>User</code> and a other given <code>User</code>, ordered by
	 * amount descending, filtered to retain only transactions between two distinct
	 * users.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@Override
	public List<Transaction> getTransactionsBetweenUsers_orderByAmountDesc(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers_orderByAmountDesc(firstUserId, secondUserId);
	}
	
	/**
	 * A <code>POST</code> method that returns a <code>Transaction</code> between
	 * the active user and himself, for which user id and amount are attributes of
	 * the <code>EwalletTransactionAddDto</code> passed as parameter. It's called
	 * when the active user credits or debits his e-wallet.
	 * 
	 * @singularity Sender is Receiver. These transactions have no fee. E-Wallet
	 *              debits are the only transactions in the entire API for which the
	 *              amount is negative.
	 * 
	 * @return A <code>Transaction</code> list OR <code>null</code> if
	 *         <code>User</code> doesn't exist.
	 */
	@Override
	public Transaction addEwalletTransaction(EwalletTransactionAddDto ewalletTransactionAddDto) throws Exception{
		if (userRepository.findById(ewalletTransactionAddDto.getUserId()).isPresent()) {
			double transactionAmount = ewalletTransactionAddDto.getAmount();
			String transactionComment = new String();
			
			if(transactionAmount >= 0) {
				transactionComment = "You have credit your account.";
			} else {
				transactionComment = "You have withdraw your money.";
			}
			
			User user = userRepository.findById(ewalletTransactionAddDto.getUserId()).get();
			user.setAmount(user.getAmount() + transactionAmount);
			iUserService.updateAmount(user.getId(), user.getAmount());
			
			Transaction newTransaction = new Transaction();
			newTransaction.setDate(LocalDate.now());
			newTransaction.setSender(user);
			newTransaction.setReceiver(user);
			newTransaction.setAmount(transactionAmount);
			newTransaction.setFee(0);
			newTransaction.setDescription(transactionComment);
			
			return transactionRepository.save(newTransaction);
		}
		
		return null;
	}
	
	/**
	 * A <code>POST</code> method that returns a <code>Transaction</code> between
	 * the active user and an other, , for which active user id, sender (as DTO),
	 * amount and comment are attributes of the <code>TransactionAddDto</code>
	 * passed as parameter.
	 * 
	 * @singularity The receiver attributes of <code>TransactionAddDto</code> is an
	 *              <code>UserTransactionDto</code>. The encrypted password must
	 *              never be transmitted between the Backend and the Frontend.
	 * 
	 * @return A <code>Transaction</code> list OR <code>null</code> if one of the
	 *         <code>User</code> entities concerned doesn't exist.
	 */
	@Override
	public Transaction addTransaction(TransactionAddDto transactionAddDto) throws Exception{
		if (userRepository.findById(transactionAddDto.getSenderId()).isPresent() && userRepository.findById(transactionAddDto.getReceiver().getId()).isPresent()) {
			double transactionAmount = transactionAddDto.getAmount();
			double transactionFee = Math.round(transactionAmount*0.5)/100.0;
			String transactionDescription = transactionAddDto.getComment();
			
			User sender = userRepository.findById(transactionAddDto.getSenderId()).get();
			sender.setAmount(sender.getAmount() - transactionAmount - transactionFee);
			iUserService.updateAmount(sender.getId(), sender.getAmount());
	
			User receiver = userRepository.findById(transactionAddDto.getReceiver().getId()).get();
			receiver.setAmount(receiver.getAmount() + transactionAmount);
			iUserService.updateAmount(receiver.getId(), receiver.getAmount());
			
			Transaction newTransaction = new Transaction();
			newTransaction.setDate(LocalDate.now());
			newTransaction.setSender(sender);
			newTransaction.setReceiver(receiver);
			newTransaction.setAmount(transactionAmount);
			newTransaction.setFee(transactionFee);
			newTransaction.setDescription(transactionDescription);
			
			return transactionRepository.save(newTransaction);
		}
		
		return null;
	}
}