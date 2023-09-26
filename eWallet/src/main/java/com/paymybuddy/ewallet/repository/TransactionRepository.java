package com.paymybuddy.ewallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.paymybuddy.ewallet.constants.MySqlQueries;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;

import jakarta.transaction.Transactional;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with Derived and JPQL query relative to
 * <code>Transaction</code> entities.
 * 
 * @singularity Method <code>findBySender</code> is created for integration
 *              tests purpose only. That's why it's Optional, not List. We're
 *              sure that only one entity will be returned.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	Optional<Transaction> findBySender(User sender);
	
	List<Transaction> findByOrderByDateAsc();
	List<Transaction> findByOrderByDateDesc();
	List<Transaction> findByOrderByAmountAsc();
	List<Transaction> findByOrderByAmountDesc();
	@Query(value = MySqlQueries.allTransaction_orderBySenderNameAsc, nativeQuery = true)
	List<Transaction> getTransactions_orderBySenderNameAsc();
	@Query(value = MySqlQueries.allTransaction_orderBySenderNameDesc, nativeQuery = true)
	List<Transaction> getTransactions_orderBySenderNameDesc();
	@Query(value = MySqlQueries.allTransaction_orderByReceiverNameAsc, nativeQuery = true)
	List<Transaction> getTransactions_orderByReceiverNameAsc();
	@Query(value = MySqlQueries.allTransaction_orderByReceiverNameDesc, nativeQuery = true)
	List<Transaction> getTransactions_orderByReceiverNameDesc();

	List<Transaction> findBySenderOrReceiverOrderByIdDesc(User firstUser, User secondUser);
	List<Transaction> findBySenderOrReceiverOrderByDateAsc(User firstUser, User secondUser);
	List<Transaction> findBySenderOrReceiverOrderByDateDesc(User firstUser, User secondUser);
	List<Transaction> findBySenderOrReceiverOrderByAmountAsc(User firstUser, User secondUser);
	List<Transaction> findBySenderOrReceiverOrderByAmountDesc(User firstUser, User secondUser);
	
	List<Transaction> findBySenderAndReceiverOrderByIdDesc(User sender, User receiver);
	
	@Query(value = MySqlQueries.allTransactionBySenderAndReceiver, nativeQuery = true)
	List<Transaction> getTransactionsBetweenUsers(int firstUserId, int secondUserId);
	@Query(value = MySqlQueries.allTransactionBySenderAndReceiver_orderByDateAsc, nativeQuery = true)
	List<Transaction> getTransactionsBetweenUsers_orderByDateAsc(int firstUserId, int secondUserId);
	@Query(value = MySqlQueries.allTransactionBySenderAndReceiver_orderByDateDesc, nativeQuery = true)
	List<Transaction> getTransactionsBetweenUsers_orderByDateDesc(int firstUserId, int secondUserId);
	@Query(value = MySqlQueries.allTransactionBySenderAndReceiver_orderByAmountAsc, nativeQuery = true)
	List<Transaction> getTransactionsBetweenUsers_orderByAmountAsc(int firstUserId, int secondUserId);
	@Query(value = MySqlQueries.allTransactionBySenderAndReceiver_orderByAmountDesc, nativeQuery = true)
	List<Transaction> getTransactionsBetweenUsers_orderByAmountDesc(int firstUserId, int secondUserId);

	@Transactional
	@Modifying
	@Query(value = MySqlQueries.updateTransactionById, nativeQuery = true)
	void updateTransactionById(int id, String description);
}