package com.paymybuddy.ewallet.service;

import java.util.List;

import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;

public interface ITransactionService {

	public List<Transaction> getTransactions();
	public List<Transaction> getTransactions_orderByDateAsc();
	public List<Transaction> getTransactions_orderByDateDesc();
	public List<Transaction> getTransactions_orderByAmountAsc();
	public List<Transaction> getTransactions_orderByAmountDesc();
	public List<Transaction> getTransactions_orderByReceiverNameAsc();
	public List<Transaction> getTransactions_orderByReceiverNameDesc();
	
	public Transaction getTransactionById(int id);
	
	public List<Transaction> getTransactionsBySender(User sender);
	public List<Transaction> getTransactionsBySender_orderByDateAsc(User sender);
	public List<Transaction> getTransactionsBySender_orderByDateDesc(User sender);
	public List<Transaction> getTransactionsBySender_orderByAmountAsc(User sender);
	public List<Transaction> getTransactionsBySender_orderByAmountDesc(User sender);
	public List<Transaction> getTransactionsBySender_orderByReceiverNameAsc(User sender);
	public List<Transaction> getTransactionsBySender_orderByReceiverNameDesc(User sender);
	
	public Transaction addTransaction(Transaction transaction) throws Exception;
	public Transaction updateTransactionById(Transaction transaction);
	public void deleteTransaction(Transaction transaction);
}
