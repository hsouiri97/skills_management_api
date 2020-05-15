package com.alten.skillsmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Min(0)
    @Max(5)
    @ColumnDefault("0")
    private double rating;

    @OneToMany(mappedBy = "skill")
    private Set<UnderSkill> underSkills;

    public void addUnderSkill(UnderSkill underSkill) {
        underSkills.add(underSkill);
        underSkill.setSkill(this);
    }

    public void removeSkill(UnderSkill underSkill) {
        underSkills.remove(underSkill);
        underSkill.setSkill(null);
    }
}
