package com.alten.skillsmanagement.model;

public enum PermissionName {
    SKILL_READ("skill:read"),
    SKILL_WRITE("skill:write"),
    SKILLS_MATRIX_READ("skills_matrix_read"),
    SKILLS_MATRIX_WRITE("skills_matrix_write"),
    MANAGER_READ("manager:read"),
    MANAGER_WRITE("manager:write"),
    CONSULTANT_READ("consultant:read"),
    CONSULTANT_WRITE("consultant:write");

    private final String permission;

    PermissionName(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
