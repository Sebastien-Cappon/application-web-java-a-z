package com.paymybuddy.ewallet.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public User getUserById(int id) {
		if (userRepository.findById(id).isPresent()) {
			return userRepository.findById(id).get();
		}

		return null;
	}

	public User getUserByEmailAndPassword(User userInput) throws Exception {
		if (userRepository.findByEmail(userInput.getEmail()).isPresent()) {
			User user = userRepository.findByEmail(userInput.getEmail()).get();
			String inputPassword = userInput.getPassword();
			String userPassword = user.getPassword();

			if (passwordManager.checkPassword(inputPassword, userPassword)) {
				return user;
			}
		}

		return null;
	}

	public User addUser(User user) throws Exception {
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

	public User updateUser(User update) throws Exception {
		User userToUpdate = this.getUserById(update.getId());

		if (userToUpdate != null) {
			update.setId(userToUpdate.getId());
			if (update.getFirstname() == null) { update.setFirstname(userToUpdate.getFirstname()); }
			if (update.getLastname() == null) { update.setLastname(userToUpdate.getLastname()); }
			if (update.isSocial() == false) { update.setSocial(userToUpdate.isSocial()); }
			if (update.getAmount() == 0) { update.setAmount(userToUpdate.getAmount()); }
			if (update.isActive() == false) { update.setActive(userToUpdate.isActive()); }

			if (update.getEmail() == null) {
				update.setEmail(userToUpdate.getEmail());
			} else {
				for (User checkUser : this.getUsers()) {
					if (checkUser.getEmail().contentEquals(update.getEmail())) {
						logger.warn("This email address is already used.");
						update.setEmail(userToUpdate.getEmail());
						break;
					}
				}
			}

			if (update.getPassword() == null) {
				update.setPassword(userToUpdate.getPassword());
			} else {
				update.setPassword(passwordManager.hashPassword(update.getPassword()));
			}

			return userRepository.save(update);
		}

		logger.warn("This user doesn't exist.");
		return null;
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}
}
