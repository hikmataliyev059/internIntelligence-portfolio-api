package com.portfolioapi.dao.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SkillResponse {

    private Long id;
    private String name;
    private String level;
    private Long userId;
}
