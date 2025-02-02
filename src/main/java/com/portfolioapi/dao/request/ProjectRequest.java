package com.portfolioapi.dao.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {

    private String title;
    private String description;
    private String url;
}
