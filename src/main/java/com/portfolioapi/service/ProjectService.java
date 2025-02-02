package com.portfolioapi.service;

import com.portfolioapi.dao.request.ProjectRequest;
import com.portfolioapi.dao.response.ProjectResponse;

import java.util.List;

public interface ProjectService {

    List<ProjectResponse> getAllProjects();

    ProjectResponse getProjectById(Long id);

    ProjectResponse createProjectByUser(ProjectRequest projectRequest, String username);

    ProjectResponse createProjectByAdmin(ProjectRequest projectRequest, Long userId);

    ProjectResponse updateProject(Long id, ProjectRequest projectRequest, String username);

    void deleteProject(Long id, String username);
}
