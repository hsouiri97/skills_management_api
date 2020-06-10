package com.alten.skillsmanagement.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    //@JsonIgnoreProperties("department")
    //@JsonManagedReference
    @OneToMany(mappedBy = "department",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Position> positions = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "department",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<Sector> sectors = new HashSet<>();

    public void addSector(Sector sector) {
        sector.setDepartment(this);
        this.sectors.add(sector);
    }

    public void removeSector(Sector sector) {
        sector.setDepartment(null);
        //this.sectors.remove(sector); // it causes concurrent modification issue
    }
}
