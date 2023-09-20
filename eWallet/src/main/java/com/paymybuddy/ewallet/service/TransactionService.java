package com.paymybuddy.ewallet.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.ewallet.dto.EwalletTransactionAddDto;
import com.paymybuddy.ewallet.dto.TransactionAddDto;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.TransactionRepository;
import com.paymybuddy.ewallet.repository.UserRepository;
import com.paymybuddy.ewallet.utils.TransactionUtil;

@Service
public class TransactionService implements ITransactionService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private TransactionUtil transactionUtil;

	public List<Transaction> getTransactions() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findAll());
	}

	public List<Transaction> getTransactions_orderByDateAsc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findByOrderByDateAsc());
	}

	public List<Transaction> getTransactions_orderByDateDesc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findByOrderByDateDesc());
	}

	public List<Transaction> getTransactions_orderByAmountAsc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findByOrderByAmountAsc());
	}

	public List<Transaction> getTransactions_orderByAmountDesc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findByOrderByAmountDesc());
	}
	
	public List<Transaction> getTransactions_orderBySenderNameAsc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.getTransactions_orderBySenderNameAsc());
	}

	public List<Transaction> getTransactions_orderBySenderNameDesc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.getTransactions_orderBySenderNameDesc());
	}

	public List<Transaction> getTransactions_orderByReceiverNameAsc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.getTransactions_orderByReceiverNameAsc());
	}

	public List<Transaction> getTransactions_orderByReceiverNameDesc() {
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.getTransactions_orderByReceiverNameDesc());
	}

	// TODO: USELESS ?
	public Transaction getTransactionById(int id) {
		if(transactionRepository.findById(id).isPresent()) {
			return transactionRepository.findById(id).get();
		}
		
		return null;
	}
	
	public List<Transaction> getTransactionsByUser(int userId) {
		if(userRepository.findById(userId).isPresent()) {
			User user = userRepository.findById(userId).get();
			return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByIdDesc(user, user));
		}
		
		return null;
	}
	
	public List<Transaction> getTransactionsByUser_orderByDateAsc(int userId) {
		if(userRepository.findById(userId).isPresent()) {
			User user = userRepository.findById(userId).get();
			return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByDateAsc(user, user));
		}
		
		return null;
	}
	
	public List<Transaction> getTransactionsByUser_orderByDateDesc(int userId) {
		if(userRepository.findById(userId).isPresent()) {
			User user = userRepository.findById(userId).get();
			return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByDateDesc(user, user));
		}
		
		return null;
	}
	
	public List<Transaction> getTransactionsByUser_orderByAmountAsc(int userId) {
		if(userRepository.findById(userId).isPresent()) {
			User user = userRepository.findById(userId).get();
			return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByAmountAsc(user, user));
		}
		
		return null;
	}
	
	public List<Transaction> getTransactionsByUser_orderByAmountDesc(int userId) {
		if(userRepository.findById(userId).isPresent()) {
			User user = userRepository.findById(userId).get();
			return transactionUtil.filterTransactionsOfEwalletHistory(transactionRepository.findBySenderOrReceiverOrderByAmountDesc(user, user));
		}
		
		return null;
	}
	
	public List<Transaction> getTransactionsByUser_orderByBuddyNameAsc(int userId) {
		List<Transaction> transactionsByUserOrderByBuddyNameDesc = new ArrayList<>();
		TreeMap<String, Integer> buddies = transactionUtil.getBuddiesNameByTransactionByUser_orderByBuddyNameAsc(userId); 
		
		Set<String> keys = buddies.keySet();
		for(String key : keys) { 
			transactionsByUserOrderByBuddyNameDesc.addAll(transactionRepository.getTransactionsBetweenUsers_orderByDateDesc(userId, buddies.get(key)));
		}
		
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionsByUserOrderByBuddyNameDesc);
	}

	public List<Transaction> getTransactionsByUser_orderByBuddyNameDesc(int userId) {
		List<Transaction> transactionsByUserOrderByBuddyNameAsc = new ArrayList<>();
		TreeMap<String, Integer> buddies = transactionUtil.getBuddiesNameByTransactionByUser_orderByBuddyNameAsc(userId);
		
		Set<String> keys = buddies.descendingKeySet();
		for(String key : keys) { 
			transactionsByUserOrderByBuddyNameAsc.addAll(transactionRepository.getTransactionsBetweenUsers_orderByDateDesc(userId, buddies.get(key)));
		}
		
		return transactionUtil.filterTransactionsOfEwalletHistory(transactionsByUserOrderByBuddyNameAsc);
	}
	
	public List<Transaction> getTransactionsFromEwallet(int userId) {
		if(userRepository.findById(userId).isPresent()) {
			User user = userRepository.findById(userId).get();
			return transactionRepository.findBySenderAndReceiverOrderByIdDesc(user, user);
		}
		
		return null;
	}
	
	public List<Transaction> getTransactionsBetweenUsers(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers(firstUserId, secondUserId);
	}
	
	public List<Transaction> getTransactionsBetweenUsers_orderByDateAsc(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers_orderByDateAsc(firstUserId, secondUserId);
	}
	
	public List<Transaction> getTransactionsBetweenUsers_orderByDateDesc(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers_orderByDateDesc(firstUserId, secondUserId);
	}
	
	public List<Transaction> getTransactionsBetweenUsers_orderByAmountAsc(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers_orderByAmountAsc(firstUserId, secondUserId);
	}
	
	public List<Transaction> getTransactionsBetweenUsers_orderByAmountDesc(int firstUserId, int secondUserId) {
		return transactionRepository.getTransactionsBetweenUsers_orderByAmountDesc(firstUserId, secondUserId);
	}
	
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
	
	public Transaction updateTransactionById(Transaction transaction) {
		if(transactionRepository.findById(transaction.getId()).isPresent()) {
			Transaction transactionToUpdate = transactionRepository.findById(transaction.getId()).get();
			
			transactionToUpdate.setDescription(transaction.getDescription());
			return transactionRepository.save(transactionToUpdate);
		}
		
		logger.warn("This transaction doesn't exist.");
		return null;
	}
	
	public void deleteTransaction(Transaction transaction) {
		transactionRepository.delete(transaction);
	}
}
