package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.model.SkillsMatrix;
import com.alten.skillsmanagement.model.SkillsMatrixUser;
import com.alten.skillsmanagement.model.SkillsMatrixUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillsMatrixUserRepository extends JpaRepository<SkillsMatrixUser, SkillsMatrixUserId> {
    List<SkillsMatrixUser> getSkillsMatrixUserByAppUser(AppUser appUser);
    List<SkillsMatrixUser> getSkillsMatrixUserBySkillsMatrix(SkillsMatrix matrix);
}
