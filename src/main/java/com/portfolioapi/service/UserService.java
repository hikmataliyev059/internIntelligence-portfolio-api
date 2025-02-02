package com.portfolioapi.service;

import com.portfolioapi.dao.request.UserRequest;
import com.portfolioapi.dao.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Long userId, UserRequest userRequest, String username);

    void deleteUser(Long id, String username);
}
