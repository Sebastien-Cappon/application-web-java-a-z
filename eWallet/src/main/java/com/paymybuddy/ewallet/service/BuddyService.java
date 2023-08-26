package com.paymybuddy.ewallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.ewallet.model.Buddy;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.repository.BuddyRepository;

@Service
public class BuddyService implements IBuddyService {
	
	@Autowired
	private BuddyRepository buddyRepository;
	
	public List<Buddy> getBuddies() {
		return buddyRepository.findAll();
	}
	
	public List<Buddy> getBuddiesByUser(User user) {	
		return buddyRepository.getBuddiesByUser(user.getId());
	}
	
	public Buddy addBuddy(Buddy buddy) {
		if(buddy != null && buddy.getId().getFirstUser() != null && buddy.getId().getSecondUser() != null) {
			return buddyRepository.save(buddy);
		}
		
		return null;
	}
	
	public void deleteBuddy(Buddy buddy) {
		buddyRepository.delete(buddy);
	}
	
	public void deleteBuddiesByUser(User user) {
		buddyRepository.deleteBuddiesByUser(user.getId());
	}
}
