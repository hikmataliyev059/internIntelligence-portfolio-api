package com.portfolioapi.service;

import com.portfolioapi.dao.request.SkillRequest;
import com.portfolioapi.dao.response.SkillResponse;

import java.util.List;

public interface SkillService {

    List<SkillResponse> getAllSkills();

    SkillResponse getSkillById(Long id);

    SkillResponse createSkillByAdmin(SkillRequest skillRequest, Long userId);

    SkillResponse createSkillByUser(SkillRequest skillRequest, String username);

    SkillResponse updateSkill(Long id, SkillRequest skillRequest, String username);

    void deleteSkill(Long id, String username);
}
