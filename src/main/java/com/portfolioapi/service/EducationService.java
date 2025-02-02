package com.portfolioapi.service;

import com.portfolioapi.dao.request.EducationRequest;
import com.portfolioapi.dao.response.EducationResponse;

import java.util.List;

public interface EducationService {

    List<EducationResponse> getAllEducations();

    EducationResponse getEducationById(Long id);

    EducationResponse createEducationByUser(EducationRequest educationRequest, String username);

    EducationResponse createEducationByAdmin(EducationRequest educationRequest, Long userId);

    EducationResponse updateEducation(Long id, EducationRequest educationRequest, String username);

    void deleteEducation(Long id, String username);
}
