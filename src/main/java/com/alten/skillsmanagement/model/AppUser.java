package com.alten.skillsmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"}),
                @UniqueConstraint(columnNames = {"email"})
        })
@JsonIgnoreProperties(value={"password", "id"}, allowSetters = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    @Size(max = 7)
    private String cin;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 40)
    @NaturalId
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<AppRole> roles;
}
