package com.portfolioapi.mapper;

import com.portfolioapi.dao.request.ProjectRequest;
import com.portfolioapi.dao.response.ProjectResponse;
import com.portfolioapi.entity.ProjectEntity;
import com.portfolioapi.entity.UserEntity;

public enum ProjectMapper {

    PROJECT_MAPPER;

    public ProjectResponse mapToResponse(ProjectEntity projectEntity) {
        return ProjectResponse.builder()
                .id(projectEntity.getId())
                .description(projectEntity.getDescription())
                .title(projectEntity.getTitle())
                .url(projectEntity.getUrl())
                .userId(projectEntity.getUser().getId())
                .build();
    }

    public ProjectEntity mapToEntity(ProjectRequest projectRequest, UserEntity user) {
        return ProjectEntity.builder()
                .description(projectRequest.getDescription())
                .title(projectRequest.getTitle())
                .url(projectRequest.getUrl())
                .user(user)
                .build();
    }
}
