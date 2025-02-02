package com.portfolioapi.mapper;

import com.portfolioapi.dao.request.SkillRequest;
import com.portfolioapi.dao.response.SkillResponse;
import com.portfolioapi.entity.SkillEntity;
import com.portfolioapi.entity.UserEntity;

public enum SkillMapper {

    SKILL_MAPPER;

    public SkillResponse mapToResponse(SkillEntity skillEntity) {
        return SkillResponse.builder()
                .id(skillEntity.getId())
                .name(skillEntity.getName())
                .level(skillEntity.getLevel())
                .userId(skillEntity.getUser().getId())
                .build();
    }

    public SkillEntity mapToEntity(SkillRequest skillRequest, UserEntity userEntity) {
        return SkillEntity.builder()
                .level(skillRequest.getLevel())
                .name(skillRequest.getName())
                .user(userEntity)
                .build();
    }
}
