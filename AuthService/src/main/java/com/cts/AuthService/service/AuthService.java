package com.cts.AuthService.service;

import com.cts.AuthService.dto.LoginRequest;
import com.cts.AuthService.dto.LoginResponse;
import com.cts.AuthService.dto.SignupReq;

public interface AuthService {
	void signup(SignupReq req);

	LoginResponse login(LoginRequest request);
}
