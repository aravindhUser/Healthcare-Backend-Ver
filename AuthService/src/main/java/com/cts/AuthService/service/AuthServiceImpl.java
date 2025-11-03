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
import com.cts.AuthService.model.Role;
import com.cts.AuthService.model.User;
import com.cts.AuthService.repository.UserRepository;

@Service
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
//	            throw new UserAlreadyExistsException("Email already registered");
	        }
	        

	        User user = new User();
	        
	        user.setEmail(req.getEmail());
	        user.setPassword(encoder.encode(req.getPassword()));
	        System.out.println(user.getPassword());
	        user.setRole(req.getRole());
	               

	        repo.save(user);

	        if ("DOCTOR".equalsIgnoreCase(req.getRole().name())) 
	        {

	        	UserInfo info = new UserInfo();
	        	
	        	info.setUserId(user.getUserId());
	        	info.setName(req.getName());
	        	info.setEmail(req.getEmail());
	        	info.setMobileNumber(req.getMobileNumber());
	            
	            System.out.println(info);
	        	userClient.saveDoctor(info);

	        } 
	        else if ("PATIENT".equalsIgnoreCase(req.getRole().name())) 
	        {
	        	
	        	UserInfo info = new UserInfo();
	        	
	        	info.setUserId(user.getUserId());
	        	info.setName(req.getName());
	        	info.setEmail(req.getEmail());
	        	info.setMobileNumber(req.getMobileNumber());
	            
	            System.out.println(info);
	        	userClient.savePatient(info);
	        }
	    }

	@Override
	public LoginResponse login(LoginRequest request) {
		User user = repo.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new CredentialsInvalidException("Invalid email"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new CredentialsInvalidException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail());
        System.out.println("Generated token------------------------------------------"+token);
        UserResponse u = new UserResponse();
        if ("DOCTOR".equalsIgnoreCase(user.getRole().name())) {
        	 u = userClient.getDoctor(user.getUserId());
        }
        if ("PATIENT".equalsIgnoreCase(user.getRole().name())) {
       	 u = userClient.getPatient(user.getUserId());
       }
        return new LoginResponse(token, user.getUserId(), user.getRole(), "Login Successfull", u.getId(), u.getName());
    }
	
		
}
