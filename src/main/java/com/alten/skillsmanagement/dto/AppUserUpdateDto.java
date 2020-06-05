package com.alten.skillsmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AppUserUpdateDto {
    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 20)
    private String lastName;

    @NotBlank
    @Size(max = 250)
    private String address;

    @NotBlank
    @Size(max = 7)
    private String cin;

    @NotBlank
    @Size(max = 10)
    private String gender;

    private String diploma;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    private String mobile;

    private String quote;
}
