package com.paymybuddy.ewallet.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.UserRepository;
import com.paymybuddy.ewallet.utils.PasswordManager;

@Service
public class UserService implements IUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordManager passwordManager;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public List<User> getUsers_orderByNameAsc() {
		return userRepository.getUsers_orderByNameAsc();
	}

	public List<User> getUsers_orderByNameDesc() {
		return userRepository.getUsers_orderByNameDesc();
	}

	public List<User> getUsers_orderByEmailAsc() {
		return userRepository.findByOrderByEmailAsc();
	}

	public List<User> getUsers_orderByEmailDesc() {
		return userRepository.findByOrderByEmailDesc();
	}

	public List<User> getUsers_orderByAmountAsc() {
		return userRepository.findByOrderByAmountAsc();
	}

	public List<User> getUsers_orderByAmountDesc() {
		return userRepository.findByOrderByAmountDesc();
	}

	public User getUserById(int userId) {
		if (userRepository.findById(userId).isPresent()) {
			User userResponse = userRepository.findById(userId).get();
			return userResponse;
		}

		return null;
	}
	
	public User postUserByEmailAndPassword(UserLoginDto userLoginDto) throws Exception {
		if (userRepository.findByEmail(userLoginDto.getEmail()).isPresent()) {
			User user = userRepository.findByEmail(userLoginDto.getEmail()).get();
			
			if(user.isActive() && !user.isSocial()) {
				String inputPassword = userLoginDto.getPassword();
				String userPassword = user.getPassword();
	
				if (passwordManager.checkPassword(inputPassword, userPassword)) {
					return user;
				}
			}
		}

		return null;
	}

	public User addUser(User user) throws Exception {
		if(user != null && user.getFirstname() != null && user.getLastname() != null && user.getEmail() != null && (user.isSocial() || user.getPassword() != null)) {
			for (User checkUser : this.getUsers()) {
				if (checkUser.getEmail().contentEquals(user.getEmail())) {
					logger.warn("A user with this email address already exists.");
					return null;
				}
			}
	
			if (!user.isSocial()) {
				String hashedPassword = passwordManager.hashPassword(user.getPassword());
				user.setPassword(hashedPassword);
			} else {
				user.setPassword(null);
			}
	
			return userRepository.save(user);
		}
		
		return null;
	}
	
	public Integer updateProfile(int userId, UserProfileDto userProfileDto) throws Exception {
		if(userRepository.findById(userId).isPresent()) {
			User userToUpdate = userRepository.findById(userId).get();
			
			if (userProfileDto.getFirstname() == null || userProfileDto.getFirstname().isBlank()) {
				userProfileDto.setFirstname(userToUpdate.getFirstname());
			}
			if (userProfileDto.getLastname() == null || userProfileDto.getLastname().isBlank()) {
				userProfileDto.setLastname(userToUpdate.getLastname());
			}
			if (userProfileDto.getEmail() == null || userProfileDto.getEmail().isBlank()) {
				userProfileDto.setEmail(userToUpdate.getEmail());
			} else {
				for (User checkUser : this.getUsers()) {
					if (checkUser.getEmail().contentEquals(userProfileDto.getEmail())) {
						logger.warn("This email address is already used.");
						userProfileDto.setEmail(userToUpdate.getEmail());
						break;
					}
				}
			}
			if (userProfileDto.getPassword() == null || userProfileDto.getPassword().isBlank()) {
				userProfileDto.setPassword(userToUpdate.getPassword());
			} else {
				userProfileDto.setPassword(passwordManager.hashPassword(userProfileDto.getPassword()));
			}

			userRepository.updateProfile(userId, userProfileDto.getFirstname(), userProfileDto.getLastname(), userProfileDto.getEmail(), userProfileDto.getPassword());
			return 1;
		}

		logger.warn("This user doesn't exist.");
		return null;
	}
	
	public Integer updateActive(int userId, boolean isActive) throws Exception {
		if(userRepository.findById(userId).isPresent()) {
			userRepository.updateActive(userId, isActive);
			return 1;
		}

		logger.warn("This user doesn't exist.");
		return null;
	}
	
	public Integer updateAmount(int userId, double amount) throws Exception {
		if(userRepository.findById(userId).isPresent()) {
			userRepository.updateAmount(userId, amount);
			return 1;
		}

		logger.warn("This user doesn't exist.");
		return null;
	}

	public void deleteUserById(int userId) {
		User userToDelete = userRepository.findById(userId).get();
		
		if (userToDelete != null) {
			userRepository.delete(userToDelete);
		}
	}
}
