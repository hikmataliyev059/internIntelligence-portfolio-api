package com.portfolioapi.controller;

import com.portfolioapi.dao.request.EducationRequest;
import com.portfolioapi.dao.response.EducationResponse;
import com.portfolioapi.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/education")
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getAllEducations() {
        return ResponseEntity.ok(educationService.getAllEducations());
    }

    @GetMapping("{id}")
    public ResponseEntity<EducationResponse> getEducationById(@PathVariable Long id) {
        return ResponseEntity.ok(educationService.getEducationById(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user")
    public ResponseEntity<EducationResponse> createEducationByUser(@RequestBody EducationRequest educationRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(educationService.createEducationByUser(educationRequest, authentication.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/admin")
    public ResponseEntity<EducationResponse> createEducationByAdmin(@RequestBody EducationRequest educationRequest, @PathVariable Long userId) {
        return ResponseEntity.ok(educationService.createEducationByAdmin(educationRequest, userId));
    }

    @PutMapping("{id}")
    public ResponseEntity<EducationResponse> updateEducation(@PathVariable Long id, @RequestBody EducationRequest educationRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(educationService.updateEducation(id, educationRequest, authentication.getName()));
    }

    @DeleteMapping("{id}")
    public void deleteEducation(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        educationService.deleteEducation(id, authentication.getName());
    }
}
