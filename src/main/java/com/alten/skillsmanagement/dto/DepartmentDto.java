package com.alten.skillsmanagement.dto;

import com.alten.skillsmanagement.model.Position;
import com.alten.skillsmanagement.model.Sector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Data
public class DepartmentDto {

    @NotBlank
    private String name;

    @Valid
    private List<Sector> sectors;

    @Valid
    private List<Position> positions;
}
