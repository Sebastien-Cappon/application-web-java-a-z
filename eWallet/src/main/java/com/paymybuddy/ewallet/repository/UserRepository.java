package com.paymybuddy.ewallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.paymybuddy.ewallet.constants.MySqlQueries;
import com.paymybuddy.ewallet.model.User;

import jakarta.transaction.Transactional;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with Derived and JPQL query relative to
 * <code>User</code> entities.
 * 
 * @singularity Method <code>deleteByEmail</code> is created for integration
 *              tests purpose only.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	
	List<User> findByOrderByEmailAsc();
	List<User> findByOrderByEmailDesc();
	List<User> findByOrderByAmountAsc();
	List<User> findByOrderByAmountDesc();

	@Query(value = MySqlQueries.allUser_orderByNameAsc, nativeQuery = true)
	List<User> getUsers_orderByNameAsc();
	@Query(value = MySqlQueries.allUser_orderByNameDesc, nativeQuery = true)
	List<User> getUsers_orderByNameDesc();

	@Transactional
	@Modifying
	@Query(value = MySqlQueries.updateProfileById, nativeQuery = true)
	Integer updateProfile(int id, String firstname, String lastname, String email, String password);
	@Transactional
	@Modifying
	@Query(value = MySqlQueries.updateActiveById, nativeQuery = true)
	Integer updateActive(int id, boolean isActive);
	@Transactional
	@Modifying
	@Query(value = MySqlQueries.updateAmountById, nativeQuery = true)
	Integer updateAmount(int id, double amount);
	
	void deleteByEmail(String email);
}