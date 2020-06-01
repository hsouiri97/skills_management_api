package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.SkillUser;
import com.alten.skillsmanagement.model.SkillUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SkillUserRepository extends JpaRepository<SkillUser, SkillUserId> {
    @Query("select su from SkillUser su where su.appUser.id = :userId and su.skill.id = :skillId")
    Optional<SkillUser> findByUserIdAndSkillId(@Param("userId") Long userId,
                                               @Param("skillId") Long skillId);
}
