package com.portfolioapi.service.impl;

import com.portfolioapi.dao.request.AuthRequest;
import com.portfolioapi.dao.request.UserRequest;
import com.portfolioapi.dao.response.JwtResponse;
import com.portfolioapi.dao.response.UserResponse;
import com.portfolioapi.entity.UserEntity;
import com.portfolioapi.exception.AlreadyExistsException;
import com.portfolioapi.exception.NotFoundException;
import com.portfolioapi.repository.UserRepository;
import com.portfolioapi.security.JwtUtil;
import com.portfolioapi.service.AuthService;
import com.portfolioapi.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.portfolioapi.enums.ErrorMessage.*;
import static java.lang.String.format;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public JwtResponse authenticateAndGetToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponse.builder()
                    .token(jwtUtil.generateToken(authRequest.getUsername())).build();
        } else {
            throw new UsernameNotFoundException(INVALID_USER_REQUEST.getMessage());
        }
    }

    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        UserEntity existingUser = userRepository.findByEmail(userRequest.getEmail())
                .orElse(null);
        if (existingUser != null && existingUser.getEmail() != null && existingUser.getEmail().isEmpty()) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS.getMessage());
        }
        return userService.createUser(userRequest);
    }
}
