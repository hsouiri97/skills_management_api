package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
