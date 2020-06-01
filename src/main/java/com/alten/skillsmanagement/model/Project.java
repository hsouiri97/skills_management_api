package com.alten.skillsmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date expectedEndDate;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "projectManagerId", referencedColumnName = "id")
    private AppUser projectManager;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<ProjectUser> projectUsers = new HashSet<>();

}
