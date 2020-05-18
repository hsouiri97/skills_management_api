package com.alten.skillsmanagement.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "projects")
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Calendar startDate;

    private Calendar expectedEndDate;

    @OneToOne
    @JoinColumn(name = "projectManagerId", referencedColumnName = "id")
    private AppUser projectManager;

}
