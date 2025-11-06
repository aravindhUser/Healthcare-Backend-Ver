package com.cts.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.cts.AuthService.client.UserClient;
import com.cts.AuthService.dto.*;
import com.cts.AuthService.exception.CredentialsInvalidException;
import com.cts.AuthService.exception.UserAlreadyExistsException;
import com.cts.AuthService.model.Role;
import com.cts.AuthService.model.User;
import com.cts.AuthService.repository.UserRepository;
import com.cts.AuthService.service.AuthServiceImpl;
import com.cts.AuthService.service.JWTService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class AuthServiceApplicationTests {

	@InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository repo;

    @Mock
    private UserClient userClient;

    @Mock
    private JWTService jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignupDoctor_Success() {
        SignupReq req = new SignupReq();
        req.setEmail("doctor@example.com");
        req.setPassword("password123");
        req.setName("Dr. Test");
        req.setMobileNumber("1234567890");
        req.setRole(Role.DOCTOR);
        
        when(repo.findUserByEmail(req.getEmail())).thenReturn(Optional.empty());

        authService.signup(req);

        verify(repo, times(1)).save(any(User.class));
        verify(userClient, times(1)).saveDoctor(any(UserInfo.class));
    }

    @Test
    public void testSignupPatient_Success() {
        SignupReq req = new SignupReq();
        req.setEmail("patient@example.com");
        req.setPassword("password123");
        req.setName("Mr. Patient");
        req.setMobileNumber("9876543210");
        req.setRole(Role.PATIENT);

        when(repo.findUserByEmail(req.getEmail())).thenReturn(Optional.empty());

        authService.signup(req);

        verify(repo, times(1)).save(any(User.class));
        verify(userClient, times(1)).savePatient(any(UserInfo.class));
    }

    @Test
    public void testSignup_AlreadyExists() {
        SignupReq req = new SignupReq();
        req.setEmail("existing@example.com");
        req.setPassword("password123");
        req.setRole(Role.DOCTOR);
        
        User existingUser = new User();
        existingUser.setEmail(req.getEmail());

        when(repo.findUserByEmail(req.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> authService.signup(req));
    }

    @Test
    public void testLogin_Success_Doctor() {
        LoginRequest request = new LoginRequest();
        request.setEmail("doctor@example.com");
        request.setPassword("password123");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode("password123"));
        user.setRole(Role.DOCTOR);
        user.setUserId(1);

        UserResponse response = new UserResponse();
        response.setId(1);
        response.setName("Dr. Test");

        when(repo.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("mocked-token");
        when(userClient.getDoctor(user.getUserId())).thenReturn(response);

        LoginResponse loginResponse = authService.login(request);

        assertEquals("mocked-token", loginResponse.getToken());
        assertEquals("Dr. Test", loginResponse.getName());
    }

    @Test
    public void testLogin_InvalidEmail() {
        LoginRequest request = new LoginRequest();
        request.setEmail("invalid@example.com");
        request.setPassword("password123");

        when(repo.findUserByEmail(request.getEmail())).thenReturn(Optional.empty());

        assertThrows(CredentialsInvalidException.class, () -> authService.login(request));
    }

    @Test
    public void testLogin_InvalidPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("wrongpassword");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode("correctpassword"));
        user.setRole(Role.PATIENT);

        when(repo.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));

        assertThrows(CredentialsInvalidException.class, () -> authService.login(request));
    }

}
