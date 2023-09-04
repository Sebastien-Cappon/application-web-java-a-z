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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.IBuddyService;
import com.paymybuddy.ewallet.views.UserView;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class BuddyController {

	@Autowired
	private IBuddyService iBuddyService;
	
	@GetMapping("/buddies")
	public List<Buddy> getBuddies() {
		return iBuddyService.getBuddies();
	}
	
	@JsonView(UserView.BuddyView.class)
	@GetMapping("/mybuddies/{id}")
	@ResponseBody
	public List<User> getBuddiesByUser(@PathVariable("id") int userId) {
		return iBuddyService.getBuddiesByUser(userId);
	}
	
	@JsonView(UserView.BuddyView.class)
	@PostMapping("/buddy")
	@ResponseBody
	public ResponseEntity<User> addBuddy(@RequestBody BuddyAddDto buddyAddDto) {
		User myNewBuddy = iBuddyService.addBuddy(buddyAddDto);
		
		if(myNewBuddy == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(myNewBuddy, HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/buddy/{firstUserId}-{secondUserId}")
	public void deleteBuddy(@PathVariable("firstUserId") int firstUserId, @PathVariable("secondUserId") int secondUserId) {
		iBuddyService.deleteBuddy(firstUserId, secondUserId);
	}
	
	@DeleteMapping("/mybuddies")
	public void deleteBuddiesByUser(@RequestBody User user) {
		iBuddyService.deleteBuddiesByUser(user);
	}
}
