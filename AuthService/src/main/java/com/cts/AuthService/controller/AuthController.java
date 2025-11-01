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

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
@CrossOrigin
public class AuthController {
	
	private final AuthService authService;

	
	@PostMapping("/doctor/register")
	public ResponseEntity<String> registerDoctor(@RequestBody SignupReq req)
	{
		
		req.setRole(com.cts.AuthService.model.Role.DOCTOR);
		System.out.println("Controller For Doctor Sign Up");
		authService.signup(req);
		return ResponseEntity.ok("Doctor Profile Saved Successfully");
	}
	
	@PostMapping("patient/register")
	public ResponseEntity<String> registerPatient(@RequestBody SignupReq req)
	{
		req.setRole(com.cts.AuthService.model.Role.PATIENT);
		authService.signup(req);
		return ResponseEntity.ok("Patient Profile Saved Successfully");
	}
	
	@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
	
}
