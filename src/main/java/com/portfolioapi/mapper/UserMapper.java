package com.portfolioapi.mapper;

import com.portfolioapi.dao.request.UserRequest;
import com.portfolioapi.dao.response.UserResponse;
import com.portfolioapi.entity.UserEntity;
import com.portfolioapi.enums.RoleEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.portfolioapi.mapper.EducationMapper.EDUCATION_MAPPER;
import static com.portfolioapi.mapper.ProjectMapper.PROJECT_MAPPER;
import static com.portfolioapi.mapper.SkillMapper.SKILL_MAPPER;

public enum UserMapper {

    USER_MAPPER;

    public UserResponse mapToResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .educationList(user.getEducationList() == null ? null : user.getEducationList().stream().map(EDUCATION_MAPPER::mapToResponse).toList())
                .projects(user.getProjects() == null ? null : user.getProjects().stream().map(PROJECT_MAPPER::mapToResponse).toList())
                .skills(user.getSkills() == null ? null : user.getSkills().stream().map(SKILL_MAPPER::mapToResponse).toList())
                .build();
    }

    public UserEntity mapToEntity(UserRequest userRequest) {
        return UserEntity.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .build();
    }
}
