package com.portfolioapi.service.impl;

import com.portfolioapi.dao.request.ExperienceRequest;
import com.portfolioapi.dao.response.ExperienceResponse;
import com.portfolioapi.entity.ExperienceEntity;
import com.portfolioapi.entity.UserEntity;
import com.portfolioapi.enums.RoleEnum;
import com.portfolioapi.exception.NotFoundException;
import com.portfolioapi.exception.UnauthorizedException;
import com.portfolioapi.repository.ExperienceRepository;
import com.portfolioapi.repository.UserRepository;
import com.portfolioapi.service.ExperienceService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.portfolioapi.enums.ErrorMessage.*;
import static com.portfolioapi.mapper.ExperienceMapper.*;
import static java.lang.String.format;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ExperienceResponse> getAllExperiences() {
        return experienceRepository.findAll().stream().map(EXPERIENCE_MAPPER::mapToResponse).toList();
    }

    @Override
    public ExperienceResponse getExperienceById(Long id) {
        return experienceRepository.findById(id)
                .map(EXPERIENCE_MAPPER::mapToResponse)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                EXPERIENCE_NOT_FOUND.getMessage(),
                                id
                        )
                ));
    }

    @Override
    public ExperienceResponse createExperienceByAdmin(ExperienceRequest experienceRequest, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        ExperienceEntity experienceEntity = experienceRepository.save(EXPERIENCE_MAPPER.mapToEntity(experienceRequest, userEntity));
        return EXPERIENCE_MAPPER.mapToResponse(experienceEntity);
    }

    @Override
    public ExperienceResponse createExperienceByUser(ExperienceRequest experienceRequest, String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        ExperienceEntity experienceEntity = experienceRepository.save(EXPERIENCE_MAPPER.mapToEntity(experienceRequest, userEntity));
        return EXPERIENCE_MAPPER.mapToResponse(experienceEntity);
    }

    @Override
    public ExperienceResponse updateExperience(Long id, ExperienceRequest experienceRequest, String username) {
        ExperienceEntity experienceEntity = experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                EXPERIENCE_NOT_FOUND.getMessage(),
                                id
                        )
                ));

        if (!hasPermission(experienceEntity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        experienceEntity.setCompany(experienceRequest.getCompany());
        experienceEntity.setDescription(experienceRequest.getDescription());
        experienceEntity.setStartDate(experienceRequest.getStartDate());
        experienceEntity.setEndDate(experienceRequest.getEndDate());
        experienceEntity.setPosition(experienceRequest.getPosition());
        return EXPERIENCE_MAPPER.mapToResponse(experienceRepository.save(experienceEntity));
    }

    @Override
    public void deleteExperience(Long id, String username) {
        ExperienceEntity experienceEntity = experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                EXPERIENCE_NOT_FOUND.getMessage(),
                                id
                        )
                ));

        if (!hasPermission(experienceEntity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        UserEntity userEntity = experienceEntity.getUser();
        userEntity.getExperiences().remove(experienceEntity);
        userRepository.save(userEntity);
    }

    private Boolean hasPermission(ExperienceEntity experienceEntity, String username) {
        return experienceEntity.getUser().getUsername().equals(username) ||
               userRepository.findByUsername(username)
                       .orElseThrow(() -> new NotFoundException(
                               format(
                                       USER_NOT_FOUND.getMessage()
                               )
                       ))
                       .getRole().equals(RoleEnum.ADMIN);
    }
}
