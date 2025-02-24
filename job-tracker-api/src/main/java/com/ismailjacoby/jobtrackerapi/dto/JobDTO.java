package com.ismailjacoby.jobtrackerapi.dto;

import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.enums.JobStatus;


import java.math.BigDecimal;
import java.time.LocalDate;

public record JobDTO (
        Long id,
        String company,
        String title,
        String location,
        JobStatus status,
        LocalDate dateApplied,
        String link,
        String description,
        BigDecimal salary,
        String notes) {

    public static JobDTO toDto(JobEntity entity) {
        return new JobDTO(
                entity.getId(),
                entity.getCompany(),
                entity.getTitle(),
                entity.getLocation(),
                entity.getStatus(),
                entity.getDateApplied(),
                entity.getLink(),
                entity.getDescription(),
                entity.getSalary(),
                entity.getNotes()
        );
    }
}
