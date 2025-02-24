package com.ismailjacoby.jobtrackerapi.form;

import com.ismailjacoby.jobtrackerapi.enums.JobStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record JobForm(
        @NotBlank(message = "Company name is required.")
        @Size(max = 50, message = "Company name can't be over 50 characters long.")
        String company,

        @NotBlank(message = "Job title is required.")
        @Size(max = 50, message = "Job title can't be over 50 characters long.")
        String title,

        @NotBlank(message = "Job location is required.")
        @Size(max = 50, message = "Location can't be over 50 characters long.")
        String location,

        JobStatus status,

        @PastOrPresent(message = "Date applied cannot be in the future")
        LocalDate dateApplied,

        @Size(max = 2048)
        @Pattern(regexp = "^(http|https)://.*", message = "Invalid URL format")
        String link,

        @Size(max = 2000, message = "Description can't be over 2000 characters long.")
        String description,

        @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be positive")
        BigDecimal salary,

        @Size(max = 500, message = "Notes  can't be over 500 characters long.")
        String notes

) {
}
