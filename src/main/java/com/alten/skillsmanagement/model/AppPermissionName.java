package com.alten.skillsmanagement.model;

public enum AppPermissionName {
    //SKILL_READ("skill:read"),
    //SKILL_WRITE("skill:write"),
    SKILLS_MATRIX_READ("skills_matrix:read"),
    SKILLS_MATRIX_WRITE("skills_matrix:write"),
    MANAGER_READ("manager:read"),
    MANAGER_WRITE("manager:write"),
    CONSULTANT_READ("consultant:read"),
    CONSULTANT_WRITE("consultant:write");

    private final String permission;

    AppPermissionName(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
