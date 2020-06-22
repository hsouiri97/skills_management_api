package com.alten.skillsmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SkillsMatrixUser {

    @EmbeddedId
    private SkillsMatrixUserId id = new SkillsMatrixUserId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "skillsMatrixId")
    @MapsId("skillsMatrixId")
    private SkillsMatrix skillsMatrix;

    private Double averageRating;
}
