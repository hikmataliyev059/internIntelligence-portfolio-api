package com.portfolioapi.dao.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private String url;
    private Long userId;

}
