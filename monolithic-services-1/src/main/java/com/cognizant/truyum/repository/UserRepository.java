package com.cognizant.truyum.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.truyum.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT us_id,us_name,first_name,last_name,password FROM user WHERE us_name=?;", nativeQuery = true)
	Optional<User> findUserByUsername(String username);

	@Query(value = "SELECT us_name,first_name,last_name,password FROM user WHERE us_id=?;", nativeQuery = true)
	Optional<User> findUserByUserId(long userId);
	
	@Modifying
	@Transactional
	@Query(value= "Insert into user(us_name, first_name, last_name, password) values (?,?,?,?)", nativeQuery=true)
	Integer addUser(String userName, String firstName, String lastName, String password);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO user_role (ur_us_id, ur_ro_id) values (?,?)", nativeQuery=true)
	Integer addUserRole(long userId, long rollId);
	
}
