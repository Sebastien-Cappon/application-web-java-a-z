package com.paymybuddy.ewallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.paymybuddy.ewallet.constants.MySqlQueries;
import com.paymybuddy.ewallet.model.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByOrderByEmailAsc();
	List<User> findByOrderByEmailDesc();
	List<User> findByOrderByAmountAsc();
	List<User> findByOrderByAmountDesc();

	@Query(value = MySqlQueries.allUser_orderByNameAsc, nativeQuery = true)
	List<User> getUsers_orderByNameAsc();
	@Query(value = MySqlQueries.allUser_orderByNameDesc, nativeQuery = true)
	List<User> getUsers_orderByNameDesc();

	Optional<User> findByEmail(String email);
	
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
}
