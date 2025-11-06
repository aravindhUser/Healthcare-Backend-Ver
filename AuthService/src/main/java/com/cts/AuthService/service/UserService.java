package com.cts.AuthService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.AuthService.model.Role;
import com.cts.AuthService.model.User;
import com.cts.AuthService.repository.UserRepository;
//import org.springframework.security.core.userdetails.UserDetailsService;

@Service 
public class UserService implements UserDetailsService{
	@Autowired
	UserRepository repo;
	
	Role role;
	
	
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		User user = repo.findUserByEmail(email).orElse(null);
//		
//		if(user!=null && user.getRole().equals("DOCTOR")) {
//			return new User(user.getEmail(), user.getPassword(), user.getRole());	
//		}
//		else if(user!=null && user.getRole().equals("PATIENT")) {
//			return new User(user.getEmail(), user.getPassword(), user.getRole());	
//		}
//		
//		throw new UsernameNotFoundException("User Not Found");
//	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    return repo.findUserByEmail(email)
	        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
	}

	    
}
