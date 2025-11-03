package com.cts.AuthService.dto;

import com.cts.AuthService.model.Role;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignupReq {
	
	private String name;
	private String email;
	private String mobileNumber;
	private String password;
	
	private Role role;
}
