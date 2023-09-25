package com.paymybuddy.ewallet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.BuddyRepository;
import com.paymybuddy.ewallet.repository.UserRepository;
import com.paymybuddy.ewallet.utils.BuddyBuilder;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Buddy</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class BuddyService implements IBuddyService {

	@Autowired
	private BuddyRepository buddyRepository;
	@Autowired
	private UserRepository userRepository;

	public List<Buddy> getBuddies() {
		return buddyRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns a list of <code>User</code>s in the
	 * friend list of the active <code>User</code> whose id is passed as a
	 * parameter. It calls the <code>CrudRepository</code> <code>add()</code>
	 * method.
	 * 
	 * @return A <code>User</code> list OR <code>null</code> if the active user
	 *         doesn't exist in the database.
	 */
	@Override
	public List<User> getBuddiesByUser(int userId) {
		if (userRepository.findById(userId).isPresent()) {
			List<User> myBuddyList = new ArrayList<>();
			TreeMap<String, Integer> buddies = new TreeMap<>();

			for (Buddy buddy : buddyRepository.getBuddiesByUser(userId)) {
				if (buddy.getId().getFirstUser().getId() != userId) {
					String buddyName = buddy.getId().getFirstUser().getFirstname() + buddy.getId().getFirstUser().getLastname();
					buddies.put(buddyName, buddy.getId().getFirstUser().getId());
				} else {
					String buddyName = buddy.getId().getSecondUser().getFirstname() + buddy.getId().getSecondUser().getLastname();
					buddies.put(buddyName, buddy.getId().getSecondUser().getId());
				}
			}

			Set<String> keys = buddies.keySet();
			for (String key : keys) {
				myBuddyList.add(userRepository.findById(buddies.get(key)).get());
			}

			return myBuddyList;
		}

		return null;
	}

	/**
	 * A <code>GET</code> method that returns a list of <code>User</code>s, whose
	 * <code>active</code> attribute value is true, in the friend list of the active
	 * <code>User</code> whose id is passed as a parameter. It calls the <code>CrudRepository</code> <code>add()</code>
	 * method.
	 * 
	 * @return A <code>User</code> list OR <code>null</code> if the active user
	 *         doesn't exist in the database.
	 */
	@Override
	public List<User> getActiveBuddiesByUser(int userId) {
		if (userRepository.findById(userId).isPresent()) {
			List<User> myBuddyList = new ArrayList<>();
			TreeMap<String, Integer> buddies = new TreeMap<>();

			for (Buddy buddy : buddyRepository.getBuddiesByUser(userId)) {
				if (buddy.getId().getFirstUser().isActive() && buddy.getId().getSecondUser().isActive()) {
					if (buddy.getId().getFirstUser().getId() != userId) {
						String buddyName = buddy.getId().getFirstUser().getFirstname() + buddy.getId().getFirstUser().getLastname();
						buddies.put(buddyName, buddy.getId().getFirstUser().getId());
					} else {
						String buddyName = buddy.getId().getSecondUser().getFirstname() + buddy.getId().getSecondUser().getLastname();
						buddies.put(buddyName, buddy.getId().getSecondUser().getId());
					}
				}
			}

			Set<String> keys = buddies.keySet();
			for (String key : keys) {
				myBuddyList.add(userRepository.findById(buddies.get(key)).get());
			}

			return myBuddyList;
		}

		return null;
	}

	/**
	 * A <code>POST</code> method that returns the <code>User</code>s added to the
	 * friend list of the active <code>User</code>. The e-mail address of the future
	 * buddy and the id of the active user are the attributes of the
	 * <code>BuddyAddDto</code> passed as parameter. It calls the <code>CrudRepository</code> <code>save()</code>
	 * method.
	 * 
	 * @return A <code>User</code> OR <code>null</code> if the active user or the
	 *         future buddy doesn't exist in the database.
	 */
	@Override
	public User addBuddy(BuddyAddDto buddyAddDto) {
		if (userRepository.findById(buddyAddDto.getUserId()).isPresent() && userRepository.findByEmail(buddyAddDto.getNewBuddyEmail()).isPresent()) {
			User firstUser = userRepository.findById(buddyAddDto.getUserId()).get();
			User secondUser = userRepository.findByEmail(buddyAddDto.getNewBuddyEmail()).get();

			for (Buddy buddy : buddyRepository.findAll()) {
				if (firstUser == secondUser
					|| buddy.getId().getFirstUser() == firstUser && buddy.getId().getSecondUser() == secondUser
					|| buddy.getId().getFirstUser() == secondUser && buddy.getId().getSecondUser() == firstUser) {
					return null;
				}
			}

			Buddy newBuddy = BuddyBuilder.createBuddyFromUsers(firstUser, secondUser);
			buddyRepository.save(newBuddy);

			return secondUser;
		}

		return null;
	}

	/**
	 * A <code>DELETE</code> method that deletes relationship between two users from
	 * database <code>user_user</code> table. The ids of the concerned users are
	 * passed as parameters. It calls the JPQL query <code>delete()</code>
	 * method.
	 */
	@Override
	public void deleteBuddy(int firstUserId, int secondUserId) {
		User firstUser = userRepository.findById(firstUserId).get();
		User secondUser = userRepository.findById(secondUserId).get();

		for (Buddy buddy : buddyRepository.findAll()) {
			if (buddy.getId().getFirstUser() == firstUser && buddy.getId().getSecondUser() == secondUser) {
				Buddy buddyToDelete = BuddyBuilder.createBuddyFromUsers(firstUser, secondUser);
				buddyRepository.delete(buddyToDelete);
			} else if (buddy.getId().getFirstUser() == secondUser && buddy.getId().getSecondUser() == firstUser) {
				Buddy buddyToDelete = BuddyBuilder.createBuddyFromUsers(secondUser, firstUser);
				buddyRepository.delete(buddyToDelete);
			}
		}
	}
}