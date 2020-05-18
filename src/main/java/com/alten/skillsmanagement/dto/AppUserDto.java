package com.alten.skillsmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor @AllArgsConstructor @Data
public class AppUserDto {
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
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;
}
