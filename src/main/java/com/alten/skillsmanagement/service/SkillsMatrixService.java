package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.dto.SkillsMatrixDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.*;
import com.alten.skillsmanagement.payload.MatrixOfUserResponse;
import com.alten.skillsmanagement.payload.UserOfMatrixResponse;
import com.alten.skillsmanagement.repository.SkillsMatrixRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillsMatrixService {

    private SkillsMatrixRepository skillsMatrixRepository;
    private AccountService accountService;
    private SkillsMatrixUserService skillsMatrixUserService;

    @Autowired
    public SkillsMatrixService(SkillsMatrixRepository skillsMatrixRepository,
                               AccountService accountService,
                               SkillsMatrixUserService skillsMatrixUserService) {
        this.skillsMatrixRepository = skillsMatrixRepository;
        this.accountService = accountService;
        this.skillsMatrixUserService = skillsMatrixUserService;
    }

    public SkillsMatrix getSkillsMatrix(Long id) {
        return skillsMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SkillsMatrix", "id", id));
    }

    public List<SkillsMatrix> getSkillsMatrices() {
        return skillsMatrixRepository.findAll();
    }

    public SkillsMatrix createSkillsMatrix(SkillsMatrixDto skillsMatrixDto) {
        SkillsMatrix skillsMatrix = new SkillsMatrix();
        skillsMatrix.setTitle(skillsMatrixDto.getTitle());
        //Set<Skill> skills = skillsMatrixDto.getSkills();
        /*skillsMatrixDto.getSkills().forEach(skill -> {
                skill.getUnderSkills().forEach(skill::addUnderSkill);
                skill.calculateRating();
        });*/
        //skillsMatrix.setSkills(skillsMatrixDto.getSkills());
        //skillsMatrix.calculateAverageRating();
        return skillsMatrixRepository.save(skillsMatrix);
    }

    //to be invoked by the admin
    public void affectMatrixToUser(Long matrixId, Long appUserId) {
        AppUser appUser = accountService.getUser(appUserId);
        SkillsMatrix skillsMatrix = getSkillsMatrix(matrixId);
        //skillsMatrix.setAppUser(appUser);
        SkillsMatrixUser skillsMatrixUser = new SkillsMatrixUser();
        skillsMatrixUser.setAppUser(appUser);
        skillsMatrixUser.setSkillsMatrix(skillsMatrix);

        appUser.getSkillsMatrixUsers().add(skillsMatrixUser);
        skillsMatrix.getSkillsMatrixUsers().add(skillsMatrixUser);

        skillsMatrixUserService.saveSkillsMatrixUser(skillsMatrixUser);
    }

    public SkillsMatrix updateSkillsMatrix(Long id, SkillsMatrixDto skillsMatrixDto) {
        SkillsMatrix skillsMatrix = skillsMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SkillsMatrix", "id", id));

        skillsMatrix.setTitle(skillsMatrixDto.getTitle());
        return skillsMatrix;
    }

    public void deleteSkillsMatrix(Long id) {
        SkillsMatrix skillsMatrix = skillsMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SkillsMatrix", "id", id));

        skillsMatrixRepository.delete(skillsMatrix);
    }

    public List<MatrixOfUserResponse> getSkillsMatricesByAppUser(Long userId) {
        /*AppUser appUser = accountService.getUser(userId);
        return skillsMatrixRepository.getSkillsMatrixByAppUser(appUser)
                .orElseThrow(() -> new ResourceNotFoundException("SkillsMatrix", "appUser", appUser));
        return skillsMatrixUserService.getSkillsMatrixUserByAppUser(appUser)
                .stream().map(SkillsMatrixUser::getSkillsMatrix).collect(Collectors.toList());*/ //old ways

        AppUser appUser = accountService.getUser(userId);

        return skillsMatrixUserService.getSkillsMatricesUserByAppUser(appUser)
        .stream().map(skillsMatrixUser -> {
            MatrixOfUserResponse matrix = new MatrixOfUserResponse();
            matrix.setUserId(skillsMatrixUser.getAppUser().getId());
            matrix.setSkillsMatrix(skillsMatrixUser.getSkillsMatrix());
            matrix.setAverageRating(skillsMatrixUser.getAverageRating());
            return matrix;
        }).collect(Collectors.toList());
    }

    public List<UserOfMatrixResponse> getUsersByMatrix(Long matrixId) {
        SkillsMatrix skillsMatrix = getSkillsMatrix(matrixId);

        return skillsMatrixUserService.getSkillsMatricesUserBySkillsMatrix(skillsMatrix)
                .stream().map(skillsMatrixUser -> {
                    UserOfMatrixResponse user = new UserOfMatrixResponse();
                    user.setMatrixId(skillsMatrixUser.getSkillsMatrix().getId());
                    user.setUserId(skillsMatrixUser.getAppUser().getId());
                    user.setUserFirstName(skillsMatrixUser.getAppUser().getFirstName());
                    user.setUserLastName(skillsMatrixUser.getAppUser().getLastName());
                    user.setUserEmail(skillsMatrixUser.getAppUser().getEmail());
                    user.setUserMobile(skillsMatrixUser.getAppUser().getMobile());
                    return user;
        }).collect(Collectors.toList());
    }

    /*public SkillsMatrix setAverageRating(SkillsMatrix skillsMatrix, double averageRating) {
        skillsMatrix.setAverageRating(averageRating);
        return skillsMatrixRepository.save(skillsMatrix);
    }*/

    //to be invoked by the consultant
    //for setting the first time and anytime after :)
    public void setAverageRating(Long matrixId, Long userId, Double averageRating) {
        SkillsMatrixUser skillsMatrixUser = skillsMatrixUserService
                .getSkillsMatrixUser(new SkillsMatrixUserId(matrixId, userId));
        skillsMatrixUser.setAverageRating(averageRating);
        skillsMatrixUserService.saveSkillsMatrixUser(skillsMatrixUser);
    }

    //I don't think we need a method for deleting the average rating ?

    public List<SkillsMatrix> getSkillsMatricesBySkills(Skill skill) {
        return this.skillsMatrixRepository.getSkillsMatricesBySkillId(skill.getId()).stream()
                .map(matrix -> new SkillsMatrix(matrix.getId(), matrix.getTitle()))
                .collect(Collectors.toList());
    }

    public List<SkillsMatrix> getSkillsMatricesTitleOnly() {
        return this.skillsMatrixRepository.getSkillsMatricesTitleOnly();
    }

    public List<MatrixOfUserResponse> getSkillsMatrixUserList() {
        return this.skillsMatrixUserService.getSkillsMatrixUserList()
        .stream().map(skillsMatrixUser -> {
            MatrixOfUserResponse matrixOfUserResponse = new MatrixOfUserResponse();
            matrixOfUserResponse.setSkillsMatrix(skillsMatrixUser.getSkillsMatrix());
            matrixOfUserResponse.setAverageRating(skillsMatrixUser.getAverageRating());
            matrixOfUserResponse.setUserId(skillsMatrixUser.getAppUser().getId());
            return matrixOfUserResponse;
        }).collect(Collectors.toList());
    }
}
