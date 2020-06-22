package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.model.SkillsMatrix;
import com.alten.skillsmanagement.model.SkillsMatrixUser;
import com.alten.skillsmanagement.model.SkillsMatrixUserId;
import com.alten.skillsmanagement.repository.SkillsMatrixUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SkillsMatrixUserService {

    private SkillsMatrixUserRepository skillsMatrixUserRepository;

    @Autowired
    public SkillsMatrixUserService(SkillsMatrixUserRepository skillsMatrixUserRepository) {
        this.skillsMatrixUserRepository = skillsMatrixUserRepository;
    }

    public SkillsMatrixUser saveSkillsMatrixUser(SkillsMatrixUser skillsMatrixUser) {
        return skillsMatrixUserRepository.save(skillsMatrixUser);
    }

    public SkillsMatrixUser getSkillsMatrixUser(SkillsMatrixUserId skillsMatrixUserId) {
        return skillsMatrixUserRepository.findById(skillsMatrixUserId)
                .orElseThrow(() -> new ResourceNotFoundException("SkillsMatrixUser", "id", skillsMatrixUserId));
    }

    public List<SkillsMatrixUser> getSkillsMatricesUserByAppUser(AppUser appUser) {
        return skillsMatrixUserRepository.getSkillsMatrixUserByAppUser(appUser);
    }

    public List<SkillsMatrixUser> getSkillsMatricesUserBySkillsMatrix(SkillsMatrix matrix) {
        return skillsMatrixUserRepository.getSkillsMatrixUserBySkillsMatrix(matrix);
    }

    public void deleteSkillsUserMatrix(SkillsMatrixUser skillsMatrixUser) {
        skillsMatrixUserRepository.delete(skillsMatrixUser);
    }

    public List<SkillsMatrixUser> getSkillsMatrixUserList() {
        return skillsMatrixUserRepository.findAll();
    }
}
