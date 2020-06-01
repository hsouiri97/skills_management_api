package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.model.SkillsMatrix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillsMatrixRepository extends JpaRepository<SkillsMatrix, Long> {
    Optional<SkillsMatrix> getSkillsMatrixByAppUser(AppUser appUser);
}
