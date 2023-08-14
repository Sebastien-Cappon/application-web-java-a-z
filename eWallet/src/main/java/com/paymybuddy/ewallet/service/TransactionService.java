package com.paymybuddy.ewallet.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.TransactionRepository;

@Service
public class TransactionService implements ITransactionService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private IUserService iUserService;

	public List<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}

	public List<Transaction> getTransactions_orderByDateAsc() {
		return transactionRepository.findByOrderByDateAsc();
	}

	public List<Transaction> getTransactions_orderByDateDesc() {
		return transactionRepository.findByOrderByDateDesc();
	}

	public List<Transaction> getTransactions_orderByAmountAsc() {
		return transactionRepository.findByOrderByAmountAsc();
	}

	public List<Transaction> getTransactions_orderByAmountDesc() {
		return transactionRepository.findByOrderByAmountDesc();
	}

	public List<Transaction> getTransactions_orderByReceiverNameAsc() {
		return transactionRepository.getTransactions_orderByReceiverNameAsc();
	}

	public List<Transaction> getTransactions_orderByReceiverNameDesc() {
		return transactionRepository.getTransactions_orderByReceiverNameDesc();
	}

	public Transaction getTransactionById(int id) {
		if(transactionRepository.findById(id).isPresent()) {
			return transactionRepository.findById(id).get();
		}
		
		return null;
	}
	
	public List<Transaction> getTransactionsBySender(User sender) {
		return transactionRepository.findBySender(sender);
	}
	
	public List<Transaction> getTransactionsBySender_orderByDateAsc(User sender) {
		return transactionRepository.findBySenderOrderByDateAsc(sender);
	}
	
	public List<Transaction> getTransactionsBySender_orderByDateDesc(User sender) {
		return transactionRepository.findBySenderOrderByDateDesc(sender);
	}
	
	public List<Transaction> getTransactionsBySender_orderByAmountAsc(User sender) {
		return transactionRepository.findBySenderOrderByAmountAsc(sender);
	}
	
	public List<Transaction> getTransactionsBySender_orderByAmountDesc(User sender) {
		return transactionRepository.findBySenderOrderByAmountDesc(sender);
	}
	
	public List<Transaction> getTransactionsBySender_orderByReceiverNameAsc(User sender) {
		return transactionRepository.getTransactionsBySender_orderByReceiverNameAsc(sender.getId());
	}

	public List<Transaction> getTransactionsBySender_orderByReceiverNameDesc(User sender) {
		return transactionRepository.getTransactionsBySender_orderByReceiverNameDesc(sender.getId());
	}
	
	public Transaction addTransaction(Transaction transaction) throws Exception{
		double transactionAmount = transaction.getAmount();
		double transactionFee = Math.round(transactionAmount*0.5)/100.0;
		
		User sender = iUserService.getUserById(transaction.getSender().getId());
		sender.setAmount(sender.getAmount() - transactionAmount - transactionFee);
		iUserService.updateUser(sender);

		User receiver = iUserService.getUserById(transaction.getReceiver().getId());
		receiver.setAmount(receiver.getAmount() + transactionAmount);
		iUserService.updateUser(receiver);
		
		transaction.setDate(LocalDate.now());
		transaction.setSender(sender);
		transaction.setReceiver(receiver);
		transaction.setFee(transactionFee);
		
		return transactionRepository.save(transaction);
	}
	
	public Transaction updateTransactionById(Transaction transaction) {
		Transaction transactionToUpdate = this.getTransactionById(transaction.getId());
		
		if(transactionToUpdate != null) {
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
