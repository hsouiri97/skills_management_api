package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.AppRole;
import com.alten.skillsmanagement.model.AppRoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {
    AppRole findByRoleName(AppRoleName roleName);
}
