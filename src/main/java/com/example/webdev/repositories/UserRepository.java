package com.example.webdev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.webdev.models.*;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
	Iterable<User> findUserByCredentials(
			@Param("username") String username,
			@Param("password") String password);

	@Query("SELECT u FROM User u WHERE u.username=:username")
	Iterable<User> findUserByUsername(@Param("username") String u);
	
	@Query("SELECT u FROM User u WHERE u.email=:email")
	Iterable<User> findUserByEmail(@Param("email")String email);
	
	@Query("SELECT u FROM User u WHERE u.token=:token")
	Iterable<User> findUserByToken(@Param("token")String token);
	
	@Query("SELECT u FROM User u WHERE u.username=:username or u.firstName=:firstName or u.lastName=:lastName or u.role=:role")
	Iterable<User> findUserByField(
			@Param("username")String username,
			@Param("firstName")String firstName,
			@Param("lastName")String lastName,
			@Param("role")String role);
	
}





