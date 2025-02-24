package com.ismailjacoby.jobtrackerapi.dto;

import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.enums.JobStatus;

import java.time.LocalDate;

public record JobShortDTO(
        Long id,
        String company,
        String title,
        String location,
        JobStatus status,
        LocalDate dateApplied
) {
    public static JobShortDTO toDto(JobEntity entity){
        return new JobShortDTO(
                entity.getId(),
                entity.getCompany(),
                entity.getTitle(),
                entity.getLocation(),
                entity.getStatus(),
                entity.getDateApplied()
        );
    }
}
