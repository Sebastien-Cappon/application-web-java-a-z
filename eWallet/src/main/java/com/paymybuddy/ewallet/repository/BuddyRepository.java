package com.paymybuddy.ewallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.paymybuddy.ewallet.constants.MySqlQueries;
import com.paymybuddy.ewallet.model.Buddy;

import jakarta.transaction.Transactional;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with JPQL query relative to <code>Buddy</code>
 * entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface BuddyRepository extends JpaRepository<Buddy, Integer> {

	@Query(value = MySqlQueries.allBuddiesByUser, nativeQuery = true)
	public List<Buddy> getBuddiesByUser(int userId);
	
	@Modifying
	@Transactional
	@Query(value = MySqlQueries.deleteBuddiesByUser, nativeQuery = true)
	public void deleteBuddiesByUser(int userId);
}