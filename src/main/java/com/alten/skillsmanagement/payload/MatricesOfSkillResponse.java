package com.alten.skillsmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatricesOfSkillResponse {
    private Long skillId;
    private Long matrixId;
    private String matrixTitle;

}
