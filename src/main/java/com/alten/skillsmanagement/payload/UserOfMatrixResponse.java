package com.alten.skillsmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOfMatrixResponse {
    private Long matrixId;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userMobile;
}
