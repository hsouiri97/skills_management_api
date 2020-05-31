package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.dto.SkillsMatrixDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.model.Skill;
import com.alten.skillsmanagement.model.SkillsMatrix;
import com.alten.skillsmanagement.repository.SkillsMatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SkillsMatrixService {

    private SkillsMatrixRepository skillsMatrixRepository;
    private AccountService accountService;

    @Autowired
    public SkillsMatrixService(SkillsMatrixRepository skillsMatrixRepository, AccountService accountService) {
        this.skillsMatrixRepository = skillsMatrixRepository;
        this.accountService = accountService;
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

    public SkillsMatrix updateSkillsMatrix(Long id, SkillsMatrixDto skillsMatrixDto){
        SkillsMatrix skillsMatrix = skillsMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SkillsMatrix", "id", id));

        skillsMatrix.setTitle(skillsMatrixDto.getTitle());
        return skillsMatrix;
    }

    /*public void affectMatrixToUser(String username, Long id) {
        AppUser appUser = accountService.findUserByUsername(username);
        SkillsMatrix skillsMatrix = getSkillsMatrix(id);
        skillsMatrix.getAppUsers().add(appUser);
    }*/
}
