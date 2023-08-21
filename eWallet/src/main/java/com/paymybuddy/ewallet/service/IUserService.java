package com.paymybuddy.ewallet.service;

import java.util.List;

import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserLoginResponseDto;
import com.paymybuddy.ewallet.model.User;

public interface IUserService {

	public List<User> getUsers();
	public List<User> getUsers_orderByNameAsc();
	public List<User> getUsers_orderByNameDesc();
	public List<User> getUsers_orderByEmailAsc();
	public List<User> getUsers_orderByEmailDesc();
	public List<User> getUsers_orderByAmountAsc();
	public List<User> getUsers_orderByAmountDesc();
	
	public User getUserById(int id);
	public UserLoginResponseDto postUserByEmailAndPassword(UserLoginDto userLoginDto) throws Exception;
	
	public User addUser(User user) throws Exception;	
	public User updateUser(User update) throws Exception;
	public void deleteUser(User user);
}
