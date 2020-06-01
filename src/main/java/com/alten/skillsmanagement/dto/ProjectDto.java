package com.alten.skillsmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ProjectDto {

    @NotBlank
    private String title;

    @NotNull
    private Date startDate;

    @NotNull
    private Date expectedEndDate;
}
