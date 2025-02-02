package com.portfolioapi.service;

import com.portfolioapi.dao.request.AuthRequest;
import com.portfolioapi.dao.request.UserRequest;
import com.portfolioapi.dao.response.JwtResponse;
import com.portfolioapi.dao.response.UserResponse;

public interface AuthService {

    JwtResponse authenticateAndGetToken(AuthRequest authRequest);

    UserResponse registerUser(UserRequest userRequest);
}
