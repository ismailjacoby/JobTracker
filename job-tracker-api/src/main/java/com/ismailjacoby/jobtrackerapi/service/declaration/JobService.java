package com.ismailjacoby.jobtrackerapi.service.declaration;

import com.ismailjacoby.jobtrackerapi.dto.JobDTO;
import com.ismailjacoby.jobtrackerapi.dto.JobShortDTO;
import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.form.JobForm;

import java.util.List;
import java.util.Optional;

public interface JobService {
    void addJob(JobForm form);
    Optional<JobEntity> getJobById(Long id);
    List<JobEntity> getAllJobs();
    void updateJob(Long id, JobForm form);
    void deleteJob(Long id);
}
