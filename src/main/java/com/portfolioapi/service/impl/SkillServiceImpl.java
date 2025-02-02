package com.portfolioapi.service.impl;

import com.portfolioapi.dao.request.SkillRequest;
import com.portfolioapi.dao.response.SkillResponse;
import com.portfolioapi.entity.EducationEntity;
import com.portfolioapi.entity.SkillEntity;
import com.portfolioapi.entity.UserEntity;
import com.portfolioapi.enums.RoleEnum;
import com.portfolioapi.exception.NotFoundException;
import com.portfolioapi.exception.UnauthorizedException;
import com.portfolioapi.mapper.SkillMapper;
import com.portfolioapi.repository.SkillRepository;
import com.portfolioapi.repository.UserRepository;
import com.portfolioapi.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.portfolioapi.enums.ErrorMessage.*;
import static com.portfolioapi.enums.RoleEnum.ADMIN;
import static com.portfolioapi.mapper.SkillMapper.SKILL_MAPPER;
import static java.lang.String.format;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public SkillServiceImpl(SkillRepository skillRepository, UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream().map(SKILL_MAPPER::mapToResponse).toList();
    }

    @Override
    public SkillResponse getSkillById(Long id) {
        return skillRepository.findById(id)
                .map(SKILL_MAPPER::mapToResponse)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                SKILL_NOT_FOUND.getMessage(),
                                id
                        )
                ));
    }

    @Override
    public SkillResponse createSkillByAdmin(SkillRequest skillRequest, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        SkillEntity skillEntity = skillRepository.save(SKILL_MAPPER.mapToEntity(skillRequest, userEntity));
        return SKILL_MAPPER.mapToResponse(skillEntity);
    }

    @Override
    public SkillResponse createSkillByUser(SkillRequest skillRequest, String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage()
                        )
                ));

        SkillEntity skillEntity = skillRepository.save(SKILL_MAPPER.mapToEntity(skillRequest, userEntity));
        return SKILL_MAPPER.mapToResponse(skillEntity);
    }

    @Override
    public SkillResponse updateSkill(Long id, SkillRequest skillRequest, String username) {
        SkillEntity skillEntity = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                SKILL_NOT_FOUND.getMessage(),
                                id
                        )
                ));

        if (!hasPermission(username, skillEntity)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        skillEntity.setName(skillRequest.getName());
        skillEntity.setLevel(skillRequest.getLevel());
        skillRepository.save(skillEntity);

        return SKILL_MAPPER.mapToResponse(skillEntity);
    }

    @Override
    public void deleteSkill(Long id, String username) {
        SkillEntity skillEntity = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                SKILL_NOT_FOUND.getMessage(),
                                id
                        )
                ));

        if (!hasPermission(username, skillEntity)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        UserEntity userEntity = skillEntity.getUser();
        userEntity.getSkills().remove(skillEntity);
        userRepository.save(userEntity);
    }

    private Boolean hasPermission(String username, SkillEntity skillEntity) {
        return skillEntity.getUser().getUsername().equals(username) ||
               userRepository.findByUsername(username)
                       .orElseThrow(() -> new NotFoundException(
                               format(
                                       USER_NOT_FOUND.getMessage()
                               )
                       )).getRole().equals(ADMIN);
    }
}
