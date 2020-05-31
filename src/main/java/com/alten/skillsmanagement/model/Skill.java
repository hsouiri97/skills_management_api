package com.alten.skillsmanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
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
@Getter
@Setter
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @ColumnDefault("0")
    private Double rating = -1.d;

    @JsonManagedReference
    @OneToMany(mappedBy = "skill",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<UnderSkill> underSkills = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "skill_user",
            joinColumns = {@JoinColumn(name = "skill_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<AppUser> appUsers = new HashSet<>();


    @OneToMany (mappedBy = "skill")
    private Set<SkillUser> skillUsers = new HashSet<>();

    public void addUnderSkill(UnderSkill underSkill) {
        underSkills.add(underSkill);
        underSkill.setSkill(this);
    }


    public void removeUnderSkill(UnderSkill underSkill) {
        underSkill.setSkill(null);
    }

    public void calculateRating() {
        //if (rating.equals(-1.d)) {
            rating = (double) Math.round(underSkills.stream().mapToDouble(UnderSkill::getRating)
                    .average()
                    .orElse(Double.NaN)*100d)/100d;

        //}
        System.out.println("from skill: "+ rating);
    }
}
