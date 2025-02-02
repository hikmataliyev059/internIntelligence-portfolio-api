package com.portfolioapi.mapper;

import com.portfolioapi.dao.request.ExperienceRequest;
import com.portfolioapi.dao.response.ExperienceResponse;
import com.portfolioapi.entity.ExperienceEntity;
import com.portfolioapi.entity.UserEntity;

public enum ExperienceMapper {

    EXPERIENCE_MAPPER;

    public ExperienceResponse mapToResponse(ExperienceEntity experienceEntity) {
        return ExperienceResponse.builder()
                .id(experienceEntity.getId())
                .description(experienceEntity.getDescription())
                .company(experienceEntity.getCompany())
                .startDate(experienceEntity.getStartDate())
                .endDate(experienceEntity.getEndDate())
                .position(experienceEntity.getPosition())
                .userId(experienceEntity.getUser().getId())
                .build();
    }

    public ExperienceEntity mapToEntity(ExperienceRequest experienceRequest, UserEntity userEntity) {
        return ExperienceEntity.builder()
                .startDate(experienceRequest.getStartDate())
                .endDate(experienceRequest.getEndDate())
                .company(experienceRequest.getCompany())
                .description(experienceRequest.getDescription())
                .position(experienceRequest.getPosition())
                .user(userEntity)
                .build();
    }
}
