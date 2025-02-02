package com.portfolioapi.service.impl;

import com.portfolioapi.dao.request.ProjectRequest;
import com.portfolioapi.dao.response.ProjectResponse;
import com.portfolioapi.entity.ProjectEntity;
import com.portfolioapi.entity.UserEntity;
import com.portfolioapi.exception.NotFoundException;
import com.portfolioapi.exception.UnauthorizedException;
import com.portfolioapi.repository.ProjectRepository;
import com.portfolioapi.repository.UserRepository;
import com.portfolioapi.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.portfolioapi.enums.ErrorMessage.*;
import static com.portfolioapi.enums.RoleEnum.*;
import static com.portfolioapi.mapper.ProjectMapper.PROJECT_MAPPER;
import static java.lang.String.format;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(PROJECT_MAPPER::mapToResponse).toList();
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(PROJECT_MAPPER::mapToResponse)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                PROJECT_NOT_FOUND.getMessage(),
                                id
                        )
                ));
    }

    @Override
    public ProjectResponse createProjectByUser(ProjectRequest projectRequest, String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        ProjectEntity projectEntity = projectRepository.save(PROJECT_MAPPER.mapToEntity(projectRequest, userEntity));
        return PROJECT_MAPPER.mapToResponse(projectEntity);
    }

    @Override
    public ProjectResponse createProjectByAdmin(ProjectRequest projectRequest, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        ProjectEntity projectEntity = projectRepository.save(PROJECT_MAPPER.mapToEntity(projectRequest, userEntity));
        return PROJECT_MAPPER.mapToResponse(projectEntity);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest, String username) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                PROJECT_NOT_FOUND.getMessage()
                        )
                ));

        if (!hasPermission(projectEntity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        projectEntity.setDescription(projectRequest.getDescription());
        projectEntity.setUrl(projectRequest.getUrl());
        projectEntity.setTitle(projectRequest.getTitle());

        return PROJECT_MAPPER.mapToResponse(projectRepository.save(projectEntity));
    }

    @Override
    public void deleteProject(Long id, String username) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                PROJECT_NOT_FOUND.getMessage(),
                                id
                        )
                ));

        if (!hasPermission(projectEntity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        UserEntity userEntity = projectEntity.getUser();
        userEntity.getProjects().remove(projectEntity);
        userRepository.save(userEntity);
    }

    private Boolean hasPermission(ProjectEntity projectEntity, String username) {
        return projectEntity.getUser().getUsername().equals(username) ||
               userRepository.findByUsername(username)
                       .orElseThrow(() -> new NotFoundException(
                               format(
                                       USER_NOT_FOUND.getMessage()
                               )
                       )).getRole().equals(ADMIN);
    }
}
