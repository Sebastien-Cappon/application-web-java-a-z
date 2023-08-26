package com.paymybuddy.ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.ITransactionService;

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
	
	@GetMapping("/history")
	@ResponseBody
	public List<Transaction> getTransactionsBySender(@RequestBody User sender, @RequestParam(defaultValue="none") String sortBy, @RequestParam(defaultValue="asc") String order) {
		switch(sortBy) {
			case "date" :
				if(order.contains("asc")) { return iTransactionService.getTransactionsBySender_orderByDateAsc(sender); }
				return iTransactionService.getTransactionsBySender_orderByDateDesc(sender);
			case "amount" :
				if(order.contains("asc")) { return iTransactionService.getTransactionsBySender_orderByAmountAsc(sender); }
				return iTransactionService.getTransactionsBySender_orderByAmountDesc(sender);
			case "receiver" :
				if(order.contains("asc")) { return iTransactionService.getTransactionsBySender_orderByReceiverNameAsc(sender); }
				return iTransactionService.getTransactionsBySender_orderByReceiverNameDesc(sender);
			default :
				return iTransactionService.getTransactionsBySender(sender);
		}
	}
	
	@PostMapping("/transaction")
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) throws Exception {
		Transaction createdTransaction = iTransactionService.addTransaction(transaction); 
		
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
