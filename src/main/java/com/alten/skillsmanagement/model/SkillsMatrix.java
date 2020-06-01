package com.alten.skillsmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "skills_matrix")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SkillsMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Min(0)
    @Max(5)
    @ColumnDefault("0")
    @Setter(AccessLevel.NONE)
    private double averageRating;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "skillsMatrix_skill",
            joinColumns = @JoinColumn(name = "skillsMatrixId"),
            inverseJoinColumns = @JoinColumn(name = "skillId"))
    private Set<Skill> skills = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    /*public void calculateAverageRating() {
        averageRating =  Math.round(skills.stream().mapToDouble(Skill::getRating)
                .average()
                .orElse(Double.NaN)*100d)/100d;
        System.out.println("from skillMatrix: "+ averageRating);
    }*/

}
