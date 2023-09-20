package com.paymybuddy.ewallet.service;

import java.util.List;

import com.paymybuddy.ewallet.dto.EwalletTransactionAddDto;
import com.paymybuddy.ewallet.dto.TransactionAddDto;
import com.paymybuddy.ewallet.model.Transaction;

public interface ITransactionService {

	public List<Transaction> getTransactions();
	public List<Transaction> getTransactions_orderByDateAsc();
	public List<Transaction> getTransactions_orderByDateDesc();
	public List<Transaction> getTransactions_orderByAmountAsc();
	public List<Transaction> getTransactions_orderByAmountDesc();
	public List<Transaction> getTransactions_orderBySenderNameAsc();
	public List<Transaction> getTransactions_orderBySenderNameDesc();
	public List<Transaction> getTransactions_orderByReceiverNameAsc();
	public List<Transaction> getTransactions_orderByReceiverNameDesc();
	
	public Transaction getTransactionById(int id);
	
	public List<Transaction> getTransactionsByUser(int userId);
	public List<Transaction> getTransactionsByUser_orderByDateAsc(int userId);
	public List<Transaction> getTransactionsByUser_orderByDateDesc(int userId);
	public List<Transaction> getTransactionsByUser_orderByAmountAsc(int userId);
	public List<Transaction> getTransactionsByUser_orderByAmountDesc(int userId);
	public List<Transaction> getTransactionsByUser_orderByBuddyNameAsc(int userId);
	public List<Transaction> getTransactionsByUser_orderByBuddyNameDesc(int userId);
	
	public List<Transaction> getTransactionsFromEwallet(int userId);
	
	public List<Transaction> getTransactionsBetweenUsers(int firstUserId, int secondUserId);
	public List<Transaction> getTransactionsBetweenUsers_orderByDateAsc(int firstUserId, int secondUserId);
	public List<Transaction> getTransactionsBetweenUsers_orderByDateDesc(int firstUserId, int secondUserId);
	public List<Transaction> getTransactionsBetweenUsers_orderByAmountAsc(int firstUserId, int secondUserId);
	public List<Transaction> getTransactionsBetweenUsers_orderByAmountDesc(int firstUserId, int secondUserId);
	
	public Transaction addEwalletTransaction(EwalletTransactionAddDto ewalletTransactionAddDto) throws Exception;
	public Transaction addTransaction(TransactionAddDto transactionAddDto) throws Exception;
	public Transaction updateTransactionById(Transaction transaction);
	public void deleteTransaction(Transaction transaction);
}
