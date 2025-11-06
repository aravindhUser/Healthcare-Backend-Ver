package com.cts.AuthService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.AuthService.client.UserClient;
import com.cts.AuthService.dto.LoginRequest;
import com.cts.AuthService.dto.LoginResponse;
import com.cts.AuthService.dto.SignupReq;
import com.cts.AuthService.dto.UserInfo;
import com.cts.AuthService.dto.UserResponse;
import com.cts.AuthService.exception.CredentialsInvalidException;
import com.cts.AuthService.exception.UserAlreadyExistsException;
import com.cts.AuthService.model.Role;
import com.cts.AuthService.model.User;
import com.cts.AuthService.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	UserClient userClient;
	
	@Autowired
	JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Override
	public void signup(SignupReq req) {
	        if (!repo.findUserByEmail(req.getEmail()).isEmpty()) {
	            throw new UserAlreadyExistsException("Email already registered");
	        }
	        

	        User user = new User();
	        
	        user.setEmail(req.getEmail());
	        user.setPassword(encoder.encode(req.getPassword()));
	        System.out.println(user.getPassword());
	        user.setRole(req.getRole());
	               

	        repo.save(user);
	        log.debug("Encoded password for {}: {}", req.getEmail(), user.getPassword());
	        log.info("Saving user with email: {} and role: {}", req.getEmail(), req.getRole());

	        if ("DOCTOR".equalsIgnoreCase(req.getRole().name())) 
	        {
	        	log.info("Registering doctor: {}", req.getName());
	        	UserInfo info = new UserInfo();
	        	
	        	info.setUserId(user.getUserId());
	        	info.setName(req.getName());
	        	info.setEmail(req.getEmail());
	        	info.setMobileNumber(req.getMobileNumber());
	            
	            try {
	            	userClient.saveDoctor(info);
	            	log.info("Doctor info sent to user service: {}", info);
	            }
	            catch(Exception e) {
	            	log.error("Failed to send doctor info: {}", e.getMessage());
	            }

	        } 
	        else if ("PATIENT".equalsIgnoreCase(req.getRole().name())) 
	        {
	        	log.info("Registering patient: {}", req.getName());	
	        	UserInfo info = new UserInfo();
	        	
	        	info.setUserId(user.getUserId());
	        	info.setName(req.getName());
	        	info.setEmail(req.getEmail());
	        	info.setMobileNumber(req.getMobileNumber());
	            
	            System.out.println(info);
	            userClient.savePatient(info);
//	        	try {
//	        		userClient.savePatient(info);
//	            	log.info("Patient info sent to user service: {}", info);
//	            }
//	            catch(Exception e) {
//	            	log.error("Failed to send patient info: {}", e.getMessage());
//	            }
	        }
	    }

	@Override
	public LoginResponse login(LoginRequest request) {
		
		log.info("Attempting login for email: {}", request.getEmail());
		
		User user = repo.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new CredentialsInvalidException("Invalid email"));
//		System.out.println(encoder.getPassword()+user.getPassword());
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new CredentialsInvalidException("Invalid Password");
        }

        // Generating token
        String token = jwtService.generateToken(user.getEmail());
        log.debug("Generated token for {}: {}", user.getEmail(), token);

        
        UserResponse u = new UserResponse();
        //Doctor Login
        if ("DOCTOR".equalsIgnoreCase(user.getRole().name())) {
        	log.info("Fetching doctor info for userId: {}", user.getUserId());
        	 u = userClient.getDoctor(user.getUserId());
        }
        //Patient Login
        if ("PATIENT".equalsIgnoreCase(user.getRole().name())) {
        	log.info("Fetching patient info for userId: {}", user.getUserId());
        	u = userClient.getPatient(user.getUserId());
       }
        log.info("Login successful for userId: {}", user.getUserId());
        return new LoginResponse(token, user.getUserId(), user.getRole(), "Login Successfull", u.getId(), u.getName());
    }
	
		
}
