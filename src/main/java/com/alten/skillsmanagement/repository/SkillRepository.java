package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
