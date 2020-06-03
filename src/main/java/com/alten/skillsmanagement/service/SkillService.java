package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.dto.SkillDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.*;
import com.alten.skillsmanagement.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SkillService {

    private SkillRepository skillRepository;
    private SkillsMatrixService skillsMatrixService;
    private AccountService accountService;
    private SkillUserService skillUserService;

    @Autowired
    public SkillService(SkillRepository skillRepository,
                        SkillsMatrixService skillsMatrixService,
                        AccountService accountService,
                        SkillUserService skillUserService) {
        this.skillRepository = skillRepository;
        this.skillsMatrixService = skillsMatrixService;
        this.accountService = accountService;
        this.skillUserService = skillUserService;
    }

    public Skill getSkill(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));
    }

    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    public Skill createSkill(SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setName(skillDto.getName());
        //setTheRating(skillDto, skill);
        skillDto.getUnderSkills().forEach(skill::addUnderSkill);
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, SkillDto skillDto) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));

        skill.setName(skillDto.getName());
        Set<UnderSkill> underSkills = skill.getUnderSkills();
        underSkills.forEach(skill::removeUnderSkill);
        skill.getUnderSkills().clear();
        //setTheRating(skillDto, skill);
        skillDto.getUnderSkills().forEach(skill::addUnderSkill);
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }


    public void addSkillToMatrix(Long skillId, Long matrixId) {
        Skill skill = getSkill(skillId);
        SkillsMatrix skillsMatrix = skillsMatrixService.getSkillsMatrix(matrixId);
        skillsMatrix.getSkills().add(skill);
        //skillsMatrix.calculateAverageRating();
    }

    public void removeSkillFromMatrix(Long skillId, Long matrixId) {
        Skill skill = getSkill(skillId);
        SkillsMatrix skillsMatrix = skillsMatrixService.getSkillsMatrix(matrixId);
        skillsMatrix.getSkills().remove(skill);
    }

    public void addRatingToSkillUser(Long skillId, Double rating, Long appUserId) {
        Skill skill = getSkill(skillId);
        AppUser appUser = accountService.getUser(appUserId);

        SkillUser skillUser = new SkillUser();
        skillUser.setAppUser(appUser);
        skillUser.setSkill(skill);
        skillUser.setRating(rating);

        //to check the utility of the code bellow !
        skill.getSkillUsers().add(skillUser);
        appUser.getSkillUsers().add(skillUser);

        skillUserService.saveSkillUser(skillUser);

    }

    public void updateRatingOfSkillUser(Long skillId, Double rating, Long appUserId) {
        SkillUser skillUser = skillUserService.getSkillUser(appUserId, skillId);
        skillUser.setRating(rating);
        skillUserService.saveSkillUser(skillUser);
    }

    public void deleteRatingOfSkillUser(Long skillId, Long appUserId) {
        SkillUser skillUser = skillUserService.getSkillUser(appUserId, skillId);
        skillUserService.deleteSkillUser(skillUser);
    }

    /*private void setTheRating(SkillDto skillDto, Skill skill) {
        if (!skillDto.getUnderSkills().isEmpty()) {
            skillDto.getUnderSkills().forEach(skill::addUnderSkill);
            //skill.calculateRating();
        }
        else {
            skill.setRating(skillDto.getRating());
        }
    }*/

}
