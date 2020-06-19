package com.alten.skillsmanagement.payload;

import com.alten.skillsmanagement.model.SkillsMatrix;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatricesOfUserResponse {
    private List<SkillsMatrix> skillsMatrices;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userMobile;
    
}
