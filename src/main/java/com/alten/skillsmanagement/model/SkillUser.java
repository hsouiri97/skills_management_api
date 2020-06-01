package com.alten.skillsmanagement.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class SkillUser {
    @EmbeddedId
    private SkillUserId id = new SkillUserId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "skillId")
    @MapsId("skillId")
    private Skill skill;

    private Double rating;
}
