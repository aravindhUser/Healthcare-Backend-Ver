package com.cts.AuthService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.AuthService.dto.LoginRequest;
import com.cts.AuthService.dto.LoginResponse;
import com.cts.AuthService.dto.SignupReq;

import com.cts.AuthService.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
//@CrossOrigin
@Slf4j
public class AuthController {
	
	private final AuthService authService;

	
	@PostMapping("/doctor/register")
	public ResponseEntity<String> registerDoctor(@RequestBody SignupReq req)
	{
		
		req.setRole(com.cts.AuthService.model.Role.DOCTOR);
		log.info("API Call : Controller For Doctor Sign Up");
		authService.signup(req);
		return ResponseEntity.ok("Doctor Profile Saved Successfully");
	}
	
	@PostMapping("patient/register")
	public ResponseEntity<String> registerPatient(@RequestBody SignupReq req)
	{
		req.setRole(com.cts.AuthService.model.Role.PATIENT);
		log.info("API Call : Controller For Patient Sign Up");
		authService.signup(req);
		return ResponseEntity.ok("Patient Profile Saved Successfully");
	}
	
	@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        log.info("API Call : Controller for User Login");
        return ResponseEntity.ok(response);
    }
	
}
