package com.portfolioapi.controller;

import com.portfolioapi.dao.request.ExperienceRequest;
import com.portfolioapi.dao.response.ExperienceResponse;
import com.portfolioapi.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    @Autowired
    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getAllExperience() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @GetMapping("{id}")
    public ResponseEntity<ExperienceResponse> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(experienceService.getExperienceById(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user")
    public ResponseEntity<ExperienceResponse> createExperienceByUser(@RequestBody ExperienceRequest experienceRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(experienceService.createExperienceByUser(experienceRequest, authentication.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/admin")
    public ResponseEntity<ExperienceResponse> createExperienceByAdmin(@RequestBody ExperienceRequest experienceRequest,
                                                                      @PathVariable Long userId) {
        return ResponseEntity.ok(experienceService.createExperienceByAdmin(experienceRequest, userId));
    }

    @PutMapping("{id}")
    public ResponseEntity<ExperienceResponse> updateExperience(@RequestBody ExperienceRequest experienceRequest,
                                                               @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(experienceService.updateExperience(id, experienceRequest, authentication.getName()));
    }

    @DeleteMapping("{id}")
    public void deleteExperience(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        experienceService.deleteExperience(id, authentication.getName());
    }

}
