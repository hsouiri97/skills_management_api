package com.alten.skillsmanagement.dto;

import com.alten.skillsmanagement.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Data
public class SkillsMatrixDto {
    @NotBlank
    private String title;
    //private Set<Skill> skills;
}
