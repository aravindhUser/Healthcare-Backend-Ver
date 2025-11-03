package com.cts.AuthService.dto;

import com.cts.AuthService.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

	private String token;
	private int userId;
	private Role role;
	private String message;
	private int id;
	private String name;
}
