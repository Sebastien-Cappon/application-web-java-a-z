package com.paymybuddy.ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.ewallet.dto.EwalletTransactionAddDto;
import com.paymybuddy.ewallet.dto.TransactionAddDto;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.service.ITransactionService;
import com.paymybuddy.ewallet.views.TransactionView;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Transactions. The <code>@CrossOrigin</code> annotation
 * limits the acceptance of requests to those sent from the front-end.
 * 
 * @singularity Some responses are filtered using JsonView to match the response
 *              pattern desired by the front-end.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class TransactionController {
	
	@Autowired
	private ITransactionService iTransactionService;
	
	/**
	 * A <code>GetMapping</code> method on the <code>/transactions</code> URI with
	 * two optional <code>RequestParams</code> for sorting and ordering the lists
	 * returned. It calls several <code>ITransactionService</code> methods depending
	 * on the values of the parameters passed and returns a list of
	 * <code>Transaction</code> model entities.
	 * 
	 * @frontCall <code>admin.service.ts</code> : <code>getTransactions()</code>,
	 *            <code>getTransaction_orderByDateDesc</code>, <code>getTransaction_orderByDateAsc</code>,
	 *            <code>getTransaction_orderByAmountDesc</code>, <code>getTransaction_orderByAmountAsc</code>
	 *            <code>getTransaction_orderBySenderDesc</code>, <code>getTransaction_orderBySenderAsc</code>,
	 *            <code>getTransaction_orderByReceiverDesc</code>, <code>getTransaction_orderByReceiverAsc</code>,
	 * 
	 * @view Display date, amount, fee, description attributes, plus sender and
	 *       receiver id, firstname, lastname attributes, for the involved
	 *       <code>Transaction</code> model entities.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@JsonView(TransactionView.TransactionSimpleView.class)
	@GetMapping("/transactions")
	public List<Transaction> getTransactions(@RequestParam(defaultValue="none") String sortBy, @RequestParam(defaultValue="asc") String order) {
		switch(sortBy) {
			case "date" :
				if(order.contains("asc")) { return iTransactionService.getTransactions_orderByDateAsc(); }
				return iTransactionService.getTransactions_orderByDateDesc();
			case "amount" :
				if(order.contains("asc")) { return iTransactionService.getTransactions_orderByAmountAsc(); }
				return iTransactionService.getTransactions_orderByAmountDesc();
			case "sender" :
				if(order.contains("asc")) { return iTransactionService.getTransactions_orderBySenderNameAsc(); }
				return iTransactionService.getTransactions_orderBySenderNameDesc();
			case "receiver" :
				if(order.contains("asc")) { return iTransactionService.getTransactions_orderByReceiverNameAsc(); }
				return iTransactionService.getTransactions_orderByReceiverNameDesc();
			default :
				return iTransactionService.getTransactions();
		}
	}
	
	/**
	 * A <code>GetMapping</code> method on the <code>/history</code> URI with an
	 * user id as <code>PathVariable</code> and two optional <code>RequestParams</code>
	 * for sorting and ordering the lists returned. It calls several
	 * <code>ITransactionService</code> methods depending on the values of the
	 * parameters passed and returns a list of <code>Transaction</code> model
	 * entities.
	 * 
	 * @frontCall <code>transactions.service.ts</code> : <code>getHistory()</code>,
	 *            <code>getHistory_orderByDateDesc</code>, <code>getHistory_orderByDateAsc</code>,
	 *            <code>getHistory_orderByBuddyDesc</code>, <code>getHistory_orderByBuddyAsc</code>,
	 *            <code>getHistory_orderByAmountDesc</code>, <code>getHistory_orderByAmountAsc</code>
	 * 
	 * @view Display date, amount, fee, description attributes, plus sender and
	 *       receiver id, firstname, lastname attributes, for the involved
	 *       <code>Transaction</code> model entities.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@JsonView(TransactionView.TransactionSimpleView.class)
	@GetMapping("/history/{id}")
	public List<Transaction> getTransactionsByUser(@PathVariable("id") int userId, @RequestParam(defaultValue="none") String sortBy, @RequestParam(defaultValue="asc") String order) {
		switch(sortBy) {
			case "date" :
				if(order.contains("asc")) { return iTransactionService.getTransactionsByUser_orderByDateAsc(userId); }
				return iTransactionService.getTransactionsByUser_orderByDateDesc(userId);
			case "amount" :
				if(order.contains("asc")) { return iTransactionService.getTransactionsByUser_orderByAmountAsc(userId); }
				return iTransactionService.getTransactionsByUser_orderByAmountDesc(userId);
			case "buddy" :
				if(order.contains("asc")) { return iTransactionService.getTransactionsByUser_orderByBuddyNameAsc(userId); }
				return iTransactionService.getTransactionsByUser_orderByBuddyNameDesc(userId);
			default :
				return iTransactionService.getTransactionsByUser(userId);
		}
	}
	
	/**
	 * A <code>GetMapping</code> method on the <code>/history/ewallet</code> URI
	 * with an user id as <code>PathVariable</code>. It calls the eponymous
	 * <code>ITransactionService</code> methods and returns a list of
	 * <code>Transaction</code> model entities.
	 * 
	 * @frontCall <code>transactions.service.ts</code> : <code>getEwalletHistory()</code>
	 * 
	 * @view Display date, amount, fee, description attributes, plus sender and
	 *       receiver id, firstname, lastname attributes, for the involved
	 *       <code>Transaction</code> model entities.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@JsonView(TransactionView.TransactionSimpleView.class)
	@GetMapping("/history/ewallet/{id}")
	public List<Transaction> getTransactionsFromEwallet(@PathVariable("id") int userId) {
		return iTransactionService.getTransactionsFromEwallet(userId);
	}
	
	/**
	 * A <code>GetMapping</code> method on the <code>/history/between</code> URI with two
	 * user id as <code>PathVariable</code> and two optional <code>RequestParams</code>
	 * for sorting and ordering the lists returned. It calls several
	 * <code>ITransactionService</code> methods depending on the values of the
	 * parameters passed and returns a list of <code>Transaction</code> model
	 * entities.
	 * 
	 * @frontCall <code>transactions.service.ts</code> : <code>getHistoryBetween()</code>,
	 *            <code>getHistoryBetween_orderByDateDesc</code>, <code>getHistoryBetween_orderByDateAsc</code>,
	 *            <code>getHistoryBetween_orderByAmountDesc</code>, <code>getHistoryBetween_orderByAmountAsc</code>
	 * 
	 * @view Display date, amount, fee, description attributes, plus sender and
	 *       receiver id, firstname, lastname attributes, for the involved
	 *       <code>Transaction</code> model entities.
	 * 
	 * @return A <code>Transaction</code> list.
	 */
	@JsonView(TransactionView.TransactionSimpleView.class)
	@GetMapping("/history/between/{userId}-{buddyId}")
	public List<Transaction> getTransactionsBetweenUsers(@PathVariable("userId") int firstUserId, @PathVariable("buddyId") int secondUserId, @RequestParam(defaultValue="none") String sortBy, @RequestParam(defaultValue="asc") String order) {
		switch(sortBy) {
			case "date" :
				if(order.contains("asc")) { return iTransactionService.getTransactionsBetweenUsers_orderByDateAsc(firstUserId, secondUserId); }
				return iTransactionService.getTransactionsBetweenUsers_orderByDateDesc(firstUserId, secondUserId);
			case "amount" :
				if(order.contains("asc")) { return iTransactionService.getTransactionsBetweenUsers_orderByAmountAsc(firstUserId, secondUserId); }
				return iTransactionService.getTransactionsBetweenUsers_orderByAmountDesc(firstUserId, secondUserId);
			default :
				return iTransactionService.getTransactionsBetweenUsers(firstUserId, secondUserId);
		}
	}
	
	/**
	 * A <code>PostMapping</code> method on the <code>/ewallet</code> URI with a
	 * DTO, consisting of the active user id and the amount of the e-wallet credit
	 * or debit, as <code>RequestBody</code>. It calls the eponymous
	 * <code>ITransactionService</code> method and returns the
	 * <code>Transaction</code> added with status code 201. If the result is null,
	 * it returns status code 204.
	 * 
	 * @frontCall <code>ewallet.service.ts</code> : <code>homeEwalletTransafer(ewalletTransferValue: EwalletTransferValue)</code>
	 * 
	 * @view Display date, amount, fee, description attributes, plus sender and
	 *       receiver id, firstname, lastname attributes, for the involved
	 *       <code>Transaction</code> model entities.
	 * 
	 * @throws <code>BAD_REQUEST</code> if the user concerned doesn't exist.
	 * 
	 * @return A <code>Transaction</code> and a status code.
	 */
	@JsonView(TransactionView.TransactionSimpleView.class)
	@PostMapping("/ewallet")
	public ResponseEntity<Transaction> addEwalletTransaction(@RequestBody EwalletTransactionAddDto ewalletTransactionAddDto) throws Exception {
		Transaction createdTransaction = iTransactionService.addEwalletTransaction(ewalletTransactionAddDto);

		if (createdTransaction == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Transaction>(createdTransaction, HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/transaction</code> URI with a
	 * DTO, consisting of the active user id, the receiver (as DTO) the amount and
	 * the comment of the transaction, as <code>RequestBody</code>. It calls the
	 * eponymous <code>ITransactionService</code> method and returns the
	 * <code>Transaction</code> added with status code 201. If the result is null,
	 * it returns status code 204.
	 * 
	 * @frontCall <code>transfer.service.ts</code> : <code>payMyBuddy(transferValue: TransferValue)</code>
	 * 
	 * @view Display date, amount, fee, description attributes, plus sender and
	 *       receiver id, firstname, lastname attributes, for the involved
	 *       <code>Transaction</code> model entities.
	 * 
	 * @throws <code>BAD_REQUEST</code> if the users concerned doesn't exist or if
	 * the two users are the same user.
	 * 
	 * @return A <code>Transaction</code> and a status code.
	 */
	@JsonView(TransactionView.TransactionSimpleView.class)
	@PostMapping("/transaction")
	public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionAddDto transactionAddDto) throws Exception {
		Transaction createdTransaction = iTransactionService.addTransaction(transactionAddDto);

		if (createdTransaction == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Transaction>(createdTransaction, HttpStatus.CREATED);
		}
	}
}