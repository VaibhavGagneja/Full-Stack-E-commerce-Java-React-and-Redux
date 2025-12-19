package com.commerce.e_commerce.controller;

import com.commerce.e_commerce.domain.USER_ROLE;
import com.commerce.e_commerce.model.User;
import com.commerce.e_commerce.model.VerificationCode;
import com.commerce.e_commerce.repository.UserRepository;
import com.commerce.e_commerce.response.ApiResponse;
import com.commerce.e_commerce.response.AuthResponse;
import com.commerce.e_commerce.response.SignupRequest;
import com.commerce.e_commerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {
        String jwt = authService.createUser(req);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User created successfully");
        authResponse.setRole(USER_ROLE.ROLE_CUSTOMER);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody VerificationCode req) throws Exception {
        authService.sendLoginOtp(req.getEmail());
        ApiResponse res = new ApiResponse();
        res.setMessage("Otp verified successfully");
        return ResponseEntity.ok(res);
    }
}
