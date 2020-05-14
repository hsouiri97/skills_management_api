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

@Entity
@Table(name = "skills_matrix")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SkillsMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Min(0)
    @Max(5)
    @ColumnDefault("0")
    private double average_rating;

    @ManyToMany
    @JoinTable(name = "skillsMatrix_skill",
            joinColumns = @JoinColumn(name = "skillsMatrixId"),
            inverseJoinColumns = @JoinColumn(name = "skillId"))
    private HashSet<Skill> skills;

}
