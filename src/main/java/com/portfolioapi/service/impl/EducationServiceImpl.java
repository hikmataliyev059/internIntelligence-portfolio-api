package com.portfolioapi.service.impl;

import com.portfolioapi.dao.request.EducationRequest;
import com.portfolioapi.dao.response.EducationResponse;
import com.portfolioapi.entity.EducationEntity;
import com.portfolioapi.entity.UserEntity;
import com.portfolioapi.exception.NotFoundException;
import com.portfolioapi.exception.UnauthorizedException;
import com.portfolioapi.repository.EducationRepository;
import com.portfolioapi.repository.UserRepository;
import com.portfolioapi.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.portfolioapi.enums.ErrorMessage.*;
import static com.portfolioapi.enums.RoleEnum.*;
import static com.portfolioapi.mapper.EducationMapper.*;
import static java.lang.String.format;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;

    @Autowired
    public EducationServiceImpl(EducationRepository educationRepository, UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<EducationResponse> getAllEducations() {
        return educationRepository.findAll().stream().map(EDUCATION_MAPPER::mapToResponse).toList();
    }

    @Override
    public EducationResponse getEducationById(Long id) {
        return educationRepository.findById(id)
                .map(EDUCATION_MAPPER::mapToResponse)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                EDUCATION_NOT_FOUND.getMessage(),
                                id
                        )
                ));
    }

    @Override
    public EducationResponse createEducationByUser(EducationRequest educationRequest, String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        EducationEntity educationEntity = educationRepository.save(EDUCATION_MAPPER.mapToEntity(educationRequest, userEntity));
        return EDUCATION_MAPPER.mapToResponse(educationEntity);
    }

    @Override
    public EducationResponse createEducationByAdmin(EducationRequest educationRequest, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        EducationEntity educationEntity = educationRepository.save(EDUCATION_MAPPER.mapToEntity(educationRequest, userEntity));
        return EDUCATION_MAPPER.mapToResponse(educationEntity);
    }

    @Override
    public EducationResponse updateEducation(Long id, EducationRequest educationRequest, String username) {
        EducationEntity educationEntity = educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                EDUCATION_NOT_FOUND.getMessage(),
                                id
                        )
                ));

        if (!hasPermission(username, educationEntity)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        educationEntity.setDescription(educationRequest.getDescription());
        educationEntity.setStartDate(educationRequest.getStartDate());
        educationEntity.setEndDate(educationRequest.getEndDate());
        educationEntity.setInstitution(educationRequest.getInstitution());
        educationEntity.setDegree(educationRequest.getDegree());
        educationRepository.save(educationEntity);
        return EDUCATION_MAPPER.mapToResponse(educationEntity);
    }

    @Override
    public void deleteEducation(Long id, String username) {
        EducationEntity educationEntity = educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                EDUCATION_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        if (!hasPermission(username, educationEntity)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }
        UserEntity userEntity = educationEntity.getUser();
        userEntity.getEducationList().remove(educationEntity);
        userRepository.save(userEntity);
    }

    private Boolean hasPermission(String username, EducationEntity educationEntity) {
        return educationEntity.getUser().getUsername().equals(username) ||
               userRepository.findByUsername(username).
                       orElseThrow(() -> new NotFoundException(
                               format(
                                       USER_NOT_FOUND.getMessage()
                               )
                       )).getRole().equals(ADMIN);
    }
}
