package com.paymybuddy.ewallet.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.Transaction;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.TransactionRepository;
import com.paymybuddy.ewallet.repository.UserRepository;
import com.paymybuddy.ewallet.utils.PasswordManager;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>User</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class UserService implements IUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * A <code>GET</code> method that returns a <code>User</code>s whose id is
	 * passed as a parameter after calling the <code>findById()</code> derived
	 * query from repository.
	 * 
	 * @return A <code>User</code> OR <code>null</code> if he doesn't exist in the
	 *         database.
	 */
	@Override
	public User getUserById(int userId) {
		if (userRepository.findById(userId).isPresent()) {
			User userResponse = userRepository.findById(userId).get();
			return userResponse;
		}

		return null;
	}

	/**
	 * A <code>POST</code> method that returns a <code>User</code> whose email and
	 * non-encrypted password are attributes of the <code>UserLoginDto</code> passed
	 * as parameter. It calls the <code>findByEmail()</code> derived query from
	 * repository.
	 * 
	 * @return A <code>User</code> OR <code>null</code> if he doesn't exist in the
	 *         database or if the password sent is wrong.
	 */
	@Override
	public User postUserByEmailAndPassword(UserLoginDto userLoginDto) throws Exception {
		if (userRepository.findByEmail(userLoginDto.getEmail()).isPresent()) {
			User user = userRepository.findByEmail(userLoginDto.getEmail()).get();
			
			if(user.isActive()) {
				String inputPassword = userLoginDto.getPassword();
				String userPassword = user.getPassword();
	
				if (PasswordManager.checkPassword(inputPassword, userPassword)) {
					return user;
				}
			}
		}

		return null;
	}

	/**
	 * A <code>POST</code> method that returns the <code>User</code> passed as
	 * parameter if his account is created or reactivated, and calls the
	 * <code>CrudRepository</code> <code>save()</code> method.
	 * 
	 * @singularity If the e-mail address of the user passed as parameter is already
	 *              in the database AND if this <code>User</code> is no longer
	 *              active, then, his account is reactivated with his new password.
	 * 
	 * @return A <code>User</code> OR <code>null</code> if his email is already in
	 *         the database, and the account is always active.
	 */
	@Override
	public User addUser(User user) throws Exception {
		if(user.getFirstname() != null && user.getLastname() != null && user.getEmail() != null) {
			for (User checkUser : userRepository.findAll()) {
				if (checkUser.getEmail().contentEquals(user.getEmail())) {
					if (checkUser.isActive()) {
						logger.warn("A user with this email address already exists.");
						return null;
					} else {
						String newPassword = PasswordManager.hashPassword(user.getPassword());
						
						userRepository.updateProfile(checkUser.getId(), checkUser.getFirstname(), checkUser.getLastname(), checkUser.getEmail(), newPassword);
						userRepository.updateActive(checkUser.getId(), true);
						
						return checkUser;
					}
				}
			}
	
			String hashedPassword = PasswordManager.hashPassword(user.getPassword());
			user.setPassword(hashedPassword);
	
			return userRepository.save(user);
		}
		
		return null;
	}
	
	/**
	 * An <code>UPDATE</code> method that checks informations passed as
	 * <code>UserProfileDto</code> parameter and calls then the JPQL query
	 * <code>updateProfile()</code> for the user whose id is passed as the first
	 * parameter.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the <code>User</code>
	 *         doesn't exists in the database.
	 */
	@Override
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
				for (User checkUser : userRepository.findAll()) {
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
				userProfileDto.setPassword(PasswordManager.hashPassword(userProfileDto.getPassword()));
			}

			userRepository.updateProfile(userId, userProfileDto.getFirstname(), userProfileDto.getLastname(), userProfileDto.getEmail(), userProfileDto.getPassword());
			return 1;
		}

		logger.warn("This user doesn't exist.");
		return null;
	}
	
	/**
	 * An <code>UPDATE</code> method that calls the JPQL query
	 * <code>updateActive()</code> if the user whose id is passed as parameter
	 * exists.
	 * 
	 * @singularity If the <code>active</code> attribute goes false, the user bank
	 *              account his credited with the residual value of the e-wallet.
	 *              The e-wallet value is therefore reset to 0.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the <code>User</code>
	 *         doesn't exists in the database.
	 */
	@Override
	public Integer updateActive(int userId, boolean isActive) throws Exception {
		if(userRepository.findById(userId).isPresent()) {
			User user = userRepository.findById(userId).get();
			
			if(user.isActive() && !isActive) {
				Transaction lastTransaction = new Transaction();
				lastTransaction.setDate(LocalDate.now());
				lastTransaction.setSender(user);
				lastTransaction.setReceiver(user);
				lastTransaction.setAmount(-user.getAmount());
				lastTransaction.setFee(0);
				lastTransaction.setDescription("You have closed your PayMyBuddyAccount.");
				
				userRepository.updateAmount(userId, 0);
				transactionRepository.save(lastTransaction);
			}
			
			userRepository.updateActive(userId, isActive);
			return 1;
		}

		logger.warn("This user doesn't exist.");
		return null;
	}
	
	/**
	 * An <code>UPDATE</code> method that calls the JPQL query
	 * <code>updateAmount()</code> if the user whose id is passed as parameter
	 * exists.
	 * 
	 * @singularity This method is only called from service. It has no controller
	 *              attached.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the <code>User</code>
	 *         doesn't exists in the database.
	 */
	@Override
	public Integer updateAmount(int userId, double amount) throws Exception {
		if(userRepository.findById(userId).isPresent()) {
			userRepository.updateAmount(userId, amount);
			return 1;
		}

		logger.warn("This user doesn't exist.");
		return null;
	}
}