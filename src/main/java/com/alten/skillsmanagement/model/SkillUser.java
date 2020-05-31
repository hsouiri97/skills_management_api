package com.alten.skillsmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
@Table(name = "skill_user")
public class SkillUser {
    @EmbeddedId
    private SkillUserId id = new SkillUserId();

    @ManyToOne
    @MapsId("skillId")
    private Skill skill;

    @ManyToOne
    @MapsId("userId")
    private AppUser appUser;

    private Double rating;
}
