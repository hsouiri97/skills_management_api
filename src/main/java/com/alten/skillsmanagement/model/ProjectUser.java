package com.alten.skillsmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ProjectUser {
    @EmbeddedId
    private ProjectUserId projectUserId = new ProjectUserId();

    @ManyToOne
    @JoinColumn(name = "userId")
    @MapsId("userId")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "projectId")
    @MapsId("projectId")
    private Project project;

    private Date startDate;

    private Date endDate;

    private String responsibility;

}
