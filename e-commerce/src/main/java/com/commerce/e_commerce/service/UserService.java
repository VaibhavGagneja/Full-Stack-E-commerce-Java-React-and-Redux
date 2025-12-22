package com.commerce.e_commerce.service;

import com.commerce.e_commerce.model.User;

public interface UserService {
    User findUserByJwtToken(String jwtToken) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
