package com.alten.skillsmanagement.payload;

import com.alten.skillsmanagement.model.SkillsMatrix;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatrixOfUserResponse {
    private SkillsMatrix skillsMatrix;
    private Long userId;
    private Double averageRating;
}
