package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.dto.SkillDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.Skill;
import com.alten.skillsmanagement.model.SkillsMatrix;
import com.alten.skillsmanagement.model.UnderSkill;
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

    @Autowired
    public SkillService(SkillRepository skillRepository,
                        SkillsMatrixService skillsMatrixService) {
        this.skillRepository = skillRepository;
        this.skillsMatrixService = skillsMatrixService;
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
        setTheRating(skillDto, skill);
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, SkillDto skillDto) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));

        skill.setName(skillDto.getName());
        Set<UnderSkill> underSkills = skill.getUnderSkills();
        underSkills.forEach(skill::removeUnderSkill);
        skill.getUnderSkills().clear();
        setTheRating(skillDto, skill);
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    public void addSkillToMatrix(Long skillId, Long matrixId) {
        Skill skill = getSkill(skillId);
        SkillsMatrix skillsMatrix = skillsMatrixService.getSkillsMatrix(matrixId);
        skillsMatrix.getSkills().add(skill);
        skillsMatrix.calculateAverageRating();
    }

    private void setTheRating(SkillDto skillDto, Skill skill) {
        if (!skillDto.getUnderSkills().isEmpty()) {
            skillDto.getUnderSkills().forEach(skill::addUnderSkill);
            skill.calculateRating();
        }
        else {
            skill.setRating(skillDto.getRating());
        }
    }

}
