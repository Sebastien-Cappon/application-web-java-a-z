package com.paymybuddy.ewallet.service;

import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.User;

/**
 * <code>UserService</code> interface that abstracts it from its implementation
 * in order to achieve better code modularity in compliance with SOLID
 * principles.
 *
 * @singularity updateAmount(int userId, double amount) don't own a controller.
 *              It's only called from Service.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IUserService {

	public User getUserById(int userId);
	
	public User postUserByEmailAndPassword(UserLoginDto userLoginDto) throws Exception;
	public User addUser(User user) throws Exception;
	
	public Integer updateProfile(int userId, UserProfileDto userProfileDto) throws Exception;
	public Integer updateActive(int userId, boolean isActive) throws Exception;
	
	public Integer updateAmount(int userId, double amount) throws Exception;
}