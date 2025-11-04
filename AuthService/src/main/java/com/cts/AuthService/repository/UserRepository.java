package com.cts.AuthService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.AuthService.model.Role;
import com.cts.AuthService.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findUserByEmail(String email);
	
	boolean existsByEmail(String email);
	
	List<User> findByRole(Role role);

}
