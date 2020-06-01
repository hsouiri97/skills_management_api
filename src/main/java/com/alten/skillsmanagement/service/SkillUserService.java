package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.SkillUser;
import com.alten.skillsmanagement.model.SkillUserId;
import com.alten.skillsmanagement.repository.SkillUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SkillUserService {
    private SkillUserRepository skillUserRepository;

    @Autowired
    public SkillUserService(SkillUserRepository skillUserRepository) {
        this.skillUserRepository = skillUserRepository;
    }

    public SkillUser saveSkillUser(SkillUser skillUser) {
        return skillUserRepository.save(skillUser);
    }

    public SkillUser getSkillUser(Long userId, Long skillId) {
        return skillUserRepository.findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("SkillUser", "userId and skillId", new SkillUserId(skillId, userId)));
    }

    public void deleteSkillUser(SkillUser skillUser) {
        skillUserRepository.delete(skillUser);
    }
}
