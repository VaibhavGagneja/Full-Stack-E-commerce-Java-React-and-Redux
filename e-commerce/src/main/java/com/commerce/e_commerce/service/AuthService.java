package com.commerce.e_commerce.service;

import com.commerce.e_commerce.domain.USER_ROLE;
import com.commerce.e_commerce.model.User;
import com.commerce.e_commerce.request.LoginRequest;
import com.commerce.e_commerce.response.AuthResponse;
import com.commerce.e_commerce.response.SignupRequest;

public interface AuthService {
    String createUser(SignupRequest req) throws Exception;
    void sendLoginOtp(String email, USER_ROLE role) throws Exception;
    AuthResponse signIn(LoginRequest req) throws Exception; //in video method name is signing
}
