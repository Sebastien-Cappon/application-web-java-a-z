package com.paymybuddy.ewallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.paymybuddy.ewallet.constants.MySqlQueries;
import com.paymybuddy.ewallet.model.User;

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
}
