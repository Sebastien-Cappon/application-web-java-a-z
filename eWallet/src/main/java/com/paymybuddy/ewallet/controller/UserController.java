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
import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.IUserService;
import com.paymybuddy.ewallet.views.UserView;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

	@Autowired
	private IUserService iUserService;

	@GetMapping("/users")
	@ResponseBody
	public List<User> getUsers(@RequestParam(defaultValue = "none") String sortBy, @RequestParam(defaultValue = "asc") String order) {
		switch (sortBy) {
		case "name":
			if (order.contains("asc")) { return iUserService.getUsers_orderByNameAsc(); }
			return iUserService.getUsers_orderByNameDesc();
		case "email":
			if (order.contains("asc")) { return iUserService.getUsers_orderByEmailAsc(); }
			return iUserService.getUsers_orderByEmailDesc();
		case "amount":
			if (order.contains("asc")) { return iUserService.getUsers_orderByAmountAsc(); }
			return iUserService.getUsers_orderByAmountDesc();
		default:
			return iUserService.getUsers();
		}
	}

	@JsonView(UserView.LoginView.class)
	@GetMapping("/users/login/{id}")
	@ResponseBody
	public User getUserById_forLoginPage(@PathVariable("id") int userId) {
		return iUserService.getUserById(userId);
	}

	@JsonView(UserView.ProfileView.class)
	@GetMapping("/users/profile/{id}")
	@ResponseBody
	public User getUserById_forProfilePage(@PathVariable("id") int userId) {
		return iUserService.getUserById(userId);
	}


	@JsonView(UserView.LoginView.class)
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<User> postUserByEmailAndPassword(@RequestBody UserLoginDto userLoginDto) throws Exception {
		User loggedUser = iUserService.postUserByEmailAndPassword(userLoginDto);
		
		if(loggedUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(loggedUser, HttpStatus.OK);
		}
	}

	@PostMapping("/user")
	@ResponseBody
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
		User createdUser = iUserService.addUser(user);
		
		if(createdUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
		}
	}

	@PutMapping("/users/{id}/profile")
	@ResponseBody
	public ResponseEntity<Integer> updateProfile(@PathVariable("id") int userId, @RequestBody UserProfileDto userProfileDto) throws Exception {
		Integer updatedUser = iUserService.updateProfile(userId, userProfileDto);  
		
		if(updatedUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		} 
	}
	
	@PutMapping("/users/{id}/active")
	@ResponseBody
	public ResponseEntity<Integer> updateActive(@PathVariable("id") int userId, @RequestBody boolean isActive) throws Exception {
		Integer updatedUser = iUserService.updateActive(userId, isActive);  
		
		if(updatedUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		} 
	}
	
	@PutMapping("/users/{id}/amount")
	@ResponseBody
	public ResponseEntity<Integer> updateAmount(@PathVariable("id") int userId, @RequestBody double amount) throws Exception {
		Integer updatedUser = iUserService.updateAmount(userId, amount);  
		
		if(updatedUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		} 
	}

	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") int userId) {
		iUserService.deleteUserById(userId);
	}
}
