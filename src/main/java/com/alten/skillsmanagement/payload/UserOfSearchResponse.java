package com.alten.skillsmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOfSearchResponse {
    private Long id;
    private String firstName;
    private String lastName;
}
