package com.alten.skillsmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Table(name = "roles")
@NoArgsConstructor @AllArgsConstructor @Data
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName roleName;

    @ManyToMany
    @JoinTable(name = "role_permission",
    joinColumns = @JoinColumn(name = "roleIid"),
    inverseJoinColumns = @JoinColumn(name = "permissionId"))
    private HashSet<Permission> permissions;
}
