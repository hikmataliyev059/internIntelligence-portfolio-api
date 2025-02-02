package com.portfolioapi.mapper;

import com.portfolioapi.dao.request.EducationRequest;
import com.portfolioapi.dao.response.EducationResponse;
import com.portfolioapi.entity.EducationEntity;
import com.portfolioapi.entity.UserEntity;

public enum EducationMapper {

    EDUCATION_MAPPER;

    public EducationResponse mapToResponse(EducationEntity educationEntity) {
        return EducationResponse.builder()
                .id(educationEntity.getId())
                .degree(educationEntity.getDegree())
                .startDate(educationEntity.getStartDate())
                .endDate(educationEntity.getEndDate())
                .description(educationEntity.getDescription())
                .institution(educationEntity.getInstitution())
                .userId(educationEntity.getUser().getId())
                .build();
    }

    public EducationEntity mapToEntity(EducationRequest educationRequest, UserEntity user) {
        return EducationEntity.builder()
                .startDate(educationRequest.getStartDate())
                .endDate(educationRequest.getEndDate())
                .description(educationRequest.getDescription())
                .institution(educationRequest.getInstitution())
                .degree(educationRequest.getDegree())
                .user(user)
                .build();
    }
}
