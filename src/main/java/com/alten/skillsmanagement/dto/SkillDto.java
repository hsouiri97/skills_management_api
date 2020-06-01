package com.alten.skillsmanagement.dto;

import com.alten.skillsmanagement.model.UnderSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Data
public class SkillDto {

    @NotBlank
    @Size(max = 100)
    private String name;

    /*@Min(0)
    @Max(5)
    private Double rating = -1.d;*/

    @Valid
    private Set<UnderSkill> underSkills;
}
