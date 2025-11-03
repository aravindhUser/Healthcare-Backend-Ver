package com.cts.AuthService.controller;

import com.cts.AuthService.dto.SignupReq;
import com.cts.AuthService.model.Role;
import com.cts.AuthService.repository.UserRepository;
import com.cts.AuthService.service.AuthService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AutoConfig {

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private AuthService authService;

	    @PostConstruct
	    public void initDoctors() {
	        if (userRepository.findByRole(Role.DOCTOR).isEmpty()) {
	            List<SignupReq> doctors = List.of(
	                createDoctor("Guru Sakthi", "guru@gmail.com", "9360351172", "Guru@1234"),
	                createDoctor("Amarsri ", "amarsri@gmail.com", "7845511213", "Amarsri@16"),
	                createDoctor("Aravindh", "aravindh@gmail.com", "8939340849", "Aravindh@123"),
	                createDoctor("Amal Krishna", "amal@gmail.com", "9567542622", "Amal@12345"),
	                createDoctor("Venkat Ramani", "venkat@gmail.com", "9191919191", "Venkat@1234")
	            );

	            doctors.forEach(authService::signup);
	            log.info("5 hardcoded doctor profiles initialized.");
	        } else {
	            log.warn("Doctor data already exists. Skipping initialization.");
	        }
	    }

	    private SignupReq createDoctor(String name, String email, String mobile, String password) {
	        SignupReq req = new SignupReq();
	        req.setName(name);
	        req.setEmail(email);
	        req.setMobileNumber(mobile);
	        req.setPassword(password);
	        req.setRole(Role.DOCTOR);
	        return req;
	    }
	    
	    @PostConstruct
	    public void initPatients() {
	        if (userRepository.findByRole(Role.PATIENT).isEmpty()) {
	            List<SignupReq> patients = List.of(
	                createPatient("Rahul", "rahul@gmail.com", "7829813774", "Rahul@1234"),
	                createPatient("Akash", "akash@gmail.com", "9025374027", "Akash@1234"),
	                createPatient("Karthi", "karthi@gmail.com", "9345584094", "Karthi@1234"),
	                createPatient("Charles", "charles@gmail.com", "9360326131", "Charles@1234"),
	                createPatient("Raj Kumar", "raj@gmail.com", "8220271984", "Rajkumar@1234")
	            );

	            patients.forEach(authService::signup);
	            log.info("5 hardcoded Patient profiles initialized.");
	        } else {
	            log.warn("Patient data already exists. Skipping initialization.");
	        }
	    }

	    private SignupReq createPatient(String name, String email, String mobile, String password) {
	        SignupReq req = new SignupReq();
	        req.setName(name);
	        req.setEmail(email);
	        req.setMobileNumber(mobile);
	        req.setPassword(password);
	        req.setRole(Role.PATIENT);
	        return req;
	    }
	}
