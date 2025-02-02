package com.portfolioapi.dao.response;

import com.portfolioapi.enums.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private RoleEnum role;
    private List<ProjectResponse> projects;
    private List<SkillResponse> skills;
    private List<ExperienceResponse> experiences;
    private List<EducationResponse> educationList;
}
