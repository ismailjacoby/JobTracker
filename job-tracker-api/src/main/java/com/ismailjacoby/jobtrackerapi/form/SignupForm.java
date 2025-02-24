package com.ismailjacoby.jobtrackerapi.form;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupForm(
        @NotBlank(message = "First name cannot be blank.")
        @Size(max = 50, message = "First name cannot exceed 50 characters.")
        String firstName,

        @NotBlank(message = "Last name cannot be blank.")
        @Size(max = 50, message = "Last name cannot exceed 50 characters.")
        String lastName,

        @NotBlank(message = "Email cannot be blank.")
        @Email(message =  "Email should be valid.")
        String email,

        @NotBlank(message = "Username cannot be blank.")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long.")
        String username,

        @NotBlank(message = "Password cannot be blank.")
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters long.")
        String password
) {
}