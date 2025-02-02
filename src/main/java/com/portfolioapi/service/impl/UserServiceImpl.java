package com.portfolioapi.service.impl;

import com.portfolioapi.dao.request.UserRequest;
import com.portfolioapi.dao.response.UserResponse;
import com.portfolioapi.entity.UserEntity;
import com.portfolioapi.enums.RoleEnum;
import com.portfolioapi.exception.AlreadyExistsException;
import com.portfolioapi.exception.NotFoundException;
import com.portfolioapi.exception.UnauthorizedException;
import com.portfolioapi.mapper.UserMapper;
import com.portfolioapi.repository.UserRepository;
import com.portfolioapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.portfolioapi.enums.ErrorMessage.*;
import static com.portfolioapi.enums.RoleEnum.ADMIN;
import static com.portfolioapi.mapper.UserMapper.USER_MAPPER;
import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(USER_MAPPER::mapToResponse).toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(USER_MAPPER::mapToResponse)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        UserEntity user = userRepository.save(USER_MAPPER.mapToEntity(userRequest));
        return USER_MAPPER.mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest, String username) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        if (!hasPermission(username, user)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS.getMessage());
        }

        user.setUsername(userRequest.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        return USER_MAPPER.mapToResponse(user);
    }

    @Override
    public void deleteUser(Long userId, String username) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        if (!hasPermission(username, user)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }
        userRepository.delete(user);
    }

    private Boolean hasPermission(String username, UserEntity userEntity) {
        return userEntity.getUsername().equals(username) ||
               userRepository.findByUsername(username)
                       .orElseThrow(() -> new NotFoundException(
                               format(
                                       USER_NOT_FOUND.getMessage()
                               )
                       )).getRole().equals(ADMIN);
    }
}
