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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "under_skills")
public class UnderSkill {
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
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillId")
    private Skill skill;
}
