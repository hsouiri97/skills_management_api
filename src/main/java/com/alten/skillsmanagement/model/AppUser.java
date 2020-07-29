package com.alten.skillsmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"}),
                @UniqueConstraint(columnNames = {"email"})
        })
@JsonIgnoreProperties(value={"password"}, allowSetters = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 40)
    private String lastName;

    @NotBlank
    @Size(max = 250)
    private String address;

    @NotBlank
    @Size(max = 8)
    private String cin;

    @NotBlank
    @Size(max = 10)
    private String gender;

    private String diploma;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 40)
    @NaturalId(mutable = true)
    @Email
    private String email;

    @NotBlank
    private String mobile;

    private String quote;

    private boolean isManager;

    private int yearsOfExperience;

    private Date entryDate;

    private Date integrationDate;

    private Date departureDate;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<AppRole> roles = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "positionId")
    private Position position;

    @OneToOne(mappedBy = "projectManager")
    private Project manageProject;

    /*@JsonIgnore
    @OneToMany (mappedBy = "appUser")
    private Set<SkillsMatrix> skillsMatrices = new HashSet<>();*/

    @JsonIgnore
    @OneToMany (mappedBy = "appUser")
    private Set<SkillsMatrixUser> skillsMatrixUsers = new HashSet<>();

    @JsonIgnore
    @OneToMany (mappedBy = "appUser")
    private Set<SkillUser> skillUsers = new HashSet<>();

    @JsonIgnore
    @OneToMany (mappedBy = "appUser")
    private Set<ProjectUser> projectUsers = new HashSet<>();

    public AppUser(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
