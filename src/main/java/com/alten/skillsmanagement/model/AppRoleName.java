package com.alten.skillsmanagement.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.alten.skillsmanagement.model.AppPermissionName.*;

public enum AppRoleName {
    ADMIN(Sets.newHashSet(SKILL_READ, SKILL_WRITE,
            SKILLS_MATRIX_READ, SKILLS_MATRIX_WRITE,
            MANAGER_READ, MANAGER_WRITE,
            CONSULTANT_READ, CONSULTANT_WRITE)),

    MANAGER(Sets.newHashSet(SKILL_READ, SKILL_WRITE,
            SKILLS_MATRIX_READ, SKILLS_MATRIX_WRITE,
            MANAGER_READ, CONSULTANT_READ)),

    CONSULTANT(Sets.newHashSet(SKILL_READ, SKILL_WRITE,
            SKILLS_MATRIX_READ, SKILLS_MATRIX_WRITE,
            CONSULTANT_READ));

    private final Set<AppPermissionName> appPermissionNames;

    AppRoleName(Set<AppPermissionName> appPermissionNames) {
        this.appPermissionNames = appPermissionNames;
    }

    public Set<AppPermissionName> getAppPermissionNames() {
        return appPermissionNames;
    }

    public Set<SimpleGrantedAuthority> simpleGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getAppPermissionNames().stream()
                .map(appPermissionName -> new SimpleGrantedAuthority(appPermissionName.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authorities;
    }
}
