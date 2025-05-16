package com.ismailjacoby.jobtrackerapi.models.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupForm(
        @NotBlank(message = "First name is required.")
        String firstName,

        @NotBlank(message = "Last name is required.")
        String lastName,

        @NotBlank(message = "Email is required.")
        @Email(message = "Please enter a valid email address.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Please enter a valid email address.")
        String email,

        @NotBlank(message = "Password is required.")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_#?!@=$ %^&*-]).{8,}$",
                message = "Password must be at least 8 characters long, include one uppercase letter, one lowercase letter, one number and one special character")
        String password,

        @NotBlank(message = "Please confirm your password.")
        String confirmPassword
) {
}
