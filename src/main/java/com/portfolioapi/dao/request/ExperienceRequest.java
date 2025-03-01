package com.portfolioapi.dao.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExperienceRequest {

    private String company;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
