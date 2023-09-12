package com.paymybuddy.ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.ewallet.dto.TransactionAddDto;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.service.ITransactionService;
import com.paymybuddy.ewallet.views.TransactionView;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class TransactionController {
	
	@Autowired
	private ITransactionService iTransactionService;
	
	@GetMapping("/transactions")
	@ResponseBody
	public List<Transaction> getTransactions(@RequestParam(defaultValue="none") String sortBy, @RequestParam(defaultValue="asc") String order) {
		switch(sortBy) {
			case "date" :
				if(order.contains("asc")) { return iTransactionService.getTransactions_orderByDateAsc(); }
				return iTransactionService.getTransactions_orderByDateDesc();
			case "amount" :
				if(order.contains("asc")) { return iTransactionService.getTransactions_orderByAmountAsc(); }
				return iTransactionService.getTransactions_orderByAmountDesc();
			case "receiver" :
				if(order.contains("asc")) { return iTransactionService.getTransactions_orderByReceiverNameAsc(); }
				return iTransactionService.getTransactions_orderByReceiverNameDesc();
			default :
				return iTransactionService.getTransactions();
		}
	}
	
	@GetMapping("/transaction")
	@ResponseBody
	public Transaction getTransactionById(@RequestParam int id) {
		return iTransactionService.getTransactionById(id);
	}
	
	@GetMapping("/history/{id}")
	@ResponseBody
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
	
	@GetMapping("/history/between/{userId}-{buddyId}")
	@ResponseBody
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
	
	@JsonView(TransactionView.AddTransactionView.class)
	@PostMapping("/transaction")
	public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionAddDto transactionAddDto) throws Exception {
		Transaction createdTransaction = iTransactionService.addTransaction(transactionAddDto); 
		
		if(createdTransaction == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Transaction>(createdTransaction, HttpStatus.CREATED);
		}
	}
	
	@PutMapping("/transaction")
	public ResponseEntity<Transaction> updateTransactionById(@RequestBody Transaction transaction) {
		Transaction updatedTransaction = iTransactionService.updateTransactionById(transaction);
		
		if(updatedTransaction == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Transaction>(updatedTransaction, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("transaction")
	public void deleteTransaction(@RequestBody Transaction transaction) {
		iTransactionService.deleteTransaction(transaction);
	}
}
