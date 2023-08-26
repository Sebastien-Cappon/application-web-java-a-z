package com.paymybuddy.ewallet.service;

import java.util.List;

import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.User;

public interface IUserService {

	public List<User> getUsers();
	public List<User> getUsers_orderByNameAsc();
	public List<User> getUsers_orderByNameDesc();
	public List<User> getUsers_orderByEmailAsc();
	public List<User> getUsers_orderByEmailDesc();
	public List<User> getUsers_orderByAmountAsc();
	public List<User> getUsers_orderByAmountDesc();
	
	public User getUserById(int userId);
	public User postUserByEmailAndPassword(UserLoginDto userLoginDto) throws Exception;
	
	public User addUser(User user) throws Exception;
	public Integer updateProfile(int userId, UserProfileDto userProfileDto) throws Exception;
	public Integer updateActive(int userId, boolean isActive) throws Exception;
	public Integer updateAmount(int userId, double amount) throws Exception;
	public void deleteUserById(int userId);
}
