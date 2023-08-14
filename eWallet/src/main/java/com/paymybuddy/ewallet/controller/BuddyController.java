package com.paymybuddy.ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.IBuddyService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class BuddyController {

	@Autowired
	private IBuddyService iBuddyService;
	
	@GetMapping("/buddies")
	public List<Buddy> getBuddies() {
		return iBuddyService.getBuddies();
	}
	
	@GetMapping("/mybuddies")
	@ResponseBody
	public List<Buddy> getBuddiesByUser(@RequestBody User user) {
		return iBuddyService.getBuddiesByUser(user);
	}
	
	@PostMapping("/buddy")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Buddy addBuddy(@RequestBody Buddy buddy) {
		return iBuddyService.addBuddy(buddy);
	}
	
	@DeleteMapping("/buddy")
	public void deleteBuddy(@RequestBody Buddy buddy) {
		iBuddyService.deleteBuddy(buddy);
	}
	
	@DeleteMapping("/mybuddies")
	public void deleteBuddiesByUser(@RequestBody User user) {
		iBuddyService.deleteBuddiesByUser(user);
	}
}
