package com.portfolioapi.service;

import com.portfolioapi.dao.request.ExperienceRequest;
import com.portfolioapi.dao.response.ExperienceResponse;

import java.util.List;

public interface ExperienceService {

    List<ExperienceResponse> getAllExperiences();

    ExperienceResponse getExperienceById(Long id);

    ExperienceResponse createExperienceByAdmin(ExperienceRequest experienceRequest, Long userId);

    ExperienceResponse createExperienceByUser(ExperienceRequest experienceRequest, String username);

    ExperienceResponse updateExperience(Long id, ExperienceRequest experienceRequest, String username);

    void deleteExperience(Long id, String username);
}
