package com.portfolioapi.dao.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EducationResponse {

    private Long id;
    private String institution;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long userId;
}
