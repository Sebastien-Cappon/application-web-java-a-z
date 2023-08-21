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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserLoginResponseDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.IUserService;

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

	@GetMapping("/user")
	@ResponseBody
	public User getUserById(@RequestParam int id) {
		return iUserService.getUserById(id);
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<UserLoginResponseDto> postUserByEmailAndPassword(@RequestBody UserLoginDto userLoginDto) throws Exception {
		UserLoginResponseDto loggedUser = iUserService.postUserByEmailAndPassword(userLoginDto);
		
		if(loggedUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<UserLoginResponseDto>(loggedUser, HttpStatus.OK);
		}
	}

	@PostMapping("/user")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public User addUser(@RequestBody User user) throws Exception {
		return iUserService.addUser(user);
	}

	@PutMapping("/user")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public User updateUser(@RequestBody User update) throws Exception {
		return iUserService.updateUser(update);
	}

	@DeleteMapping("/user")
	public void deleteUser(@RequestBody User user) {
		iUserService.deleteUser(user);
	}
}
