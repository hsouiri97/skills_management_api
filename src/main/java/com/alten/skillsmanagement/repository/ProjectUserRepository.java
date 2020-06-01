package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.ProjectUser;
import com.alten.skillsmanagement.model.ProjectUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, ProjectUserId> {
}
