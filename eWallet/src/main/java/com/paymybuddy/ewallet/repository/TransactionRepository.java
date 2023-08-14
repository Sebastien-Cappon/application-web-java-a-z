package com.paymybuddy.ewallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.paymybuddy.ewallet.constants.MySqlQueries;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;

import jakarta.transaction.Transactional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByOrderByDateAsc();
	List<Transaction> findByOrderByDateDesc();
	List<Transaction> findByOrderByAmountAsc();
	List<Transaction> findByOrderByAmountDesc();
	@Query(value = MySqlQueries.allTransaction_orderByReceiverNameAsc, nativeQuery = true)
	List<Transaction> getTransactions_orderByReceiverNameAsc();
	@Query(value = MySqlQueries.allTransaction_orderByReceiverNameDesc, nativeQuery = true)
	List<Transaction> getTransactions_orderByReceiverNameDesc();

	List<Transaction> findBySender(User sender);
	List<Transaction> findBySenderOrderByDateAsc(User sender);
	List<Transaction> findBySenderOrderByDateDesc(User sender);
	List<Transaction> findBySenderOrderByAmountAsc(User sender);
	List<Transaction> findBySenderOrderByAmountDesc(User sender);
	@Query(value = MySqlQueries.allTransactionBySender_orderByReceiverNameAsc, nativeQuery = true)
	List<Transaction> getTransactionsBySender_orderByReceiverNameAsc(int senderId);
	@Query(value = MySqlQueries.allTransactionBySender_orderByReceiverNameDesc, nativeQuery = true)
	List<Transaction> getTransactionsBySender_orderByReceiverNameDesc(int senderId);

	@Transactional
	@Modifying
	@Query(value = MySqlQueries.updateTransactionById, nativeQuery = true)
	void updateTransactionById(int id, String description);
}
