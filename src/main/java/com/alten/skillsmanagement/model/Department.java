package com.alten.skillsmanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;


    @JsonManagedReference
    @OneToMany(mappedBy = "department",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Position> positions = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "department",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<Sector> sectors = new ArrayList<>();

    public void addSector(Sector sector) {
        sector.setDepartment(this);
        this.sectors.add(sector);
    }

    public void removeSector(Sector sector) {
        sector.setDepartment(null);
        //this.sectors.remove(sector); // it causes concurrent modification issue
    }
}
